package com.senac.domain.exception;

public class NotFoundProdutoException extends NotFoundSenacStock {

	private static final long serialVersionUID = 1L;

	public NotFoundProdutoException(String message) {
		super(message);
	}

	public NotFoundProdutoException(Long idUsuario) {
		this(String.format(MsgException.PRODUTO_NAO_EXISTE.getDesc(), idUsuario));
	}
	
}
