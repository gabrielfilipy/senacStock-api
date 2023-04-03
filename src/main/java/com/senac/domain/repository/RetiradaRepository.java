package com.senac.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senac.domain.model.Retirada;

@Repository
public interface RetiradaRepository extends JpaRepository<Retirada, Long> {

	@Query("From Retirada r where r.usuario.idUsuario = :idUsuario")
	List<Retirada> recuperar(Long idUsuario);
	
}
