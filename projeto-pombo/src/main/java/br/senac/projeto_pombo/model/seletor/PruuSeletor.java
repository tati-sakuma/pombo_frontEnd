package br.senac.projeto_pombo.model.seletor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.senac.projeto_pombo.model.entity.Pruu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PruuSeletor extends BaseSeletor implements Specification<Pruu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7977018217724759L;
	private String mensagem;	
	private String nomeUsuario;
	private LocalDate dataInicial;
	private LocalDate dataFinal;

	public boolean temFiltro() {
		return (stringValida(this.mensagem)) 
				|| (stringValida(this.nomeUsuario)) 
				|| (dataInicial != null)
				|| (dataFinal != null)
				;
	}

	@Override
	public Predicate toPredicate(Root<Pruu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> condicoes = new ArrayList<>();

        if(this.getMensagem() != null && this.getMensagem().trim().length() > 0) {
            condicoes.add(cb.like(root.get("mensagem"), "%" + this.getMensagem() + "%"));
        }

        if(this.getNomeUsuario() != null && this.getNomeUsuario().trim().length() > 0) {
            condicoes.add(cb.like(root.get("usuario").get("nome"), "%" + this.getNomeUsuario() + "%"));
        }

        aplicarFiltroPeriodo(root, cb, condicoes, this.getDataInicial(), this.getDataFinal(), "dataHoraPostagem");

        return cb.and(condicoes.toArray(new Predicate[0]));
	}

}
