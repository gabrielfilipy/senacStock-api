package com.senac.infrastruct.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.senac.domain.model.Departamento;
import com.senac.domain.repository.DepartamentoRepositoryQueries;

@Repository
public class DepartamentoRepositoryImpl implements DepartamentoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Departamento> carregarDepartamento(String nome, Pageable pageable) throws Exception {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Departamento> criteria = builder.createQuery(Departamento.class);

		Root<Departamento> root = criteria.from(Departamento.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Departamento> query = manager.createQuery(criteria);
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), PageRequest.of(pageable.getPageNumber(), 
				pageable.getPageSize()), total(nome));
	}

	private Predicate[] criarRestricoes(String nome, CriteriaBuilder builder, Root<Departamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(nome))
			predicates.add(builder.like(root.get("nomeDepartamento"), "%" + nome + "%"));
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Departamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(String nome) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Departamento> root = criteria.from(Departamento.class);
		
		Predicate[] predicates = criarRestricoes(nome, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}
