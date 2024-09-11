package br.senac.projeto_pombo.model.seletor;

import org.springframework.data.jpa.domain.Specification;

import br.senac.projeto_pombo.model.entity.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class UsuarioSeletor implements Specification<Usuario> {

	private String nome;
	private String email;
	private String cpf;
	private Boolean administrador;
	
	@Override
	public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
	
		
		return null;
	}

}
