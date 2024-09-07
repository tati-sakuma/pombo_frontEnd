package br.senac.projeto_pombo.model.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CurtidaPk implements Serializable {

	@Column(name = "id_usuario")
	Integer idUsuario;
	
	@Column(name = "id_pruu")
	UUID idPruu;
}
