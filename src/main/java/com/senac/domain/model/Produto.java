package com.senac.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbl_produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_produto")
	private Long idProduto;
	
	@Column(name = "nome_produto")
	private String nomeProduto;
	
	@Column(name = "desc_produto")
	private String descProduto;
	
	@Column(name = "qtd_produto")
	private Integer qtdProduto;
	
	@Column(name = "valor_produto")
	private BigDecimal valorProduto;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSx")
	@CreationTimestamp
	@Column(name = "dt_cadastro_prod", columnDefinition = "datetime")
	private OffsetDateTime dtCadastroProd;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSx")
	@Column(name = "dt_vencimento_prod", columnDefinition = "datetime")
	private OffsetDateTime dtVencimentoProd;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	/*
	 * Precisa ignorar os relacionamento lazy do hibernate 
	 * porque eles vem inicialmente vazios e o jackson vai 
	 * tentar fazer o parse dele pra json/xml
	 * 
	*/
	@ManyToOne()
	@JoinColumn(name = "usuario_id", nullable = false)
	//@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Usuario usuario; 
	 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departamento_id", nullable = false)
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Departamento departamento;
	
	
	
}
