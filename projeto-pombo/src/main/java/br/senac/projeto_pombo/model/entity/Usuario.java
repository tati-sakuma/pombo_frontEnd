package br.senac.projeto_pombo.model.entity;

import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUsuario;
	
	@Column(nullable = false)
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 255)
	private String nome;
	
	@NotBlank(message = "E-mail é obrigatório")
	@Column(nullable = false)
	@Email
	private String email;
	
	@NotBlank(message = "CPF é obrigatório")
	@Size(min = 11, max = 11, message = "Escreva seu cpf sem pontos ou traços. Máximo 11 caracteres.")
	@Column(nullable = false)
	@CPF
	private String cpf;
	
	@ColumnDefault(value = "false")
	private Boolean administrador = false;

	@JsonBackReference
	@OneToMany(mappedBy = "usuario")
	private Set<Curtida> pruusCurtidos;

}
