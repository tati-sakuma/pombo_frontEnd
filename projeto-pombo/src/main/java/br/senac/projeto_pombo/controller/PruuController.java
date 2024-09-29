package br.senac.projeto_pombo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.senac.projeto_pombo.model.dto.RelatorioPruuDto;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.seletor.PruuSeletor;
import br.senac.projeto_pombo.service.PruuService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/api/pruu")
public class PruuController {

	@Autowired
	private PruuService service;

	@Operation(summary = "Lista todas as mensagens não excluídas")
	@GetMapping
	public List<Pruu> pesquisarTodosAtivos() {
		return service.pesquisarTodosAtivos();
	}

	@Operation(summary = "Pesquisa mensagem pelo ID")
	@GetMapping(path = "/{id}")
	public Pruu pesquisarId(@PathVariable String id) {
		return service.pesquisarId(id);
	}

	@Operation(summary = "Pesquisa mensagem pelo ID do usuário")
	@GetMapping(path = "/idusuario/{idUsuario}")
	public List<Pruu> pesquisarPorIdUsuario(@PathVariable Integer idUsuario) {
		return service.pesquisarPorIdUsuario(idUsuario);
	}

	@Operation(summary = "Pesquisa com filtros")
	@PostMapping(path = "/filtro")
	public List<Pruu> pesquisarComFiltros(@RequestBody PruuSeletor seletor) {
		return service.pesquisarComFiltros(seletor);
	}

	@Operation(summary = "Salva nova mensagem")
	@PostMapping
	public Pruu salvar(@RequestBody Pruu mensagem) {
		return service.salvar(mensagem);
	}

	@Operation(summary = "Atualiza mensagem")
	@PutMapping
	public Pruu atualizar(@RequestBody Pruu mensagem) throws PomboException {

		return service.atualizar(mensagem);
	}

	@Operation(summary = "Exclusão lógica da mensagem")
	@DeleteMapping("/excluir/{id}")
	public void excluir(@PathVariable String id) throws PomboException {
		service.excluir(id);
	}

	@Operation(summary = "Curte um pruu")
	@PostMapping("/curtida/{idPruu}")
	public void novaCurtidaNoPruu(@PathVariable String idPruu, @RequestParam Integer idUsuario) throws PomboException {
		service.novaCurtidaNoPruu(idPruu, idUsuario);
	}

	@Operation(summary = "Quantidades de curtidas")
	@GetMapping("/curtidas")
	public Integer qtdeCurtidas(String idPruu) {
		return service.qtdeCurtidas(idPruu);
	}

	@Operation(summary = "Usuários que curtiram")
	@GetMapping("/curtidas/usuarios")
	public Set<String> usuariosQueCurtiram(String idPruu) {
		return service.usuariosQueCurtiram(idPruu);
	}

	@Operation(summary = "Relatório que apresenta o pruu, a quantidade de curtidas e denuncias, o nome do usuário e o seu id.")
	 @GetMapping("/relatorio/{idPruu}")
	    public RelatorioPruuDto getRelatorio(@PathVariable String idPruu) throws PomboException {
	        return service.gerarRelatorioPruu(idPruu);
	    }
}
