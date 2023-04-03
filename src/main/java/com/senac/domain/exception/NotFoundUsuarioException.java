package com.senac.domain.exception;

public class NotFoundUsuarioException extends NotFoundSenacStock {

	private static final long serialVersionUID = 1L;

	public NotFoundUsuarioException(String message) {
		super(message);
	}

	public NotFoundUsuarioException(Long idUsuario) {
		this(String.format(MsgException.USUARIO_NAO_EXISTE.getDesc(), idUsuario));
	}
	
}
