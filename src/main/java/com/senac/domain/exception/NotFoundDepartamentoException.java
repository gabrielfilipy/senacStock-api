package com.senac.domain.exception;

public class NotFoundDepartamentoException extends NotFoundSenacStock {

	private static final long serialVersionUID = 1L;

	public NotFoundDepartamentoException(String message) {
		super(message);
	}

	public NotFoundDepartamentoException(Long idUsuario) {
		this(String.format(MsgException.DEPARTAMENTO_NAO_EXISTE.getDesc(), idUsuario));
	}
	
}
