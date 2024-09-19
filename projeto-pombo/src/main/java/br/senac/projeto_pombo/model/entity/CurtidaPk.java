package br.senac.projeto_pombo.model.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CurtidaPk implements Serializable {

	@Column(name = "id_usuario")
	Integer idUsuario;
	
	@Column(name = "id_pruu")
	String idPruu;
	
}
