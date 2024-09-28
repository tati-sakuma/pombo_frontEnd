package br.senac.projeto_pombo.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
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

	public List<Pruu> pesquisarTodos() {
		return repository.findAllOrderedByDataHora();
	}

	public Pruu pesquisarId(String id) {
		return repository.findById(id).get();
	}

	public List<Pruu> pesquisarPorIdUsuario(Integer idUsuario) {
		return repository.findbyIdUsuario(idUsuario);
	}

	public List<Pruu> pesquisarComFiltros(PruuSeletor pruuSeletor) {
		if(pruuSeletor.temFiltro() && pruuSeletor.temPaginacao()) {
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

	public void excluir(String id) {
		repository.deleteById(id);
	}

	@Transactional
	public void novaCurtidaNoPruu(String idPruu, Integer idUsuario) throws PomboException {
		Pruu pruu = repository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não localizado!"));
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new PomboException("Usuário não localizado!"));

		CurtidaPk id = new CurtidaPk(idUsuario, idPruu);
		Curtida curtida = new Curtida();
		if (curtidaRepository.existsById(id)) {

			Integer descurtida = this.qtdeCurtidas(idPruu) - 1;
			pruu.setCurtidas(descurtida);
			updateContarCurtidas(idPruu);
			
		} else {

			curtida.setId(id);
			curtida.setPruu(pruu);
			curtida.setUsuario(usuario);

			curtidaRepository.save(curtida);
			updateContarCurtidas(idPruu);
		}
	}

	public void updateContarCurtidas(String idPruu) throws PomboException {

		Integer count = this.qtdeCurtidas(idPruu);
		Pruu pruu = repository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não localizado!"));

		pruu.setCurtidas(count);
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

}
