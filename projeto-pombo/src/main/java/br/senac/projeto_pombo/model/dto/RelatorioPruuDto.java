package br.senac.projeto_pombo.model.dto;

import lombok.Data;

@Data
public class RelatorioPruuDto {

	private String mensagem;
	private Integer qtdeCurtidas;
	private Integer idUsuarioCriacao;
	private String nomeUsuarioCriacao;
	private Integer qtdeDenuncias;
	
}
