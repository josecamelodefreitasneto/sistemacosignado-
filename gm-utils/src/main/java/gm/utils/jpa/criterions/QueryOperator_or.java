package gm.utils.jpa.criterions;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QueryOperator_or extends QueryOperator {
	
	private QueryOperator left;
	private QueryOperator rigth;

	public QueryOperator_or() {
		super("?",null,true);
	}

	@Override
	protected Criterion criterion() {
		Criterion[] c = new Criterion[2];
		c[0] = left.getCriterion();
		c[1] = rigth.getCriterion();
		return Restrictions.or(c);
	}
	
	@Override
	protected String nativo() {
		return "(" + left.getNativo() + " or " + rigth.getNativo() + ")" ;
	}

	@Override
	public ListString getCampos() {
		ListString l = new ListString();
		l.add(left.getCampos());
		l.add(rigth.getCampos());
		return l;
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		return cq.getCb().or(left.getPredicate(cq), rigth.getPredicate(cq));
	}
	
}
