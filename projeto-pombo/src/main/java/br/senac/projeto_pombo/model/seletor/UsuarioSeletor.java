package br.senac.projeto_pombo.model.seletor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.senac.projeto_pombo.exception.PomboException;
import br.senac.projeto_pombo.model.entity.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public class UsuarioSeletor extends BaseSeletor implements Specification<Usuario> {

	private String nome;
	private String email;
	private String cpf;
	
	public boolean temFiltro() {
		return (this.stringValida(this.nome))
				|| (this.stringValida(this.email))
				|| (this.stringValida(this.cpf))
				;
	} 
	
	@Override
	public Predicate toPredicate(Root<Usuario> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> condicoes = new ArrayList<>();
		
		//condição para filtrar nome, verificação se está branco ou nulo feito pelo temFiltro
		if(this.getNome().trim().length() > 0) {
			condicoes.add(cb.like(root.get("nome"), "%" + this.getNome() + "%"));
		}
		
		if(this.getEmail().trim().length() > 0) {
			condicoes.add(cb.like(root.get("email"), "%" + this.getEmail() + "%"));
		}
		
		if(this.getCpf().trim().length() > 0) {
			condicoes.add(cb.like(root.get("cpf"), "%" + this.getCpf() + "%"));
		}
		
		return cb.and(condicoes.toArray(new Predicate[0]));
	}

}
