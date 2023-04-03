package com.senac.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.domain.exception.NotFoundProdutoException;
import com.senac.domain.exception.NotFoundUsuarioException;
import com.senac.domain.model.Produto;
import com.senac.domain.model.Retirada;
import com.senac.domain.model.RetiradaProduto;
import com.senac.domain.model.Usuario;
import com.senac.domain.repository.ProdutoRepository;
import com.senac.domain.repository.RetiradaRepository;

@Service
public class RetiradaService {

	@Autowired
	private RetiradaRepository retiradaRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RetiradaProdutoService retiradaProdutoService;
	
	@Transactional
	public Retirada criarRetirada(Retirada retirada) {
		return retiradaRepository.save(retirada);
	}
	
	
	public RetiradaProduto criarRetiraProdutos(Long idProduto, Integer quantidade, Long idRetirada) {
		RetiradaProduto retiradaProdutos = new RetiradaProduto();
		Produto produtoPass = new Produto();
		Retirada retiradaPass = new Retirada();
		
		produtoPass.setIdProduto(idProduto);
		retiradaPass.setIdRetirada(idRetirada);
		
		retiradaProdutos.setProduto(produtoPass);
		retiradaProdutos.setRetirada(retiradaPass);
		
		Produto produto = produtoService.verificaExistencia(idProduto);
		
		Integer quantidadeProduto = produto.getQtdProduto();
		Integer calculo = quantidadeProduto - quantidade;
		
		produtoService.alterarQuantidade(produto.getIdProduto(), calculo);
		
		return retiradaProdutoService.criar(retiradaProdutos);
	}
	
	public void adicionarTotalRetirada(Long idRetirada, Integer total) {
		Retirada retirada = verificaExistencia(idRetirada);
		retirada.setTotal(total);
		retiradaRepository.save(retirada);
	}
	
	public Retirada verificaExistencia(Long idRetirada) {
		return retiradaRepository.findById(idRetirada)
				.orElseThrow(() -> new NotFoundProdutoException(idRetirada));
	}
	
}
