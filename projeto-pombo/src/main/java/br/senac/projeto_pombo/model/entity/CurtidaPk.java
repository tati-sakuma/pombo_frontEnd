package br.senac.projeto_pombo.model.entity;

import java.io.Serializable;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 590082665397101041L;

	@Column(name = "id_usuario")
	Integer idUsuario;
	
	@Column(name = "id_pruu")
	String idPruu;
	
}
