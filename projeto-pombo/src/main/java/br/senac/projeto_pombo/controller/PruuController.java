package br.senac.projeto_pombo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.service.PruuService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/api/pruu")
public class PruuController {
	
	
	@Autowired
	private PruuService service;
	
	@Operation(summary = "Lista todas as mensagens")
	@GetMapping
	public List<Pruu> pesquisarTodos() {
		return service.pesquisarTodos();
	}
	
	@Operation(summary = "Pesquisa mensagem pelo ID")
	@GetMapping(path = "/{id}")
	public Pruu pesquisarId(@PathVariable UUID id) {
		return service.pesquisarId(id);
	}
	
	
	@Operation(summary = "Pesquisa mensagem pelo ID do usuário")
	@GetMapping(path = "/{idUsuario}")
	public List<Pruu> pesquisarPorIdUsuario(@PathVariable Integer idUsuario) {
		return service.pesquisarPorIdUsuario(idUsuario);
	}
	
	@Operation(summary = "Salva nova mensagem")
	@PostMapping
	public Pruu salvar(@RequestBody Pruu mensagem) {
		return service.salvar(mensagem);
	}
	
	@Operation(summary = "Atualiza mensagem")
	@PutMapping
	public Pruu atualizar (@RequestBody Pruu mensagem) throws PomboException{
		
		return service.atualizar(mensagem);
	}
	
	@Operation(summary = "Exclui mensagem")
	@DeleteMapping("/{id}")
	public void excluir(@PathVariable UUID id) {
		service.excluir(id);
	}

}
