package com.senac.api.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.senac.core.util.Gerador;
import com.senac.domain.model.Usuario;
import com.senac.domain.repository.UsuarioRepository;
import com.senac.domain.service.EmailService;
import com.senac.domain.service.EmailService.Mensagem;
import com.senac.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController { 
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private Gerador gerador;
	
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public Page<Usuario> listar(String matricula, Pageable pageable) throws Exception {
		return repository.carregarUsuarios(matricula, pageable);
	} 
	
	@GetMapping("/buscar/{idUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public Usuario buscar(@PathVariable Long idUsuario) throws Exception {
		return service.verificaExistencia(idUsuario);
	} 
	
	@Transactional
	@PostMapping("/cadastrar")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) throws Exception {
		
		if (!StringUtils.hasLength(usuario.getMatriculaUsuario())) {
			
			gerador.sigla(repository.count());
			String matricula = gerador.sigla();
			String senha = gerador.senha();
			
			usuario.setMatriculaUsuario(matricula);
			usuario.setSenhaUsuario(senha);
			
			//Envia ao E-mail do usuário sua matrícula e senha.
			Mensagem mensagem = Mensagem.builder()
					.assunto("Teste assunto")
					.corpo("cadastro-usuario.html")
					.modelo("matricula", matricula)
					.modelo("senha", senha)
					.modelo("nome", usuario.getNomeUsuario())
					.destinatario(usuario.getEmailUsuario())
					.build();
					
			emailService.enviar(mensagem);
		}
		
		Usuario usuCadastrado = service.criar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuCadastrado);
	}
	
	@PostMapping("/{idUsuario}/ativar-desativar")
	@ResponseStatus(HttpStatus.CREATED)
	public void ativarDesativarUsuario(@PathVariable Long idUsuario, 
			@RequestBody Boolean ativar) throws Exception {
		
		service.ativarDesativarUsuario(idUsuario, ativar);
	}

}
