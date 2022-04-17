package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_isNull extends QueryOperator {
	public QueryOperator_isNull(String campo) {
		super(campo);
	}
	@Override
	protected Criterion criterion() {
		return Restrictions.isNull(getCampo());
	}
	@Override
	protected String nativo() {
		return getCampo() + " is null";
	}
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		return cq.getCb().isNull(cq.getPath().get(campo));
	}
	@Override
	protected Predicate getPredicateFalse(CriterioQuery<?> cq, String campo) {
		return cq.getCb().isNotNull(cq.getPath().get(campo));
	}
}
