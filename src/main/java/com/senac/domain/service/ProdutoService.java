package com.senac.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.domain.exception.NotFoundProdutoException;
import com.senac.domain.exception.RegraAppException;
import com.senac.domain.model.Produto;
import com.senac.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Transactional
	public Produto criar(Produto produto) {
		try {
			verificaUsuarioDepartamento(produto);
			
			return produtoRepository.save(produto);
		} catch (Exception e) {
			throw new RegraAppException("Error: " + e);
		}
	}
	
	public void ativarDesativarProduto(Long idProduto, Boolean ativo) {
		Produto produto = verificaExistencia(idProduto);
		produto.setAtivo(ativo);
		produtoRepository.save(produto);
	}
	
	public void alterarQuantidade(Long idProduto, Integer quantidade) {
		Produto produto = verificaExistencia(idProduto);
		
		if(quantidade > produto.getQtdProduto()) 
			throw new RegraAppException("Quantidade insuficiente para essa operação!"); 
		
		produto.setQtdProduto(quantidade);
		produtoRepository.save(produto);
	}

	private void verificaUsuarioDepartamento(Produto produto) {
		
		if(produto.getIdProduto() != null)
			verificaExistencia(produto.getIdProduto());
		
		usuarioService.verificaExistencia(produto.getUsuario().getIdUsuario());
		departamentoService.verificaExistencia(produto.getDepartamento().getIdDepartamento());
	}
	
	public Produto verificaExistencia(Long idProduto) {
		return produtoRepository.findById(idProduto)
				.orElseThrow(() -> new NotFoundProdutoException(idProduto));
	}
	
}
