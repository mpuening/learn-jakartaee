package io.github.learnjakartaee.config;

import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

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
						nav.handleNavigation(context, null, "/login");
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
