package br.senac.projeto_pombo.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.senac.projeto_pombo.model.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario>{
	@Query("SELECT COUNT(c) > 0 FROM Usuario c WHERE c.cpf = :cpf")
    boolean cpfExiste(@Param("cpf") String cpf);
	
	@Query("SELECT COUNT(p) > 0 FROM Pruu p WHERE p.usuario.id = :idUsuario")
    boolean verificarSePossuiPruu(@Param("idUsuario") Integer idUsuario);
	
	Optional<Usuario> findByCpf(String cpf);
	
}
