package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;

public class QueryOperator_InSubSelect extends QueryOperator {
	
	private Class<?> classe;

	public QueryOperator_InSubSelect(Class<?> classe, String campo) {
		super(campo);
		this.classe = classe;
	}

	@Override
	protected Criterion criterion() {
		DetachedCriteria dc = DetachedCriteria.forClass(classe).setProjection(Property.forName(getCampo()));
		Criterion in = Property.forName("id").in(dc);
		return in;
	}

	@Override
	protected String nativo() {
		throw UException.runtime("N"+UConstantes.a_til+"o implementado");
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
//		TODO ver exemplo
//		https://www.baeldung.com/jpa-criteria-api-in-expressions
		throw new RuntimeException("implementar");
	}

}
