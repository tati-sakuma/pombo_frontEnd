package br.senac.projeto_pombo.model.seletor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.senac.projeto_pombo.model.entity.Usuario;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioSeletor extends BaseSeletor implements Specification<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 532804053798933095L;
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
		
		if(this.getNome() != null && !this.getNome().trim().isEmpty()) {
			condicoes.add(cb.like(root.get("nome"), "%" + this.getNome() + "%"));
		}
		
		if(this.getEmail() != null && this.getEmail().trim().length() > 0) {
			condicoes.add(cb.like(root.get("email"), "%" + this.getEmail() + "%"));
		}
		
		if(this.getCpf() != null && this.getCpf().trim().length() > 0) {
			condicoes.add(cb.like(root.get("cpf"), "%" + this.getCpf() + "%"));
		}
		
		return cb.and(condicoes.toArray(new Predicate[0]));
	}  

}
