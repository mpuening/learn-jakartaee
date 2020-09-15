package io.github.learnjakartaee.auth;

import java.io.IOException;
import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class LoginBean {
	@Inject
	private SecurityContext securityContext;

	@Inject
	private ExternalContext externalContext;

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
		AuthenticationStatus status = securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
				(HttpServletResponse) externalContext.getResponse(),
				AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password)));
		switch (status) {
		case SEND_CONTINUE:
			facesContext.responseComplete();
			break;
		case SEND_FAILURE:
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username and password", null));
			break;
		case SUCCESS:
			externalContext.redirect(externalContext.getRequestContextPath() + "/");
			break;
		case NOT_DONE:
		}
	}

	public Principal getPrincipal() {
		return securityContext.getCallerPrincipal();
	}

}
