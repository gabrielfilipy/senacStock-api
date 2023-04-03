package com.senac.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_retirada")
public class Retirada {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRetirada;
	
	@ManyToOne()
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario; 
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSx")
	@CreationTimestamp
	@Column(name = "dt_retirada", columnDefinition = "datetime")
	private OffsetDateTime dtRetirada;
	
	private Integer total;
	
	@JsonIgnore
	@OneToMany(mappedBy = "retirada")
	private List<RetiradaProduto> produtos = new ArrayList<>();
	
}
