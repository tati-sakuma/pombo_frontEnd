package br.senac.projeto_pombo.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Entity
@Data
public class Curtida {
	
	@EmbeddedId
	CurtidaPk id;
	
	@ManyToOne
	@MapsId("idUsuario")
	@JoinColumn(name = "id_usuario")
	Usuario usuario;
	
	@ManyToOne
	@MapsId("idPruu")
	@JoinColumn(name = "id_pruu")
	Pruu pruu;
	
	
}
