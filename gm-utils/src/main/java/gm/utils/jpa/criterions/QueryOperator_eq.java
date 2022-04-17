package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.abstrato.IdObject;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.number.UInteger;

public class QueryOperator_eq extends QueryOperator {
	
	public QueryOperator_eq(String campo, Object value) {
		super(campo, value, false);
	}

	@Override
	protected Criterion criterion() {
		return Restrictions.eq(getCampo(), getValue());
	}

	@Override
	protected String nativo() {
		Object o = getValue();
		
		if (o instanceof IdObject) {
			IdObject x = (IdObject) o;
			Integer id = x.getId();
			return getCampo() + " = " + id;
		}
		if (UType.isData(o)) {
			Data data = Data.to(o);
			return getCampo() + " = " + data.format_sql(true);
		}
		if (UInteger.isInt(o)) {
			return getCampo() + " = " + o;
		}
		return getCampo() + " = " + o;
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		return cq.getCb().equal(cq.getPath().get(campo), getValue());
	}

}
