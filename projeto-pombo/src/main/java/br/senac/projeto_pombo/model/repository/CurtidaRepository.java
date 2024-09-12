package br.senac.projeto_pombo.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.senac.projeto_pombo.model.entity.Curtida;
import br.senac.projeto_pombo.model.entity.CurtidaPk;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, CurtidaPk>{
	@Query("SELECT COUNT(c) FROM Curtida c WHERE c.pruu.idPruu = :idPruu")
    public Integer countCurtidasByPruuId(@Param("idPruu") UUID idPruu);
}
