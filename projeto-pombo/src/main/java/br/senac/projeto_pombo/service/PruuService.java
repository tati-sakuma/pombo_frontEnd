package br.senac.projeto_pombo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.dto.RelatorioPruuDto;
import br.senac.projeto_pombo.model.entity.Curtida;
import br.senac.projeto_pombo.model.entity.CurtidaPk;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.CurtidaRepository;
import br.senac.projeto_pombo.model.repository.PruuRepository;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;
import br.senac.projeto_pombo.model.seletor.PruuSeletor;
import jakarta.transaction.Transactional;

@Service
public class PruuService {

	@Autowired
	private PruuRepository repository;

	@Autowired
	private CurtidaRepository curtidaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Pruu> pesquisarTodosAtivos() {
		return repository.findAtivos();
	}

	public Pruu pesquisarId(String id) {
		return repository.findById(id).get();
	}

	public List<Pruu> pesquisarPorIdUsuario(Integer idUsuario) {
		return repository.findbyIdUsuario(idUsuario);
	}

	public List<Pruu> pesquisarComFiltros(PruuSeletor pruuSeletor) {
		if (pruuSeletor.temFiltro() && pruuSeletor.temPaginacao()) {
			int pageNumber = pruuSeletor.getPagina();
			int pageSize = pruuSeletor.getLimite();

			PageRequest pagina = PageRequest.of(pageNumber - 1, pageSize);
			return repository.findAll(pruuSeletor, pagina).toList();
		}

		return repository.findAll(pruuSeletor);
	}

	public Pruu salvar(Pruu mensagem) {
		return repository.save(mensagem);
	}

	public Pruu atualizar(Pruu mensagem) throws PomboException {
		if (mensagem.getIdPruu() == null) {
			throw new PomboException("Informe o ID da mensagem");
		}
		return repository.save(mensagem);
	}

	public void excluir(String id) throws PomboException {
		Pruu pruu = repository.findById(id).orElseThrow(() -> new PomboException("Pruu não encontrado!"));

		pruu.setExcluido(true);
		repository.save(pruu);
	}

	@Transactional
	public void novaCurtidaNoPruu(String idPruu, Integer idUsuario) throws PomboException {
		Pruu pruu = repository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não localizado!"));
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new PomboException("Usuário não localizado!"));

		CurtidaPk id = new CurtidaPk(idUsuario, idPruu);

		if (curtidaRepository.existsById(id)) {

			curtidaRepository.deleteById(id);
			pruu.setCurtidas(pruu.getCurtidas() - 1);

		} else {

			Curtida curtida = new Curtida();
			curtida.setId(id);
			curtida.setPruu(pruu);
			curtida.setUsuario(usuario);

			curtidaRepository.save(curtida);
			pruu.setCurtidas(pruu.getCurtidas() + 1);
		}

		repository.save(pruu);
	}

	public Integer qtdeCurtidas(String idPruu) {
		return curtidaRepository.countCurtidasByPruuId(idPruu);
	}

	public Set<String> usuariosQueCurtiram(String idPruu) {
		Set<Integer> idUsuariosQueCurtiramOPruu = curtidaRepository.findUsuariosQueCurtiram(idPruu);

		Set<String> usuariosQCurtiram = new LinkedHashSet<String>();

		for (Integer idUsuario : idUsuariosQueCurtiramOPruu) {
			Usuario usuario = usuarioRepository.findById(idUsuario).get();

			usuariosQCurtiram.add(usuario.getNome());
		}

		return usuariosQCurtiram;
	}

	public RelatorioPruuDto gerarRelatorioPruu(String idPruu) throws PomboException {
		Pruu pruu = repository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não encontrado!"));

		RelatorioPruuDto dto = new RelatorioPruuDto();

		if (Boolean.TRUE.equals(pruu.getBloqueado())) {
			dto.setMensagem("Bloqueado pelo administrador.");
		} else {
			dto.setMensagem(pruu.getMensagem());
		}

		dto.setQtdeCurtidas(pruu.getCurtidas());
		dto.setNomeUsuarioCriacao(pruu.getUsuario().getNome());
		dto.setIdUsuarioCriacao(pruu.getUsuario().getIdUsuario());
		dto.setQtdeDenuncias(pruu.getDenuncias());

		return dto;
	}

}
