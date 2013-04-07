package com.mahmoudhossam.sayit;

class WordNotFoundException extends Exception {

	private static final long serialVersionUID = -2510687115408404287L;
	
	public WordNotFoundException() {
		super();
	}
	
	public WordNotFoundException(String message) {
		super(message);
	}
	
	public WordNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public WordNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}