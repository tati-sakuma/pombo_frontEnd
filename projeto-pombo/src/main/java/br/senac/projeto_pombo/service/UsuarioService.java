package br.senac.projeto_pombo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.CurtidaRepository;
import br.senac.projeto_pombo.model.repository.PruuRepository;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;
import br.senac.projeto_pombo.model.seletor.UsuarioSeletor;

@Service
public class UsuarioService implements UserDetailsService{

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

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		return repository.findByCpf(cpf)
				.orElseThrow(
						() -> new UsernameNotFoundException("Usuário não encontrado" + cpf)
						);
	}

	public List<Usuario> pesquisarComFiltros(UsuarioSeletor usuarioSeletor) {
		if(usuarioSeletor.temFiltro() && usuarioSeletor.temPaginacao()) {
			int pageNumber = usuarioSeletor.getPagina();
			int pageSize = usuarioSeletor.getLimite();

			PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
			return repository.findAll(usuarioSeletor, pagina).toList();
		}

		return repository.findAll(usuarioSeletor);
	}

	public Usuario salvar(Usuario usuario) throws PomboException {
		if (repository.cpfExiste(usuario.getCpf())) {
			throw new PomboException("CPF já cadastrado. Efetue o login.");
		}
		return repository.save(usuario);
	}

	public Usuario atualizar(Usuario usuario) throws PomboException {
		if (usuario.getIdUsuario() == null) {
			throw new PomboException("Informe o ID do usuário");
		}
		return repository.save(usuario);
	}

	public void excluir(Integer id) throws PomboException {
		if (repository.verificarSePossuiPruu(id)) {
			throw new PomboException("Não é possível remover usuário que já criou uma postagem.");
		}
		repository.deleteById(id);
	}

	public Set<String> pruusDoUsuario(Integer idUsuario) {
		List<Pruu> pruus = pruuRepository.findbyIdUsuario(idUsuario);
		Set<String> pruusDoUsuario = new LinkedHashSet<String>();

		for (Pruu pruu : pruus) {
			pruusDoUsuario.add(pruu.getMensagem());
		}

		return pruusDoUsuario;
	}

	public void bloquearPruu(Integer idUsuario, String idPruu) throws PomboException {
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
		Set<String> idPruusCurtidosPeloUsuario = curtidaRepository.findPruuQueUsuarioCurtiu(idUsuario);
		Set<String> pruusCurtidos = new LinkedHashSet<String>();

		for (String idPruu : idPruusCurtidosPeloUsuario) {
			Pruu pruu = pruuRepository.findById(idPruu).get();

			pruusCurtidos.add(pruu.getMensagem());
		}

		return pruusCurtidos;
	}
}
