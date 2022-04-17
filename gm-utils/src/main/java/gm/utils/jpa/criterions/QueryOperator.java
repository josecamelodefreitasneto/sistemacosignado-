package gm.utils.jpa.criterions;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.jpa.SqlNativeValue;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class QueryOperator {
	protected boolean not;
	private String campo;
	private Object value;
	
	public QueryOperator(String campo) {
		this(campo, null, true);
	}
	
	public ListString getCampos() {
		ListString list = new ListString();
		list.add(campo);
		return list;
	}
	
	public QueryOperator(String campo, Object value, boolean valorPodeSerNulo) {
		UAssert.notEmpty(campo, "O valor n"+UConstantes.a_til+"o pode ser nulo");
		if (!valorPodeSerNulo) {
			UAssert.notEmpty(value, "O valor n"+UConstantes.a_til+"o pode ser nulo");
		}
		this.campo = campo;
		this.value = value;
	}
	
	public final Criterion getCriterion() {
		Criterion c = criterion();
		if (not) {
			return Restrictions.not(c);
		} else {
			return c;
		}
	}
	
	public QueryOperator not() {
		setNot(true);
		return this;
	}
	
	protected abstract Criterion criterion();

	public final String getNativo() {
		String s = nativo();
		if (not) {
			s = "not ( " + s + " )";
		}
		return s;
	}
	
	protected abstract String nativo();
//	protected String nativo() {
//		throw UException.runtime("N"+UConstantes.a_til+"o implementado: " + getClass().getSimpleName());
//	}
	
	@Override
	public String toString() {
		String s = getClass().getSimpleName();
		s = UString.afterFirst(s, "_");
		s = getCampo() + " " + s + " " + value;
		if (not) {
			s = "not (" + s + ")";
		}
		return s;
	}
	public final String getNativeValue() {
		return SqlNativeValue.get( getValue() );
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (super.equals(obj)) {
			return true;
		}
		QueryOperator o = (QueryOperator) obj;
		if (o.isNot() != isNot()) {
			return false;
		}
		if (o.getCampo() != getCampo()) {
			return false;
		}
		if (o.getValue() != getValue()) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campo == null) ? 0 : campo.hashCode());
		result = prime * result + (not ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	private From<?, ?> root;

	public final <TT> Predicate getPredicate(CriterioQuery<?> cq) {
		
		String field = cq.trataCampo(getCampo());
		
		if (not) {
			return getPredicateFalse(cq, field);
		} else {
			return getPredicateTrue(cq, field);
		}
	}

//	protected static String getField(From<?, ?> path, String campo) {
//		if (campo.contains(".")) {
//			ListString campos = ListString.byDelimiter(campo, ".");
//			campo = campos.removeLast();
//			for (String s : campos) {
//				path = path.join(s, JoinType.LEFT);	
//			}
//		}
//		return campo;
//	}

	protected abstract Predicate getPredicateTrue(CriterioQuery<?> cq, String campo);

	protected Predicate getPredicateFalse(CriterioQuery<?> cq, String campo) {
		return cq.getCb().not(getPredicateTrue(cq, campo));
	}
	
}
