package br.senac.projeto_pombo.model.dto;

import lombok.Data;

@Data
public class RelatorioDenunciaDto {
	private String uuidMensagem;
    private Integer quantidadeDenuncias;
    private Integer denunciasPendentes;
    private Integer denunciasAnalisadas;
}
