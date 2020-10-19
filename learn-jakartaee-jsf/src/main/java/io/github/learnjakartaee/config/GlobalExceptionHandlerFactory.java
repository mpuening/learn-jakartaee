package io.github.learnjakartaee.config;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class GlobalExceptionHandlerFactory extends ExceptionHandlerFactory {

	public GlobalExceptionHandlerFactory(ExceptionHandlerFactory factory) {
		super(factory);
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = getWrapped().getExceptionHandler();
		handler = new GlobalExceptionHandler(handler);
		return handler;
	}
}