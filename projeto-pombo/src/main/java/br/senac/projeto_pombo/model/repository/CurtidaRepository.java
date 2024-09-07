package br.senac.projeto_pombo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.projeto_pombo.model.entity.Curtida;
import br.senac.projeto_pombo.model.entity.CurtidaPk;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, CurtidaPk>{
	
}
