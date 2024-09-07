package br.senac.projeto_pombo.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.senac.projeto_pombo.model.entity.Pruu;


@Repository
public interface PruuRepository extends JpaRepository<Pruu, UUID>, JpaSpecificationExecutor<Pruu> {

}
