package br.senac.projeto_pombo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.CurtidaRepository;
import br.senac.projeto_pombo.model.repository.PruuRepository;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private PruuRepository pruuRepository;

	@Autowired
	private CurtidaRepository curtidaRepository;

	public List<Usuario> pesquisarTodos() {
		return repository.findAll();
	}

	public Usuario pesquisarId(Integer id) {
		return repository.findById(id).get();
	}

	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}

	public Usuario atualizar(Usuario usuario) throws PomboException {
		if (usuario.getIdUsuario() == null) {
			throw new PomboException("Informe o ID do usuário");
		}
		return repository.save(usuario);
	}

	public void excluir(Integer id) {
		repository.deleteById(id);
	}

	public void bloquearPruu(Integer idUsuario, UUID idPruu) throws PomboException {
		Pruu pruu = pruuRepository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não localizado!"));
		Usuario usuario = repository.findById(idUsuario)
				.orElseThrow(() -> new PomboException("Usuário não localizado!"));

		if (usuario.getAdministrador()) {
			pruu.setBloqueado(true);
			pruuRepository.save(pruu);
		} else {
			throw new PomboException("Usuário não é administrador. Não é possível fazer o bloqueio do pruu.");
		}
	}

	public Set<String> pruusQueUsuarioCurtiu(Integer idUsuario) {
		Set<UUID> idPruusCurtidosPeloUsuario = curtidaRepository.findPruuQueUsuarioCurtiu(idUsuario);
		Set<String> pruusCurtidos = new LinkedHashSet<String>();

		for (UUID idPruu : idPruusCurtidosPeloUsuario) {
			Pruu pruu = pruuRepository.findById(idPruu).get();

			pruusCurtidos.add(pruu.getMensagem());
		}

		return pruusCurtidos;
	}
}
