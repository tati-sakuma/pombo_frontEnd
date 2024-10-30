package br.senac.projeto_pombo.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Usuario implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3937222177765226793L;

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
	
	@NotBlank(message = "Senha é obrigatória")
	@Column(nullable = false, length = 4000)
	private String senha;
	
	@ColumnDefault(value = "false")
	private Boolean administrador = false;

	@JsonBackReference
	@OneToMany(mappedBy = "usuario")
	private Set<Curtida> pruusCurtidos;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(administrador.toString()));
		
		return list;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.cpf;
	}

}
