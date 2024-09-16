package br.senac.projeto_pombo.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class DenunciaPk {

	@Column(name = "id_usuario")
	Integer idUsuario;
	
	@Column(name = "id_pruu")
	UUID idPruu;
}
