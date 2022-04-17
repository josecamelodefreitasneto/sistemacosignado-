package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_NativeSql extends QueryOperator {
	
	public QueryOperator_NativeSql(String subQuery) {
		super("x", subQuery, false);
	}

	@Override
	protected Criterion criterion() {
		return Restrictions.sqlRestriction(getValue().toString());
	}

	@Override
	protected String nativo() {
		return getValue().toString();
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		throw new RuntimeException("implementar");
	}

}
