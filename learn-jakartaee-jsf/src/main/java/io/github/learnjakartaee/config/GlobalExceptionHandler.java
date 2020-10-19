package io.github.learnjakartaee.config;

import java.util.Iterator;
import jakarta.faces.FacesException;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

public class GlobalExceptionHandler extends ExceptionHandlerWrapper {

	public GlobalExceptionHandler(ExceptionHandler handler) {
		super(handler);
	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> queue = getUnhandledExceptionQueuedEvents().iterator();

		while (queue.hasNext()) {
			ExceptionQueuedEvent item = queue.next();
			ExceptionQueuedEventContext exceptionQueuedEventContext = (ExceptionQueuedEventContext) item.getSource();

			try {
				Throwable throwable = exceptionQueuedEventContext.getException();
				FacesContext context = FacesContext.getCurrentInstance();
				// Map<String, Object> requestMap =
				// context.getExternalContext().getRequestMap();
				// requestMap.put("error-message", throwable.getMessage());
				// requestMap.put("error-stack", throwable.getStackTrace());

				if (context != null && context.getApplication() != null) {
					NavigationHandler nav = context.getApplication().getNavigationHandler();
					if (throwable instanceof ViewExpiredException) {
						nav.handleNavigation(context, null, "/views/auth/login");
					} else {
						throwable.printStackTrace();
						nav.handleNavigation(context, null, "/views/errors/500");
					}
					context.renderResponse();
				}
			} finally {
				queue.remove();
			}
		}
	}
}
