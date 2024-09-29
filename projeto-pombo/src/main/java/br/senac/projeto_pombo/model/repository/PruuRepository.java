package br.senac.projeto_pombo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.senac.projeto_pombo.model.entity.Pruu;


@Repository
public interface PruuRepository extends JpaRepository<Pruu, String>, JpaSpecificationExecutor<Pruu> {

	@Query("SELECT p FROM Pruu p WHERE p.usuario.id = :idUsuario ORDER BY p.dataHoraPostagem DESC")
	public List<Pruu> findbyIdUsuario(Integer idUsuario);
	
	@Query("SELECT p FROM Pruu p ORDER BY p.dataHoraPostagem DESC")
    public List<Pruu> findAllOrderedByDataHora();
	
	@Query("SELECT p FROM Pruu p WHERE p.excluido = false ORDER BY p.dataHoraPostagem DESC")
    List<Pruu> findAtivos();
}
