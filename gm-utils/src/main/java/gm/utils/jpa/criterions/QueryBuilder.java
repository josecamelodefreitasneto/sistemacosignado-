package gm.utils.jpa.criterions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.jpa.UTableSchema;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class QueryBuilder {
	
	private Class<?> classe;
	private Map<String, String> campos = new HashMap<>();
	private ListString where = new ListString();
	private Map<String, String> having = new HashMap<>();
	private ListString groupBy = new ListString();
	private ListString orderBy = new ListString();
	private Map<String, String> letras = new HashMap<>();
	private Map<String, Class<?>> letraClass = new HashMap<>();
	private ListString joins = new ListString();
	private ListString result;
	private int offSet;
	private int limit;	

	public QueryBuilder(Class<?> classe) {
		this.classe = classe;
		letras.put("@", "a");
		letraClass.put("a", classe);
	}
	public void addCampo(String campo) {
		String alias = campo.replace(".", " ");
		alias = UString.toCamelCase(alias);
		addCampo(campo, alias);
	}
	public void addCampo(String campo, String alias) {
		campos.put(campo, alias);
	}
	public void addWhere(QueryOperator qo) {
		ListString campos = qo.getCampos();
		String s = "  and ( " + qo.getNativo() + " )";
		for (String campo : campos) {
			if (campo.contains(".")) {
				String letra = getLetra(campo);
				String before = UString.beforeLast(campo, ".");
				s = s.replace(" " + before + ".", " " + letra + ".");
			} else {
				s = s.replace(" " + campo + " ", " a." + campo + " ");
			}
		}
		where.add(s);
	}
	public void addWhere(List<QueryOperator> ops) {
		for (QueryOperator qo : ops) {
			addWhere(qo);
		}		
	}
	public void addHaving(String campo, String value) {
		having.put(campo, value);
	}
	public void addGroupBy(String campo) {
		groupBy.add(campo);
	}
	public void addOrderBy(String campo) {
		orderBy.add(campo);
	}
	@Override
	public String toString() {
		monta();
		return result.toString("\n");
	}
	private void monta() {
		
		if (result != null) {
			return;
		}
		
		result = new ListString();
		result.add("select");
		
		boolean first = true;
		
		Set<String> keySet = campos.keySet();
		for (String key : keySet) {

			String alias = campos.get(key);
			String before = "";
			String after = "";
			
			if (key.contains("(")) {
				before = UString.beforeFirst(key, "(") + "(";
				after = ")";
				key = UString.textoEntreFirst(key, "(", ")");
			}
			
			String letra = "a";
			if (key.contains(".")) {
				letra = getLetra(key);
				key = UString.afterLast(key, ".");
			}
			
			String s = first ? "  " : ", ";
			s += before;
			s += letra + "." + key;
			s += after;
			if (!key.equals(alias) || !before.isEmpty()) {
				s += " as " + alias;
			}
			result.add(s);
			
			first = false;
		}
		
		result.add("from " + ts(classe) + " as a");
		result.add(joins);
		result.add("where (0=0)");
		result.add(where);
		
		if (!having.isEmpty()) {
			throw UException.runtime("N"+UConstantes.a_til+"o implementado");
		}
		
		if (offSet > 0) {
			result.add(" offSet " + offSet);	
		}
		if (limit > 0) {
			result.add(" limit " + limit);	
		}
		
	}

	private String getLetra(String campo) {
		
		Class<?> classe = this.classe;
		
		String nomeCampo = UString.afterLast(campo, ".");
		campo = UString.beforeLast(campo, ".");
		String letra = letras.get(campo);
		if (letra != null) {
			classe = letraClass.get(letra);
			Atributos as = ListAtributos.get(classe);
			as.getObrig(nomeCampo);
			return letra;
		}
		
		Atributos as = ListAtributos.get(classe);
		campo += ".";
		String letraLeft = "a";
		
		while (!campo.isEmpty()) {
			
			String s = UString.beforeFirst(campo, ".");
			Atributo a = as.getObrig(s);
			classe = a.getType();
			as = ListAtributos.get(classe);
			
			String adicionado = letras.get(s);
			if (adicionado == null) {

				letra = UConstantes.letrasMinusculas.get( letras.keySet().size() );
				letras.put(s, letra);
				letraClass.put(letra, classe);
				
				s = "left join " + ts(a.getType()) + 
						" as " + letra + 
						" on " + letra + ".id = " + letraLeft + "." + a.nome();
				
				joins.add(s);
				letraLeft = letra;
			} else {
				letraLeft = adicionado;
			}
			
			campo = UString.afterFirst(campo, ".");
			if (campo.isEmpty()) {
				as.getObrig(nomeCampo);//para garantir a existencia
			}
		}
		return letraLeft;
	}
	private static String ts(Class<?> classe) {
		String ts = UTableSchema.get(classe);
		if (ts.startsWith("public.")) {
			return UString.afterFirst(ts, ".");
		} else {
			return ts;
		}
	}
}
