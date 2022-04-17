package gm.utils.jpa.criterions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Order;

import gm.utils.comum.UConstantes;
import gm.utils.jpa.UTableSchema;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MontarQueryNativa {

	private Class<?> classe;
	
	private List<QueryOperator> ops;
	private Map<String, String> select;
	private int offSet;
	private int limit;
	private List<Order> orders;
	private boolean semOrderBy;
	
	public MontarQueryNativa(Class<?> classe) {
		this.classe = classe;
	}
	public MontarQueryNativa addSelect(String campo) {
		addSelect(campo, campo);
		return this;
	}
	public MontarQueryNativa addSelect(String campo, String alias) {
		if (select == null) {
			select = new HashMap<>();
		}
		select.put(campo, alias);
		result = null;
		return this;
	}
	
	private Map<String, String> letras = new HashMap<>();
	private Map<String, Class<?>> letraClass = new HashMap<>();
	private ListString joins = new ListString();
	private ListString joinsManuais = new ListString();
	private ListString where = new ListString();
	private ListString result;
	
	public void print() {
		getResult().print();
	}
	public ListString getResult() {
		if (result == null) {
			monta();
		}
		return result;
	}
	
	private MontarQueryNativa monta() {
		
		if (result != null) {
			return this;
		}
		
		letras.put("@", "a");
		letraClass.put("a", classe);

		result = new ListString();
		result.add("select distinct ");
		
		boolean first = true;
		
		
		Set<String> selectKeySet;
		
		if (select == null) {
			result.add("a.*");
			selectKeySet = null;
		} else {
			
			selectKeySet = select.keySet();
			for (String key : selectKeySet) {

				String alias = select.get(key);
				String before = "";
				String after = "";
				
//				if (key.contains("(")) {
//					before = S.beforeFirst(key, "(") + "(";
//					after = ")";
//					key = S.textoEntre(key, "(", ")");
//				}
				
				String letra = "a";
				if (key.contains(".")) {
					letra = getLetra(key, classe);
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
		}
		
		if (ops != null) {
			for (QueryOperator qo : ops) {
				ListString campos = qo.getCampos();
				String s = "  and ( " + qo.getNativo() + " )";
				s = s.replaceAll("this_", "a");
				for (String campo : campos) {

					if (campo.contains(".")) {
						String letra = getLetra(campo, classe);
						String before = UString.beforeLast(campo, ".");
						s = s.replace(" " + before + ".", " " + letra + ".");
					} else {
						s = s.replace(" " + campo + " ", " a." + campo + " ");
					}
					
				}
				where.add(s);
			}
		}
		
		result.add("from " + ts(classe) + " as a");
		result.add(joins);
		result.add(joinsManuais);
		result.add("where (0=0)");
		result.add(where);
		
		if (!semOrderBy) {
			if (orders == null) {
				result.add(" order by 1");
			} else {
				String s = "";
				result.add(" order by ");
				for (Order order : orders) {
					String x = UString.toString(order).replace(" first", "").trim();
					if (!UString.beforeFirst(x, " ").contains(".")) {
						x = "a." + x;
					}
					
					if (selectKeySet != null) {
						
						String n = UString.afterFirst(x, ".");
						if (n.contains(" ")) {
							n = UString.beforeFirst(n, " ");	
						}
						
						if (!selectKeySet.contains(n)) {
							continue;
						}
					}
					
					s += x + ", ";
				}
				result.add(s + "1");
			}			
		}
		
		if (offSet > 0) {
			result.add(" offSet " + offSet);	
		}
		if (limit > 0) {
			result.add(" limit " + limit);	
		}
		
		return this;
		
	}

	private String getLetra(String campo, Class<?> classe) {
		
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
		return classe.getSimpleName();
//		String ts = UTableSchema.get(classe);
//		if (ts.startsWith("public.")) {
//			return UString.afterFirst(ts, ".");
//		} else {
//			return ts;
//		}
	}
	private MontarQueryNativa addJoin(String meio, Class<?> classe, String on) {
		String s = " join " + UTableSchema.get(classe) + " on " + on;
		for (String string : joins) {
			if (string.endsWith(s)) {
				return this;
			}
		}
		for (String string : joinsManuais) {
			if (string.endsWith(s)) {
				return this;
			}
		}
		joinsManuais.add(meio + s);
		return this;
	}
	public MontarQueryNativa addInnerJoin(Class<?> classe, String on) {
		addJoin("inner", classe, on);
		return this;
	}
	public MontarQueryNativa addLeftJoin(Class<?> classe, String on) {
		addJoin("left", classe, on);
		return this;
	}
	public String getSql() {
		return getResult().toString("\n");
	}

}
