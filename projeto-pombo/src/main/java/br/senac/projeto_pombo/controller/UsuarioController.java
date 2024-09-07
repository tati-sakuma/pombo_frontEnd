package br.senac.projeto_pombo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.service.UsuarioService;

@RestController
@RequestMapping(path = "/api/pombo")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public List<Usuario> pesquisarTodos() {
		return service.pesquisarTodos();
	}
	
	@GetMapping(path = "/{idUsuario}")
	public Usuario pesquisarId(@PathVariable Integer id) {
		return service.pesquisarId(id);
	}
	
	@PostMapping
	public Usuario salvar(@RequestBody Usuario usuario) {
		return service.salvar(usuario);
	}
	
	@PutMapping
	public Usuario atualizar (@RequestBody Usuario usuario) throws PomboException{
		return service.atualizar(usuario);
	}
	
	
}
