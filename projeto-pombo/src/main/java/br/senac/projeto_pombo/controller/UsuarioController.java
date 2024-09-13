package br.senac.projeto_pombo.controller;

import java.util.List;
import java.util.UUID;

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
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/api/pombo")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	
	@Operation(summary = "Pesquisa todos os usuários", 
			   description = "Retorna uma lista de todos os usuários cadastrados no sistema.",
			   responses = {
					@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso!")
				})
	@GetMapping
	public List<Usuario> pesquisarTodos() {
		return service.pesquisarTodos();
	}
	
	@Operation(summary = "Pesquisa um usuário com base no seu id",
			   description = "Retorna o usuário indicado pelo id.",
			   responses = {
					   @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso!")
			   })
	@GetMapping(path = "/{id}")
	public Usuario pesquisarId(@PathVariable Integer id) {
		return service.pesquisarId(id);
	}
	
	@Operation(summary = "Salvar novo usuário",
				description = "Adiciona novo usuário",
				responses = {
						@ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso!"),
				})
	@PostMapping
	public Usuario salvar(@RequestBody Usuario usuario) {
		return service.salvar(usuario);
	}
	
	@Operation(summary = "Atualiza usuário",
			description = "Envia as atualizações realizada em um usuário salvo",
			responses = {
					@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!"),
			})
	@PutMapping
	public Usuario atualizar (@RequestBody Usuario usuario) throws PomboException{
		return service.atualizar(usuario);
	}
	
	@Operation(summary = "Exclui usuário com base no id",
			description = "Remove o usuário indicado pelo ID enviado.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Usuário removido com sucesso!"),
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirId(@PathVariable Integer id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Chuta o pombo. Às vezes o pombo se passa...")
	@PostMapping("/bloquear/{idPruu}")
	public void bloquearPruu(@RequestParam Integer idUsuario, @PathVariable UUID idPruu) throws PomboException {
		service.bloquearPruu(idUsuario, idPruu);
	}
}
