package com.senac.domain.exception;

public class RegraAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegraAppException(String message) {
		super(message);
	}
	
	public RegraAppException(String messsage, Throwable cases) {
		super(messsage, cases);
	}
	
}
