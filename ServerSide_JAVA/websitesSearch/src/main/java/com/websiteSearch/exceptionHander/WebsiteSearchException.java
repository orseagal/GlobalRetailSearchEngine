package com.websiteSearch.exceptionHander;


import java.sql.SQLException;

public class WebsiteSearchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorType errorType;

	public WebsiteSearchException(ErrorType errorType) {
		this.errorType = errorType;

	}

	public WebsiteSearchException(String message, Throwable exception, ErrorType errorType) {
		super(message, exception);
		this.errorType = errorType;
	}

	public WebsiteSearchException(ErrorType errorType, Throwable exception) {
		super(exception);
		this.errorType = errorType;
	}

	public WebsiteSearchException(ErrorType errorType, SQLException sqlException) {
		super(sqlException);
		this.errorType = errorType;
	}

	public WebsiteSearchException(String message, ErrorType errorType) {
		super(message);
		this.errorType = errorType;

	}
	
	public WebsiteSearchException(String message,Throwable exception) {
		super(message,exception);
	

	}
	public WebsiteSearchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WebsiteSearchException(String message, Exception exception) {
		super(message,exception);
	}

	public ErrorType getErrorType() {
		return errorType;
	}

}
