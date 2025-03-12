package io.github.learnjakartaee.security.auth;

import static jakarta.interceptor.Interceptor.Priority.PLATFORM_BEFORE;
import static jakarta.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static jakarta.security.enterprise.AuthenticationStatus.SUCCESS;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.auth.message.AuthException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Implementation based on Tomee's and Soteria's implementation
 */
@SupportCustomLoginToContinue
@Interceptor
@Priority(PLATFORM_BEFORE + 220)
public class CustomLoginToContinueInterceptor {

	protected final Logger LOG = LoggerFactory.getLogger(CustomLoginToContinueInterceptor.class);

	public static final String ORIGINAL_REQUEST = "io.github.learnjakartaee.security.request.orig";
	public static final String AUTHENTICATION = "io.github.learnjakartaee.security.request.auth";
	public static final String CALLER_AUTHENTICATION = "io.github.learnjakartaee.security.request.caller.auth";

	public CustomLoginToContinueInterceptor() {
		LOG.info("LOADING CustomLoginToContinueInterceptor...");
	}

	private static final Class<?>[] VALIDATE_REQUEST_PARAMETER_TYPES = new Class<?>[] {
		HttpServletRequest.class,
		HttpServletResponse.class,
		HttpMessageContext.class };

	@AroundInvoke
	public Object intercept(InvocationContext invocationContext) throws Exception {

		if (invocationContext.getMethod().getName().equals("validateRequest") &&
			Arrays.equals(invocationContext.getMethod().getParameterTypes(), VALIDATE_REQUEST_PARAMETER_TYPES)) {

			// Only validate requests that have a login page
			HttpServletRequest request = (HttpServletRequest) invocationContext.getParameters()[0];
			String contextPath = request.getContextPath();
			if (HttpAuthenticationMechanismChain.getLoginPage(contextPath) != null) {
				return validateRequest(invocationContext);
			}
		}
		return invocationContext.proceed();
	}

	protected AuthenticationStatus validateRequest(InvocationContext invocationContext) throws Exception {

		HttpMessageContext httpMessageContext = (HttpMessageContext) invocationContext.getParameters()[2];
		clearStaleState(httpMessageContext);

		if (httpMessageContext.getAuthParameters().isNewAuthentication()) {
			return processCallerInitiatedAuthentication(invocationContext, httpMessageContext);
		} else {
			return processContainerInitiatedAuthentication(invocationContext, httpMessageContext);
		}
	}

	protected void clearStaleState(HttpMessageContext httpMessageContext) {

		// Does j_security_check still work?
		if (httpMessageContext.isProtected() && !httpMessageContext.isAuthenticationRequest()
				&& hasRequest(httpMessageContext.getRequest()) && !hasAuthentication(httpMessageContext.getRequest())
				&& !httpMessageContext.getRequest().getRequestURI().endsWith("j_security_check")) {
			httpMessageContext.getRequest().getSession().removeAttribute(ORIGINAL_REQUEST);
			httpMessageContext.getRequest().getSession().removeAttribute(CALLER_AUTHENTICATION);
		}

		if (httpMessageContext.getAuthParameters().isNewAuthentication()) {
			httpMessageContext.getRequest().getSession().setAttribute(CALLER_AUTHENTICATION, true);
			httpMessageContext.getRequest().getSession().removeAttribute(ORIGINAL_REQUEST);
			httpMessageContext.getRequest().getSession().removeAttribute(AUTHENTICATION);
		}
	}

	protected AuthenticationStatus processCallerInitiatedAuthentication(InvocationContext invocationContext,
			HttpMessageContext httpMessageContext) throws Exception {
		AuthenticationStatus status;
		try {
			status = (AuthenticationStatus) invocationContext.proceed();
		} catch (AuthException e) {
			status = AuthenticationStatus.SEND_FAILURE;
		}

		if (status == AuthenticationStatus.SUCCESS) {
			if (httpMessageContext.getCallerPrincipal() == null) {
				return AuthenticationStatus.SUCCESS;
			}
		}
		return status;
	}

