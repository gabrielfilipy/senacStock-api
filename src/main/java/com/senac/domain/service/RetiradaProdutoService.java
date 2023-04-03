package com.senac.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.domain.model.RetiradaProduto;
import com.senac.domain.repository.RetiradaProdutoRepository;

@Service
public class RetiradaProdutoService {

	@Autowired
	private RetiradaProdutoRepository retiradaProdutoRepository;
	
	public RetiradaProduto criar(RetiradaProduto retiradaProduto) {
		return retiradaProdutoRepository.save(retiradaProduto);
	}
	
}
