package gm.utils.jpa.criterions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.string.ListString;

public class QueryOperator_and extends QueryOperator {
	
	private List<QueryOperator> list;

	public QueryOperator_and() {
		this(new ArrayList<>());
	}
	public QueryOperator_and(List<QueryOperator> list) {
		super("?",null,true);
		this.list = list;
	}
	public void add(QueryOperator o) {
		list.add(o);
	}
	@Override
	protected Criterion criterion() {
		Criterion[] c = new Criterion[list.size()];
		int i = 0;
		for (QueryOperator o : list) {
			c[i++] = o.getCriterion();
		}
		return Restrictions.and(c);
	}
	
	@Override
	protected String nativo() {
		String s = "";
		for (QueryOperator qo : list) {
			s += " and ( " + qo.getNativo() + " )";
		}
		s = s.substring(5);
//		s = " (\n" + s + "\n)";
		return s;
	}
	
	@Override
	public ListString getCampos() {
		ListString l = new ListString();
		for (QueryOperator qo : list) {
			l.add(qo.getCampos());
		}
		return l;
	}
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		Predicate[] predicates = new Predicate[list.size()];
		int i = 0;
		for (QueryOperator o : list) {
			predicates[i++] = o.getPredicate(cq);
		}
		return cq.getCb().and(predicates);
	}

}
