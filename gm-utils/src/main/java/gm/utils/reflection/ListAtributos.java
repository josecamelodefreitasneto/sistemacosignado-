package gm.utils.reflection;
import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Lookup;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.exception.UException;
import gm.utils.string.ListString;

public class ListAtributos {
	
	private static Map<Class<?>, Atributos> list = new HashMap<>();
	private static Map<Class<?>, Atributos> persist = new HashMap<>();
	private static Map<Class<?>, Atributos> lookups = new HashMap<>();
	private static Map<Class<?>, Atributos> persistAndLookups = new HashMap<>();
	private static Map<Class<?>, Atributos> orderBy = new HashMap<>();

	private static final ListString arrayOrderBy = ListString.array("ordem","sequencia","numero","nome","titulo","descricao","enunciado","sigla");
	public static Atributos orderBy(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		Atributos as = orderBy.get(classe);
		if (as != null) {
			return as;
		}
		Atributos atributos = persist(classe, false);
		as = new Atributos();
		for (String string : arrayOrderBy) {
			if (atributos.contains(string)) {
				as.add(atributos.removeAndGet(string));
			}
		}
		as.add(atributos.getId());
		as.addAll(atributos);
		return as;
	}
	public static Atributos persistAndLookups(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		Atributos as = persistAndLookups.get(classe);
		if (as == null) {
			as = get(classe, false);
			Atributos lookups = as.getWhereAnnotation(Lookup.class);
			Atributos persistentes = persist(classe, false);
			
			Atributos as2 = get(classe, false);
			as.clear();
			for (Atributo a : as2) {
				if (lookups.contains(a) || persistentes.contains(a)) {
					as.add(a);
				}
			}
			persistAndLookups.put(classe, as);
		}
		return as.copy();
		
	}
	public static Atributos lookups(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		Atributos as = lookups.get(classe);
		if (as == null) {
			as = get(classe, false).getWhereAnnotation(Lookup.class);
			lookups.put(classe, as);
		}
		return as.copy();
	}
	public static Atributos persist(Object o) {return persist(o.getClass());}
	public static Atributos persist(Object o, boolean withId) {return persist(o.getClass(), withId);}
	public static Atributos persist(Class<?> classe) {return persist(classe, false);}
	public static Atributos persist(Class<?> classe, boolean withId) {
		UAssert.notEmpty(classe, "classe == null");
		classe = UClass.getClass(classe);
		Atributos as = persist.get(classe);
		if (as == null) {
			as = get(classe, false);
			as.removeNaoPersistiveis();
			persist.put(classe, as);
			if (as.existe("pagina")) {
				throw UException.runtime( "N\u00e3o pode haver um atributo chamado pagina pois este \u00e9 um nome reservado: " + classe.getSimpleName() );
			}
		}
		Atributos copy = as.copy();
		if (withId) {
			Atributo id = as.getId();
			if (id != null) {
				copy.add(id);
			}
		}
		return copy;
	}
	public static Atributos fks(Class<?> classe) {
		return persist(classe, false).getFks();
	}
	public static Atributos get(Object o) {return get(UClass.getClass(o), false);}
	public static Atributos get(Class<?> classe) {return get(classe, false);}
	public static Atributos get(Object o, boolean withId) {
		return get(UClass.getClass(o), withId);
	}
	public static Atributos get(Class<?> classe, boolean withId) {
		classe = UClass.getClass(classe); 
		Atributos as = list.get(classe);
		if (as == null) {
			as = new Atributos(classe);
			as.getId();
			list.put(classe, as);
		}
		Atributos copy = as.copy();
		if (withId) {
			Atributo id = as.getId();
			if (id != null) {
				copy.add(id);
			}
		}
		return copy;
	}
	public static Atributo getId(Class<?> classe) {
		return get(classe, false).getId();
	}
}
