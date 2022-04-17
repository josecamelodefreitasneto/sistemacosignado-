package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class QueryOperator_StringEquals extends QueryOperator {
	
	public QueryOperator_StringEquals(String campo, String value) {
		super(campo, value, false);
	}

	@Override
	protected Criterion criterion() {
		String s = (String) getValue();
		s = s.toLowerCase();
		SimpleExpression o = Restrictions.eq(getCampo(), s);
		o = o.ignoreCase();
		return o;
	}

	@Override
	protected String nativo() {
		String s = (String) getValue();
		return "lower(" + getCampo() + ") like '" + s.toLowerCase() + "'";
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		String s = (String) getValue();
		return cq.getCb().equal(cq.getCb().lower(cq.getPath().get(campo)), s.toLowerCase());
	}
	
}
