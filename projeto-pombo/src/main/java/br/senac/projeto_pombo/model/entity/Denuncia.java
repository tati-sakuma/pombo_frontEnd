package br.senac.projeto_pombo.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import br.senac.projeto_pombo.model.entity.enums.TiposDenuncia;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Entity
@Data
public class Denuncia {

	@EmbeddedId
	DenunciaPk idDenuncia;
	
	@ManyToOne
	@MapsId("idUsuario")
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	@ManyToOne
	@MapsId("idPruu")
	@JoinColumn(name = "id_pruu")
	Pruu pruu;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TiposDenuncia motivo;
	
	@CreationTimestamp
	private LocalDateTime dataDenuncia;
	
	@ColumnDefault("false")
	private Boolean analisada = false;
	
}
