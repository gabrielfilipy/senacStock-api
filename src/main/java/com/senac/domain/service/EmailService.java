package com.senac.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EmailService {

	void enviar(Mensagem Mensagem);
	
	@Getter
	@Builder
	class Mensagem {
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular("modelo")
		private Map<String, Object> modelos;
		
	}
	
}
