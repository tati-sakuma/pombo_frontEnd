package br.senac.projeto_pombo.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import br.senac.projeto_pombo.auth.AuthenticationService;
import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.seletor.UsuarioSeletor;
import br.senac.projeto_pombo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping(path = "/api/restrito/usuario")
@MultipartConfig(fileSizeThreshold = 10485760)
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private AuthenticationService authService;

	@Operation(summary = "Upload de Imagem para perfil de Usuário", 
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Arquivo de imagem a ser enviado", 
			required = true, 
			content = @Content(mediaType = "multipart/form-data", 
			schema = @Schema(type = "string", format = "binary"))), 
			description = "Realiza o upload de uma imagem associada a um usuário.")
	@PostMapping("/upload")
	public void fazerUploadImagem(@RequestParam MultipartFile imagem,
			@RequestParam String idUsuario) throws PomboException, IOException {

		if (imagem == null) {
			throw new PomboException("Arquivo inválido");
		}

		Integer idUsuarioConvertidoParaInteger;
		try {
			idUsuarioConvertidoParaInteger = Integer.parseInt(idUsuario);
		} catch (NumberFormatException e) {
			throw new PomboException("idUsuario inválido");
		}

		Usuario usuarioAutenticado = authService.getUsuarioAutenticado();
		if (usuarioAutenticado == null) {
			throw new PomboException("Usuário não encontrado");
		}

		if (usuarioAutenticado.getAdministrador() == false) {
			throw new PomboException("Usuário sem permissão de acesso");
		}

		service.salvarImagemPerfil(imagem, idUsuarioConvertidoParaInteger);
	}
	
	

	@Operation(summary = "Pesquisa todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso!") })
	@GetMapping
	public List<Usuario> pesquisarTodos() {
		return service.pesquisarTodos();
	}

	
	
	
	@Operation(summary = "Pesquisa um usuário com base no seu id", description = "Retorna o usuário indicado pelo id.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso!") })
	@GetMapping(path = "/{id}")
	public Usuario pesquisarId(@PathVariable Integer id) {
		return service.pesquisarId(id);
	}

	
	
	@Operation(summary = "Pesquisa com filtros")
	@PostMapping(path = "/filtro")
	public List<Usuario> pesquisarComFiltros(@RequestBody UsuarioSeletor seletor) {
		return service.pesquisarComFiltros(seletor);
	}

	
	
	@Operation(summary = "Salvar novo usuário", description = "Adiciona novo usuário", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso!"), })
	@PostMapping(path = "/salvar")
	public Usuario salvar(@RequestBody Usuario usuario) throws PomboException {
		return service.salvar(usuario);
	}
	
	

	@Operation(summary = "Atualiza usuário", description = "Envia as atualizações realizada em um usuário salvo", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso!"), })
	@PutMapping
	public Usuario atualizar(@RequestBody Usuario usuario) throws PomboException {
		return service.atualizar(usuario);
	}

	
	
	@Operation(summary = "Exclui usuário com base no id", description = "Remove o usuário indicado pelo ID enviado.", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário removido com sucesso!"), })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirId(@PathVariable Integer id) throws PomboException {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}

	
	
	@Operation(summary = "Pruus do usuario")
	@GetMapping(path = "/meusPruus/{idUsuario}")
	public Set<String> pruusDoUsuario(@PathVariable Integer idUsuario) {
		return service.pruusDoUsuario(idUsuario);
	}
	
	

	@Operation(summary = "Bloqueia o pruu.", description = "Às vezes o pombo se passa...")
	@PostMapping("/bloquear/{idPruu}")
	public void bloquearPruu(@RequestParam Integer idUsuario, @PathVariable String idPruu) throws PomboException {
		service.bloquearPruu(idUsuario, idPruu);
	}
	
	

	@Operation(summary = "Pruus curtidos pelo usuario")
	@GetMapping(path = "/curtida/{idUsuario}")
	public Set<String> pruusQueUsuarioCurtiu(@PathVariable Integer idUsuario) {
		return service.pruusQueUsuarioCurtiu(idUsuario);
	}

}
