package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_InNativeSubQuery extends QueryOperator {
	
	public QueryOperator_InNativeSubQuery(String campo, String subQuery) {
		super(campo, subQuery, false);
	}

	@Override
	protected Criterion criterion() {
		String s = getCampo();
		if (not) {
			s += " not";
		}
		s += " in (" + getValue() + ")";
		return Restrictions.sqlRestriction(s);
	}

	@Override
	protected String nativo() {
		String s = getCampo();
		s += " in (" + getValue() + ")";
		return s;
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		throw new RuntimeException("implementar");
	}

}
