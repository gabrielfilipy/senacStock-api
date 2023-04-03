package com.senac.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.senac.domain.model.Produto;
import com.senac.domain.repository.ProdutoRepository;
import com.senac.domain.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired ProdutoService service;
	
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public Page<Produto> buscar(String nome, Pageable pageable) throws Exception {
		return repository.carregarProdutos(nome, pageable);
	}		
	
	@GetMapping("/buscar/{idProduto}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Produto> buscar(@PathVariable Long idProduto) throws Exception {
		Produto produto = service.verificaExistencia(idProduto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Produto> criar(@RequestBody Produto produto) throws Exception {
		Produto prodCadastrado = service.criar(produto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(prodCadastrado);
	}
	
	@PostMapping("/{idProduto}/ativar-desativar")
	@ResponseStatus(HttpStatus.CREATED)
	public void ativarDesativarProduto(@PathVariable Long idProduto, 
			@RequestBody Boolean ativar) throws Exception {
		
		service.ativarDesativarProduto(idProduto, ativar);
	}
	
}
