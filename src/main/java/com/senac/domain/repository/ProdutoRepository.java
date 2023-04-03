package com.senac.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.domain.model.Produto;

@Repository
public interface ProdutoRepository 
	extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

}
