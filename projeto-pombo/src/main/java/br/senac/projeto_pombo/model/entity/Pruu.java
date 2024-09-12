package br.senac.projeto_pombo.model.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPruu;
	@NotBlank(message = "Use sua criatividade! Digite algo!")
	@Size(min = 1, max = 300)
	private String mensagem;
	@CreationTimestamp
	private LocalDateTime dataHora;
	@ColumnDefault("0")
	private Integer curtidas;
	@ColumnDefault("0")
	private Integer deuncias;
	
	@ColumnDefault("false")
	private Boolean bloqueado;
	
	@ManyToOne
    @JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pruu")
	private Set<Curtida> curtidasUsuarios;
}
