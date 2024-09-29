package br.senac.projeto_pombo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senac.projeto_pombo.model.entity.Denuncia;
import br.senac.projeto_pombo.model.entity.DenunciaPk;

public interface DenunciaRepository extends JpaRepository<Denuncia, DenunciaPk>, JpaSpecificationExecutor<Denuncia> {
	
	@Query("SELECT d FROM Denuncia d WHERE d.idDenuncia.idPruu = :idPruu")
	List<Denuncia> findByIdPruu(@Param("idPruu") String idPruu);
}
