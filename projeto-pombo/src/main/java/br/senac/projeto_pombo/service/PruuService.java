package br.senac.projeto_pombo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.projeto_pombo.model.entity.Pruu;
import br.senac.projeto_pombo.model.repository.PruuRepository;

@Service
public class PruuService {

	@Autowired
	private PruuRepository pruuRepository;
	
	public List<Pruu> listarTodos(){
		return pruuRepository.findAll();
	}
	
	
	
}
