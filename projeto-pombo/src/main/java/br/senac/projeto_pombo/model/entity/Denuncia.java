package br.senac.projeto_pombo.model.entity;

import java.time.LocalDateTime;

import br.senac.projeto_pombo.model.entity.enums.TiposDenuncia;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
	 
	private TiposDenuncia motivo;
	private LocalDateTime dataDenuncia;
	private Boolean analisada;
}
