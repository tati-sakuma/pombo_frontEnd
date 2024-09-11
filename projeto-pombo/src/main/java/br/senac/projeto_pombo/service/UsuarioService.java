 package br.senac.projeto_pombo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> pesquisarTodos() {
		return repository.findAll();
	}
	
	public Usuario pesquisarId(Integer id) {
		return repository.findById(id).get();
	}
	
	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}
	
	public Usuario atualizar (Usuario usuario) throws PomboException{
		if(usuario.getIdUsuario() == null) {
			throw new PomboException("Informe o ID do usuário");
		}
		return repository.save(usuario);
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
}
