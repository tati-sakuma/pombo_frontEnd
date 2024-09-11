package br.senac.projeto_pombo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.repository.PruuRepository;

@Service
public class PruuService {

	@Autowired
	private PruuRepository repository;
	
	public List<Pruu> pesquisarTodos(){
		return repository.findAll();
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
	
	
	
}
