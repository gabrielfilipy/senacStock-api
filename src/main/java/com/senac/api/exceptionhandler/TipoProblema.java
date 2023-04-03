package com.senac.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TipoProblema {

	DADOS_INVALIDOS("/dados-invalidos", "Invalid data"), 
	
	ERRO_DE_SISTEMA("/erro-de-sistema", "Internal system error. Please contact technical support"),
	
	PARAMETRO_INVALIDO("/parametro-invalido", "Invalid parameter"),
	
	MENSAGEM_INCONPREENSIVEL("/mensagem-incompreensivel", "Incomprehensible message"),
	
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Resource not found"),
	
	ENTIDADE_EM_USO("/entidade-em-uso", "Entity in use"),
	
	ACESSO_NEGADO("/acesso-negado", "Access denied"),
	
	ERRO_NEGOCIO("/erro-negocio", "Business rule violation");
	
	private String title;
	private String uri;
	
	TipoProblema(String caminho, String title) {
		this.uri = "http://localhost:8080/rest" + caminho;
		this.title = title;
	}
	
}
