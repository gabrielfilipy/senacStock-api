package com.senac.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.senac.domain.model.Produto;

public interface ProdutoRepositoryQueries {

	Page<Produto> carregarProdutos(String nome, Pageable pageable) throws Exception; 
	
} 
