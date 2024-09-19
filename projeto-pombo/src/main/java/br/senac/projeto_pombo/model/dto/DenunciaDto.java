package br.senac.projeto_pombo.model.dto;

import lombok.Data;

@Data
public class DenunciaDto {

	private String mensagem;
	private Integer qtdeCurtidas;
	private String nomeUsuarioCriacao;
	private Integer qtdeDenuncias;
	
}
