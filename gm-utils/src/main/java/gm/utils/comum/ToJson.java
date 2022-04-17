package gm.utils.comum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.classes.UClass;
import gm.utils.lambda.FT;
import gm.utils.lambda.FTT;
import gm.utils.lambda.FTTT;
import gm.utils.lambda.FVoidT;
import gm.utils.lambda.FVoidTT;
import gm.utils.lambda.FVoidVoid;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.rest.GetMapStringString;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Setter;

@Setter
public class ToJson {
	
	private Object o;
	private boolean nulos = false;
	private boolean first = true;
	private List<Object> os;
	
	public static ListString get(final Object o) {
		return new ToJson(new ArrayList<>(), o).get();
	}

	private ToJson(final List<Object> os, final Object o) {
		this.os = os;
		this.o = o;
	}
	
	private static String getPrimitivaValue(final Object o) {
		if (o instanceof String) {
			return "\"" + UString.toString(o) + "\"";
		}
		return UString.toString(o);
	}

//	private static int count = 0;
	
	public ListString get() {
		
		if (UType.isPrimitiva(this.o)) {
			final String s = ToJson.getPrimitivaValue(this.o);
			final ListString list = new ListString();
			list.add(s);
			return list;
		}
		
//		if (o instanceof Map) {
//			Map<?, ?> map = (Map<?, ?>) o;
//			
//		}

		if (this.os.contains(this.o)) {
			final ListString list = new ListString();
			list.add("[]");
			return list;
		}
		this.os.add(this.o);
		
		final Class<?> classe = UClass.getClass(this.o);
		final ListString list = new ListString();
		list.add("{");
		this.first = true;
		final Atributos as = ListAtributos.get(classe).filter(a -> !a.hasAnnotation(IgnoreJson.class) && !a.isStatic());
		for (final Atributo a : as) {
			
			if (a.typeIn(FT.class, FTT.class, FTTT.class, FVoidT.class, FVoidTT.class, FVoidVoid.class)) {
				continue;
			}
			
			final Object value = a.get(this.o);
			String v;
			if (value == null) {
				if (this.nulos) {
					v = "null";
				} else {
					continue;
				}
			} else if (a.isPrimitivo()) {
				v = ToJson.getPrimitivaValue(value);
			} else if (value instanceof com.google.gson.internal.LinkedTreeMap) {
				continue;
			} else if (UType.isMap(value) || this.o instanceof GetMapStringString) {
				final Map<String, Object> map = this.asMap(value);
				
				for (final String key : map.keySet()) {
					System.out.println("ToJson -> " + key);
				}
				continue;
				
			} else if (UType.isList(value)) {
				
				final List<?> lst = (List<?>) value;
				if (lst.isEmpty()) {
					v = "[]";	
				} else {
					String s = this.getVirgula();
					s += "\"" + a.nome() + "\" : [";
					
					final Object object = lst.get(0);
					
					if (UType.isPrimitiva(object)) {
						String xx = "";
						for (final Object x : lst) {
							xx += ", " + ToJson.getPrimitivaValue(x);
						}
						xx = xx.substring(2);
						s += xx + "]";
						list.add(s);
					} else {

						list.add(s);
						list.getMargem().inc();
						for (final Object x : lst) {
							list.add(new ToJson(this.os, x).get());
							if (!UList.getLast(lst).equals(x)) {
								s = list.removeLast().substring(1);
								s += ",";
								list.add(s);
							}
						}
						list.getMargem().dec();
						list.add("  ]");
						
					}
					
					continue;
				}
			} else {
				final ListString lst = new ToJson(this.os, value).get();
				if (lst.size(1)) {
					v = "{}";
				} else {
					lst.remove(0);
					lst.removeLast();
					lst.trim();
					lst.addLeft("\t");
					lst.add(0, "{");
					lst.add("   }");
					v = lst.toString("\n");
				}
			}
			
			String s = this.getVirgula();
			
			s += "\"" + a.nome() + "\" : " + v;
			list.add(s);
		}
		
		if (list.size() == 1) {
			list.clear();
			list.add("{}");
		} else {
			list.add("}");
		}
		return list;
	}

	private Map<String, Object> asMap(final Object o) {
		final Map<String, Object> map = new HashMap<>();
		if (o instanceof GetMapStringString) {
			final GetMapStringString get = (GetMapStringString) o;
			final Map<String, String> mp = get.getMapStringString();
			mp.keySet().forEach(key -> map.put(key, mp.get(key)));
		} else {
			final Map<?, ?> mp = (Map<?, ?>) o;
			mp.keySet().forEach(key -> map.put(UString.toString(key), mp.get(key)));
		}
		return map;
	}

	private String getVirgula() {
		if (this.first) {
			this.first = false;
			return "  ";
		} else {
			return ", ";
		}
	}
	
}
