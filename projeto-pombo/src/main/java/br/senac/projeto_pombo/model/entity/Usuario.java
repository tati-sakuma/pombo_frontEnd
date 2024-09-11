package br.senac.projeto_pombo.model.entity;

import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	//@CPF
	private String cpf;
	@ColumnDefault("false")
	private Boolean administrador;
	
	@OneToMany(mappedBy="usuario")
	private Set<Pruu> pruus;
	
	@OneToMany(mappedBy = "usuario")
	private Set<Curtida> pruusCurtidos;

	
}
