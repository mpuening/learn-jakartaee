package io.github.learnjakartaee.auth;

import java.io.IOException;
import java.security.Principal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Named
@RequestScoped
public class LoginBean {
	@Inject
	private SecurityContext securityContext;

	@Inject
	private FacesContext facesContext;

	@NotEmpty
	@Size(min = 3, message = "must have at least 3 characters")
	private String username;

	@NotEmpty
	@Size(min = 8, message = "must have at least 8 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void login() throws IOException {
		Credential credential = new UsernamePasswordCredential(username, password);
		AuthenticationStatus status = securityContext
				.authenticate(
						getHttpRequestFromFacesContext(), getHttpResponseFromFacesContext(),
						AuthenticationParameters.withParams().credential(credential));
		switch (status) {
		case SEND_CONTINUE:
			facesContext.responseComplete();
			break;
		case SEND_FAILURE:
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username and password", null));
			break;
		case SUCCESS:
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/");
			break;
		case NOT_DONE:
		}
	}

	protected HttpServletRequest getHttpRequestFromFacesContext() {
		return (HttpServletRequest) facesContext.getExternalContext().getRequest();
	}

	protected HttpServletResponse getHttpResponseFromFacesContext() {
		return (HttpServletResponse) facesContext.getExternalContext().getResponse();
	}

	public Principal getPrincipal() {
		return securityContext.getCallerPrincipal();
	}

}
