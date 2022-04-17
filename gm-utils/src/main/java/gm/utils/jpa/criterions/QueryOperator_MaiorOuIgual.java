package gm.utils.jpa.criterions;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_MaiorOuIgual extends QueryOperator{
	public QueryOperator_MaiorOuIgual(String campo, Object value) {
		super(campo, value, false);
	}
	@Override
	protected Criterion criterion() {
		return Restrictions.ge(getCampo(), getValue());
	}
	@Override
	protected String nativo() {
		return getCampo() + " >= " + getNativeValue();
	}
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		
		CriteriaBuilder cb = cq.getCb();
		Object value = getValue();
		
		if (value instanceof Number) {
			Number number = (Number) value;
			return cb.ge(cq.getPath().get(campo), number);
		}
		
		if (value instanceof Calendar) {
			Calendar c = (Calendar) value;
			return cb.greaterThanOrEqualTo(cq.getPath().get(campo), c);
		}

		if (value instanceof Date) {
			Date c = (Date) value;
			return cb.greaterThanOrEqualTo(cq.getPath().get(campo), c);
		}
		
		throw new RuntimeException("Tipo não tratado: " + value.getClass());
		
	}
}
