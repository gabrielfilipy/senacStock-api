package com.senac.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.domain.exception.NotFoundDepartamentoException;
import com.senac.domain.model.Departamento;
import com.senac.domain.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository repository;
	
	@Transactional
	public Departamento criar(Departamento departamento) {
		
		if(departamento.getIdDepartamento() != null)
			verificaExistencia(departamento.getIdDepartamento());
		
		return repository.save(departamento);
		
	}
	
	@Transactional
	public void deletar(Long idDepartamento) {
		verificaExistencia(idDepartamento);
		
		repository.deleteById(idDepartamento);
	}
	
	public Departamento verificaExistencia(Long idDepartamento) {
		return repository.findById(idDepartamento)
				.orElseThrow(() -> new NotFoundDepartamentoException(idDepartamento));
	}
	
}
