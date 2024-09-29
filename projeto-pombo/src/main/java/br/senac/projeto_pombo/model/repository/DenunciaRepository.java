package br.senac.projeto_pombo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.senac.projeto_pombo.model.entity.Denuncia;
import br.senac.projeto_pombo.model.entity.DenunciaPk;

public interface DenunciaRepository extends JpaRepository<Denuncia, DenunciaPk>, JpaSpecificationExecutor<Denuncia> {

}
