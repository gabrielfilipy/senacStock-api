package com.senac.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.senac.domain.model.Retirada;
import com.senac.domain.model.RetiradaProduto;

@Repository
public interface RetiradaProdutoRepository extends JpaRepository<RetiradaProduto, Long> {

	@Query("From RetiradaProduto rp where rp.retirada.idRetirada = :idRetirada")
	List<RetiradaProduto> recuperar(Long idRetirada);
	
}
