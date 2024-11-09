package br.senac.projeto_pombo.model.seletor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.senac.projeto_pombo.model.entity.Denuncia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DenunciaSeletor extends BaseSeletor implements Specification<Denuncia> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8083229553486217811L;
	private String idPruu;
	private Integer idUsuario;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private Boolean analisada;
	
	public boolean temFiltro() {
		return (stringValida(this.idPruu)) 
				|| (idUsuario != null)
				|| (dataInicial != null)
				|| (dataFinal != null)
				|| (analisada != null)
				;
	}

	@Override
	public Predicate toPredicate(Root<Denuncia> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> condicoes = new ArrayList<>();
		
		if (this.getIdPruu() != null && this.getIdPruu().trim().length() > 0) {
	        condicoes.add(cb.like(root.get("pruu").get("idPruu"), "%" + this.getIdPruu() + "%")); 
	    }

        if (this.getIdUsuario() != null && this.getIdUsuario() > 0) {
            condicoes.add(cb.equal(root.get("usuario").get("idUsuario"), this.getIdUsuario())); 
        }
        
        if (this.getAnalisada() != null) {
            condicoes.add(cb.equal(root.get("analisada"), this.getAnalisada())); 
        }
        

        aplicarFiltroPeriodo(root, cb, condicoes, this.getDataInicial(), this.getDataFinal(), "dataDenuncia");
		
		return cb.and(condicoes.toArray(new Predicate[0]));
	}

}
