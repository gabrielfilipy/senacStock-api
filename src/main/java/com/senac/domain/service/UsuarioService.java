package com.senac.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.domain.exception.NotFoundUsuarioException;
import com.senac.domain.model.Usuario;
import com.senac.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Transactional
	public Usuario criar(Usuario usuario) throws Exception {
		
		if(usuario.getIdUsuario() != null)
			verificaExistencia(usuario.getIdUsuario());
		
		return repository.save(usuario);
		
	}
	
	public void ativarDesativarUsuario(Long idUsuario, Boolean ativo) {
		Usuario usuario = verificaExistencia(idUsuario);
		usuario.setAtivo(ativo);
		repository.save(usuario);
	}
	
	public Usuario verificaExistencia(Long idUsuario) {
		return repository.findById(idUsuario)
				.orElseThrow(() -> new NotFoundUsuarioException(idUsuario));
	}
	
}
