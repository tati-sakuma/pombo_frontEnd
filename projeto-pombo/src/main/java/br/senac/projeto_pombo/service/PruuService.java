package br.senac.projeto_pombo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Curtida;
import br.senac.projeto_pombo.model.entity.CurtidaPk;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.entity.Usuario;
import br.senac.projeto_pombo.model.repository.CurtidaRepository;
import br.senac.projeto_pombo.model.repository.PruuRepository;
import br.senac.projeto_pombo.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PruuService {

	@Autowired
	private PruuRepository repository;
	
	@Autowired
	private CurtidaRepository curtidaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Pruu> pesquisarTodos(){
		return repository.findAllOrderedByDataHora();
	}
	
	public Pruu pesquisarId(UUID id) {
		return repository.findById(id).get();
	}
	
	public List<Pruu> pesquisarPorIdUsuario (Integer idUsuario) {
		return repository.findbyIdUsuario(idUsuario);
	}
	
	public Pruu salvar(Pruu mensagem) {
		return repository.save(mensagem);
	}
	
	public Pruu atualizar (Pruu mensagem) throws PomboException{
		if(mensagem.getIdPruu() == null) {
			throw new PomboException("Informe o ID da mensagem");
		}
		return repository.save(mensagem);
	}
	
	public void excluir(UUID id) {
		repository.deleteById(id);
	}
	
	
	@Transactional
    public void novaCurtidaNoPruu(UUID pruuId, Integer usuarioId) throws PomboException {
        Pruu pruu = repository.findById(pruuId)
                .orElseThrow(() -> new PomboException("Pruu não localizado!"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new PomboException("Usuário não localizado!"));

        CurtidaPk id = new CurtidaPk(usuarioId, pruuId);
        if (curtidaRepository.existsById(id)) {
            throw new PomboException("Usuário já curtiu esse Pruu!");
        }

        Curtida curtida = new Curtida();
        curtida.setId(id);
        curtida.setPruu(pruu);
        curtida.setUsuario(usuario); 

        curtidaRepository.save(curtida);
        updateContarCurtidas(pruuId);
    }
	
	
	public void updateContarCurtidas(UUID idPruu) throws PomboException {
		
		Integer count = curtidaRepository.countCurtidasByPruuId(idPruu);
		Pruu pruu = repository.findById(idPruu).orElseThrow(() -> new PomboException("Pruu não localizado!"));
		
		pruu.setCurtidas(count);
		repository.save(pruu);
	}
	
	
	
	
}
