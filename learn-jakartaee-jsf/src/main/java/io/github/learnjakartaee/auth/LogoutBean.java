package io.github.learnjakartaee.auth;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
		return "/views/auth/login.xhtml?logout=&faces-redirect=true";
	}
}
