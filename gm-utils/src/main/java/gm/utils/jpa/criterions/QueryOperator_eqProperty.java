package gm.utils.jpa.criterions;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_eqProperty extends QueryOperator {
	
	public QueryOperator_eqProperty(String campo, String campo2) {
		super(campo, campo2, false);
	}

	@Override
	protected Criterion criterion() {
		return Restrictions.eqProperty(getCampo(), (String) getValue());
	}

	@Override
	protected String nativo() {
		return getCampo() + " = " + getValue();
	}
	
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		Path<Object> field1 = cq.getPath().get(campo);
		campo = cq.trataCampo(campo);
		Path<Object> field2 = cq.getPath().get(campo);
		return cq.getCb().equal(field1, field2);
	}

}
