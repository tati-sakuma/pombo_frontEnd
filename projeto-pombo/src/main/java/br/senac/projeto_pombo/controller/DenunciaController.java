package br.senac.projeto_pombo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.dto.RelatorioDenunciaDto;
import br.senac.projeto_pombo.model.entity.Denuncia;
import br.senac.projeto_pombo.model.entity.DenunciaPk;
import br.senac.projeto_pombo.model.entity.enums.TiposDenuncia;
import br.senac.projeto_pombo.model.seletor.DenunciaSeletor;
import br.senac.projeto_pombo.service.DenunciaService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {

	@Autowired
	private DenunciaService service;
	
	@Operation(summary = "Pesquisa todas as denuncias")
	@GetMapping("/{idUsuario}")
	public List<Denuncia> buscarTodasDenuncias(@PathVariable Integer idUsuario) throws PomboException {
		
		return service.buscarTodasDenuncias(idUsuario);
	}
	
	
	@Operation(summary = "Busca uma denuncia com base no seu ID")
	@GetMapping("/{idUsuario}/{idPruu}")
	public Denuncia buscarDenuncia(@PathVariable Integer idUsuario, @PathVariable String idPruu) throws PomboException {
		
		DenunciaPk id = new DenunciaPk(idUsuario, idPruu);
		return service.buscarDenuncia(id);
	}
	
	
	@Operation(summary = "Cria nova denuncia")
	@PostMapping("/criar")
	public Denuncia criarDenuncia(@RequestParam String idPruu, @RequestParam Integer idUsuario,
			@RequestParam TiposDenuncia motivo) throws PomboException {

		return service.criarDenuncia(idPruu, idUsuario, motivo);
	}

	
	@Operation(summary = "Altera a situação de análise (true or false)")
	@PutMapping("/{idUsuario}/{idPruu}/situacao")
	public ResponseEntity<Denuncia> atualizarSituacaoDenuncia(@PathVariable Integer idUsuario,
			@PathVariable String idPruu, @RequestParam Boolean novaSituacaoDeAnalise) {
		DenunciaPk id = new DenunciaPk(idUsuario, idPruu);
		try {
			Denuncia denunciaAtualizada = service.atualizarSituacaoDenuncia(id, novaSituacaoDeAnalise);
			return ResponseEntity.ok(denunciaAtualizada);
		} catch (PomboException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	
	@Operation(summary = "Exclui denuncia com base no seu ID")
	@DeleteMapping("/{idUsuario}/{idPruu}")
	public ResponseEntity<Void> excluirDenuncia(@PathVariable Integer idUsuario, @PathVariable String idPruu) throws PomboException {
		DenunciaPk id = new DenunciaPk(idUsuario, idPruu);
		service.excluirDenuncia(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@Operation(summary = "Pesquisa com filtros")
	@PostMapping(path = "/filtro")
	public List<Denuncia> pesquisarComFiltros(@RequestBody DenunciaSeletor seletor) {
		return service.pesquisarComFiltros(seletor);
	}

	@Operation(summary = "Relatório que apresenta o id do pruu denunciado, a quantidade de denuncias e a quantidade de denuncias analisadas ou não analisadas.")
	@GetMapping("/relatorio/{idPruu}")
    public ResponseEntity<RelatorioDenunciaDto> gerarRelatorioDenuncia(@PathVariable String idPruu) throws PomboException {
		RelatorioDenunciaDto dto = service.gerarRelatorioDenuncia(idPruu);
        return ResponseEntity.ok(dto);
    }
}
