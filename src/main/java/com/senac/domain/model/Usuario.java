package com.senac.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbl_usuario")
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "matricula_usuario")
	private String matriculaUsuario;
	
	@Column(name = "email_usuario")
	private String emailUsuario;
	
	@Column(name = "senha_usuario")
	private String senhaUsuario;
	
	@Column(name = "nome_usuario")
	private String nomeUsuario;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
}
