package com.senac.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.senac.domain.model.Departamento;
import com.senac.domain.repository.DepartamentoRepository;
import com.senac.domain.service.DepartamentoService;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {

	@Autowired
	private DepartamentoRepository repository;
	
	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public Page<Departamento> listar(String nome, Pageable pageable) throws Exception {
		return repository.carregarDepartamento(nome, pageable);
	}
	
	@GetMapping("/buscar/{idDepartamento}")
	@ResponseStatus(HttpStatus.OK)
	public Departamento listar(@PathVariable Long idDepartamento) throws Exception {
		return service.verificaExistencia(idDepartamento); 
	}
	
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Departamento> criar(@RequestBody Departamento departamento) {
		Departamento depCadastrado = service.criar(departamento);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(depCadastrado);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{idDepartamento}")
	public void deletar(@PathVariable Long idDepartamento) {
		service.deletar(idDepartamento);
	}
	
}
