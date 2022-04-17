package gm.utils.jpa.criterions;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.comum.UConstantes;
import gm.utils.string.UString;

public abstract class QueryOperator_String extends QueryOperator {
	
	public QueryOperator_String(String campo, String value) {
		super(campo, value.trim(), false);
	}
	
	@Override
	public String getValue() {
		String s = (String) super.getValue();
		s = s.replace(" ", "% %");
		return percent(before()) + s + percent(after());
	}
	
	protected abstract boolean before();
	protected abstract boolean after();
	
	private String percent(boolean b) {
		return b ? "%" : "";
	}
	
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		CriteriaBuilder cb = cq.getCb();
		return cb.like(cb.lower(cq.getPath().get(campo)), getValue().toLowerCase());
	}
	@Override
	protected Predicate getPredicateFalse(CriterioQuery<?> cq, String campo) {
		CriteriaBuilder cb = cq.getCb();
		return cb.notLike(cb.lower(cq.getPath().get(campo)), getValue().toLowerCase());
	}
	
	@Override
	protected Criterion criterion() {
		/*

		//TODO Francisco
		//TODO n"+UConstantes.a_til+"o remover, pois estah em fase de aperfeicoamento
		
		String s = getReplaced();
		s = s.replace("[ESPACO]", " ");
		String campo = getCampo();
		campo = CriterioSetAlias.putAliasInEL(campo);
		S.afterFirst(campo, ".");
		s = "lower( " + campo + " ) SIMILAR TO '" + s + "'";
		return Restrictions.sqlRestriction(s);
		*/
		String s = (String) getValue();
		s = s.replace("[ESPACO]", " ");
		return Restrictions.ilike(getCampo(), s);
	}
	
	@Override
	protected String nativo() {
		String s = getReplaced();
		s = s.replace("[ESPACO]", " ");
		s = "lower( " + getCampo() + " ) SIMILAR TO '" + s + "'";
		return s;
	}
	
	private String replaced;
	
	private String getReplaced() {
		if (replaced != null) {
			return replaced;
		}
		String s = (String) getValue();
		s = s.toLowerCase();
		s = UString.removerAcentos(s);
//		s = S.removerFonetico(s);
		for (String key: replaces.keySet()) {
			String value = replaces.get(key);
			s = s.replace(key, value);
		}
		s = UString.trimPlus(s);
		s = s.replace(" ", "%");
		replaced = s;
		return replaced;
	}

	private static final Map<String, String> replaces;
	static {
		replaces = new HashMap<>();
		
		String s = "(a";
		s += "|" + UConstantes.a_agudo;
		s += "|" + UConstantes.a_crase;
		s += "|" + UConstantes.a_circunflexo;
		s += "|" + UConstantes.a_til;
		s += "|" + UConstantes.a_trema;
		s += "|" + UConstantes.a_primeira;
		replaces.put("a", s + ")");
		
		s = "(e";
		s += "|" + UConstantes.e_agudo;
		s += "|" + UConstantes.e_circunflexo;
		s += "|" + UConstantes.e_crase;
		replaces.put("e", s + ")");
		
		s = "(i";
		s += "|" + UConstantes.i_agudo;
		s += "|" + UConstantes.i_crase;
		s += "|" + UConstantes.i_circunflexo;
		s += "|" + UConstantes.i_trema;
		s += "|y";
		replaces.put("i", s + ")");
		
		s = "(o";
		s += "|" + UConstantes.o_agudo;
		s += "|" + UConstantes.o_crase;
		s += "|" + UConstantes.o_circunflexo;
		s += "|" + UConstantes.o_til;
		s += "|" + UConstantes.o_trema;
		replaces.put("o", s + ")");
		
		s = "(u";
		s += "|" + UConstantes.u_agudo;
		s += "|" + UConstantes.u_crase;
		s += "|" + UConstantes.u_circunflexo;
		s += "|" + UConstantes.u_trema;
		replaces.put("u", s + ")");
		
		s = "(c";
		s += "|" + UConstantes.cedilha;
		s += "|k";
		s += "|q";
		replaces.put("c", s + ")");
		
		s = "(n";
		s += "|" + UConstantes.n_til;
		replaces.put("n", s + ")");

		s = "(f";
		s += "|ph";
		replaces.put("f", s + ")");

		s = "(s";
		s += "|z";
		replaces.put("s", s + ")");

		s = "(x";
		s += "|ch";
		s += "|sh";
		replaces.put("x", s + ")");

		s = "(j";
		s += "|g";
		replaces.put("j", s + ")");

		s = "(j";
		s += "|g";
		replaces.put("g", s + ")");
		
	}	
}
