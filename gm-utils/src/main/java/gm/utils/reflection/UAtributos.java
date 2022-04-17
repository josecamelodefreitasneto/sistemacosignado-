package gm.utils.reflection;

import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.Aba;
import gm.utils.anotacoes.DependenciaCom;
import gm.utils.anotacoes.EntidadeVinculada;
import gm.utils.anotacoes.SemDependenciaCom;
import gm.utils.anotacoes.SemDependencias;
import gm.utils.classes.ListClass;
import gm.utils.comum.UCompare;
import gm.utils.jpa.IEntityHierarquica;
import gm.utils.lambda.FVoidT;
import gm.utils.string.ListString;

public class UAtributos {
	
	public static void ordenaPorTitulo(final Atributos as) {
		as.sort((a, b) -> UCompare.compare(a.getTitulo(), b.getTitulo()));
	}
	
	private static Map<Class<?>, Atributos> persistAndVirtuais = new HashMap<>();
	private static Map<Class<?>, Atributos> persists = new HashMap<>();
	
	private static Atributos get(final Class<?> classe, final Map<Class<?>, Atributos> map) {
		Atributos as = map.get(classe);
		if (as == null) {
			as = ListAtributos.persistAndLookups(classe);
			for (final Atributo a : as) {
				if (a.is(IEntityHierarquica.class) && a.eq("pai")) {
					a.setType(classe);
				}
			}		
			map.put(classe, as);			
		}
		return as.copy();
	}
	
	public static Atributos getPersists(final Class<?> classe) {
		return UAtributos.get(classe, UAtributos.persists);
	}
	public static Atributos getPersistAndVirtuais(final Class<?> classe) {
		return UAtributos.get(classe, UAtributos.persistAndVirtuais);
	}
	
	/* -------------- */
	
	public static Atributos getDependencias(final Atributo a, final ListClass classesStatus, final ListClass classesEstruturais) {
		return UAtributos.getDependencias(a, ListAtributos.fks(a.getClasse()), classesStatus, classesEstruturais);
	}
	public static Atributos getDependencias(final Atributo a, final Atributos atributos, final ListClass classesStatus, final ListClass classesEstruturais) {
		return UAtributos.getDependencias(a, atributos, classesStatus, classesEstruturais, null);
	}
	
	public static Atributos getDependencias(final Atributo a, Atributos atributos, final ListClass classesStatus, final ListClass classesEstruturais, final FVoidT<Atributos> filter) {
		
		Atributos dependencias = a.getProp("dependencias");
		
		if ( dependencias != null ) {
			return dependencias;
		}

		dependencias = new Atributos();
		a.setProp("dependencias", dependencias);
		
		if (a.isPrimitivo()) {
			return dependencias;
		}
		if (a.hasAnnotation(SemDependencias.class)) {
			return dependencias;
		}
		if (classesStatus.contains(a.getType())) {
			return dependencias;
		}
		if (a.getType().isAnnotationPresent(EntidadeVinculada.class)) {
			return dependencias;
		}
		
		final SemDependenciaCom sem = a.getAnnotation(SemDependenciaCom.class);
		final DependenciaCom com =  a.getAnnotation(DependenciaCom.class);
		final Atributos as = ListAtributos.persist(a.getType()).getFks();
		as.remove("problema");
		as.remove("excluido");
		
		as.removeByType(a.getType());
		classesStatus.forEach(classe -> as.removeByType(classe));
		classesEstruturais.forEach(classe -> as.removeByType(classe));

		atributos = atributos.getFks();
		atributos.removeByType(a.getType());
		classesStatus.forEach(classe -> as.removeByType(classe));
		classesEstruturais.forEach(classe -> as.removeByType(classe));
		
		if (filter != null) {
			filter.call(as);
		}
		
		for (final Atributo atributo : as) {
			for (final Atributo x : atributos) {
				if (!dependencias.contains(x)) {
					if (x.is(atributo.getType()) /*&& !x.eq(atributo)*/ && UAtributos.possuiDependencia(com, sem, x)) {
						dependencias.add(x);
						x.setAux(atributo.nome());
					}
				}
			}
		}
				
		return dependencias;
		
	}	
	private static boolean possuiDependencia(final DependenciaCom com, final SemDependenciaCom sem, final Atributo x) {
		if (sem != null) {
			return UAtributos.possuiDependenciaSem(sem, x);
		} else if (com != null) {
			return UAtributos.possuiDependenciaCom(com, x);
		} else {
			return true;
		}
	}
	private static boolean possuiDependenciaSem(final SemDependenciaCom sem, final Atributo x) {
		final String value = sem.value();
		if ( value.equalsIgnoreCase(x.nome()) ) {
			return false;
		}
		final ListString list = ListString.byDelimiter(value, ",");
		list.trimPlus();
		return !list.containsIgnoreCase(x.nome());
	}
	private static boolean possuiDependenciaCom(final DependenciaCom com, final Atributo x) {
		final String value = com.value();
		if ( value.equalsIgnoreCase(x.nome()) ) {
			return true;
		}
		final ListString list = ListString.byDelimiter(value, ",");
		list.trimPlus();
		return list.containsIgnoreCase(x.nome());
	}
	
	public static String getAba(final Atributo a) {
		
		String value = a.getProp("aba");
		
		if (value != null) {
			return value;
		}

		if (a.isId()) {
			value = "Sistema";
		} else {
			
			Aba aba = null;
			final Atributos as = ListAtributos.get(a.getClasse());
			for (final Atributo at : as) {
				final Aba annotation = at.getAnnotation(Aba.class);
				if (annotation != null) {
					aba = annotation;
				}
				if (at == a) {
					break;
				}
			}
			
			if (aba == null) {
				value = "Geral";	
			} else {
				value = aba.value();
			}
			
		}
		
		UAtributos.setAba(a, value);
		
		return value;
	}
	
	
	public static Atributos getWhereAbaGeral(final Atributos as) {
		return UAtributos.getWhereAba(as, "Geral");
	}

	public static Atributos getWhereAba(final Atributos as, final String... nomes) {
		final Atributos list = new Atributos();
		list.setId(as.getId());
		for (final Atributo a : as) {
			final String aba = UAtributos.getAba(a);
			for (final String s : nomes) {
				if (aba.equalsIgnoreCase(s)) {
					list.add(a);
					break;
				}
			}
		}
		return list;
	}

	public static void setAba(final Atributo a, final String nome) {
		a.setProp("aba", nome);		
	}	
	
}
