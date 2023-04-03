package com.senac.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senac.domain.model.Usuario;

public interface UsuarioRepositoryQueries {

	Page<Usuario> carregarUsuarios(String matricula, Pageable pageable) throws Exception; 
	
}
