package com.senac.domain.exception;

public class NotFoundSenacStock extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundSenacStock(String msg) {
		super(msg);
	}
	
}
