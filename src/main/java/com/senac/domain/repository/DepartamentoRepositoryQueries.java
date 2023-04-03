package com.senac.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senac.domain.model.Departamento;

public interface DepartamentoRepositoryQueries {

	Page<Departamento> carregarDepartamento(String nome, Pageable pageable) throws Exception; 
	
}
