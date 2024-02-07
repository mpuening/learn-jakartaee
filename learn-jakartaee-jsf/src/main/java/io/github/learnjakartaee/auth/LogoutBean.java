package io.github.learnjakartaee.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class LogoutBean {
	public String logout() {
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context.getRequest();
			request.logout();
			context.invalidateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/views/auth/logged-out.xhtml?faces-redirect=true";
	}
}