	protected AuthenticationStatus processContainerInitiatedAuthentication(InvocationContext invocationContext,
			HttpMessageContext httpMessageContext) throws Exception {

		if (isOnInitialProtectedUrl(httpMessageContext)) {
			saveRequest(httpMessageContext.getRequest());

			String loginPage = HttpAuthenticationMechanismChain.getLoginPage(httpMessageContext.getRequest().getContextPath());
			// TODO: Support forwards...
			//if (loginToContinue.useForwardToLogin()) {
			//	return httpMessageContext.forward(loginToContinue.loginPage());
			//}
			return httpMessageContext.redirect(toAbsoluteUrl(httpMessageContext.getRequest(), loginPage));
		}

		if (isOnLoginPostBack(httpMessageContext)) {
			AuthenticationStatus status = (AuthenticationStatus) invocationContext.proceed();

			if (status.equals(SUCCESS)) {
				if (httpMessageContext.getCallerPrincipal() == null) {
					return SUCCESS;
				}

				if (matchRequest(httpMessageContext.getRequest())) {
					return SUCCESS;
				}

				saveAuthentication(httpMessageContext.getRequest(), httpMessageContext.getCallerPrincipal(), httpMessageContext.getGroups());

				SavedRequest savedRequest = getRequest(httpMessageContext.getRequest());
				return httpMessageContext.redirect(savedRequest.getUrlWithQueryString());

			} else if (status.equals(SEND_FAILURE)) {
				String errorPage = HttpAuthenticationMechanismChain.getErrorPage(httpMessageContext.getRequest().getContextPath());
				return httpMessageContext.redirect(toAbsoluteUrl(httpMessageContext.getRequest(), errorPage));
			} else {
				return status;
			}
		}

		if (isOnOriginalUrlAfterAuthenticate(httpMessageContext)) {
			SavedRequest savedRequest = getRequest(httpMessageContext.getRequest());
			SavedAuthentication savedAuthentication = getAuthentication(httpMessageContext.getRequest());

			clearRequest(httpMessageContext.getRequest());

			return httpMessageContext
					.withRequest(savedRequest.wrap(httpMessageContext.getRequest()))
					.notifyContainerAboutLogin(savedAuthentication.getPrincipal(), savedAuthentication.getGroups());
		}
		return (AuthenticationStatus) invocationContext.proceed();
	}

	protected String toAbsoluteUrl(HttpServletRequest request, String page) {
		String url = request.getRequestURL().toString();
		String baseContextUrl = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();

		// prevent double slashes
		return baseContextUrl.endsWith("/") && page.startsWith("/")
				? baseContextUrl.substring(0, baseContextUrl.length() - 2) + page
				: baseContextUrl + page;
	}

	protected boolean isOnInitialProtectedUrl(HttpMessageContext httpMessageContext) {
		return httpMessageContext.isProtected() && !hasRequest(httpMessageContext.getRequest());
	}

	protected boolean isOnLoginPostBack(HttpMessageContext httpMessageContext) {
		return hasRequest(httpMessageContext.getRequest()) && !hasAuthentication(httpMessageContext.getRequest());
	}

	protected boolean isOnOriginalUrlAfterAuthenticate(HttpMessageContext httpMessageContext) {
		return hasRequest(httpMessageContext.getRequest()) && hasAuthentication(httpMessageContext.getRequest());
	}

	protected boolean matchRequest(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}

		SavedRequest originalRequest = (SavedRequest) request.getSession().getAttribute(ORIGINAL_REQUEST);
		if (originalRequest == null) {
			return false;
		}

		String requestURI = request.getRequestURI();
		return requestURI != null && requestURI.equals(originalRequest.getUrlWithQueryString());
	}

	protected boolean hasRequest(HttpServletRequest request) {
		return request.getSession().getAttribute(ORIGINAL_REQUEST) != null;
	}

	protected SavedRequest getRequest(HttpServletRequest request) {
		return (SavedRequest) request.getSession().getAttribute(ORIGINAL_REQUEST);
	}

	protected void saveRequest(HttpServletRequest request) throws IOException {
		request.getSession().setAttribute(ORIGINAL_REQUEST, SavedRequest.fromRequest(request));
	}

	protected boolean hasAuthentication(HttpServletRequest request) {
		return request.getSession().getAttribute(AUTHENTICATION) != null;
	}

	protected SavedAuthentication getAuthentication(HttpServletRequest request) {
		return (SavedAuthentication) request.getSession().getAttribute(AUTHENTICATION);
	}

	protected void saveAuthentication(HttpServletRequest request, Principal principal, Set<String> groups) {
		SavedAuthentication savedAuthentication = new SavedAuthentication(principal, groups);
		request.getSession().setAttribute(AUTHENTICATION, savedAuthentication);
	}

	protected void clearRequest(HttpServletRequest request) {
		request.getSession().removeAttribute(ORIGINAL_REQUEST);
		request.getSession().removeAttribute(AUTHENTICATION);
	}
}
