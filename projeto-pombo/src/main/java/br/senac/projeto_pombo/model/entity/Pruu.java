package br.senac.projeto_pombo.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table
public class Pruu {
	@Id
	@UuidGenerator
	private String idPruu;
	@NotBlank(message = "Use sua criatividade! Digite algo!")
	@Size(min = 1, max = 300)
	private String mensagem;
	@CreationTimestamp
	private LocalDateTime dataHoraPostagem;
	@ColumnDefault("0")
	private Integer curtidas = 0;
	@ColumnDefault("0")
	private Integer denuncias = 0;

	@ColumnDefault("false")
	private Boolean bloqueado = false;
	
	@ColumnDefault("false")
    private Boolean excluido = false;

	@ManyToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;

	@OneToMany(mappedBy = "pruu")
	private Set<Curtida> curtidasUsuarios;
}
