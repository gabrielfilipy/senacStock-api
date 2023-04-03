package com.senac.domain.exception;

import lombok.Getter;

@Getter
public enum MsgException {

	USUARIO_NAO_EXISTE("O usuário com o código: %d informado não existe."),
	DEPARTAMENTO_NAO_EXISTE("O departamento com o código: %d informado não existe."),
	PRODUTO_NAO_EXISTE("O produto com o código: %d informado não existe.");
	
	private String desc;
	
	private MsgException(String desc) {
		this.desc = desc; 
	}
	
}
