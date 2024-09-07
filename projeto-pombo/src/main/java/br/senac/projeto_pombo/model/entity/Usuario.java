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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Set<Pruu> getPruus() {
		return pruus;
	}

	public void setPruus(Set<Pruu> pruus) {
		this.pruus = pruus;
	}

	public Set<Curtida> getPruusCurtidos() {
		return pruusCurtidos;
	}

	public void setPruusCurtidos(Set<Curtida> pruusCurtidos) {
		this.pruusCurtidos = pruusCurtidos;
	}
	
	
	
}
