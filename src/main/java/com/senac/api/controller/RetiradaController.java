package com.senac.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.senac.domain.model.Retirada;
import com.senac.domain.model.RetiradaProduto;
import com.senac.domain.repository.RetiradaProdutoRepository;
import com.senac.domain.repository.RetiradaRepository;
import com.senac.domain.service.RetiradaService;

@RestController
@RequestMapping("/retirada")
public class RetiradaController {

	@Autowired
	private RetiradaRepository retiradaRepository;
	
	@Autowired RetiradaProdutoRepository retiradaProdutoRepository;
	
	@Autowired 
	RetiradaService retiradaService;
	
	@GetMapping("/{idUsuario}/usuario")
	public List<Retirada> listar(@PathVariable Long idUsuario) {
		return retiradaRepository.recuperar(idUsuario);
	}
	
	@GetMapping("/{idRetirada}/produto")
	public List<RetiradaProduto> listarProdutosDaRetirada(@PathVariable Long idRetirada) {
		return retiradaProdutoRepository.recuperar(idRetirada);
	}
	
	@PostMapping("/criar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Retirada> criarRetirada(@RequestBody Retirada retirada) {
		Retirada retiradaCadastrada = retiradaService.criarRetirada(retirada);
		return ResponseEntity.status(HttpStatus.CREATED).body(retiradaCadastrada);
	}
	
	@PostMapping("/adicionar-produto")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<RetiradaProduto> adicionarProduto(Long idProduto, Integer quantidade, Long idRetirada) {
		RetiradaProduto retiradaProdutoCad = retiradaService.criarRetiraProdutos(idProduto, quantidade, idRetirada);
		return ResponseEntity.status(HttpStatus.CREATED).body(retiradaProdutoCad);
	}

	@PutMapping("/{idRetirada}/adicinar-total")
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionarTotal(@PathVariable Long idRetirada, @RequestBody Integer total) {
		retiradaService.adicionarTotalRetirada(idRetirada, total);
	}
	
}
