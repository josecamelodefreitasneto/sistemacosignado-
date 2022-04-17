package gm.utils.comum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.UString;

public class ToProperties {
	
	private Map<String, Object> map = new LinkedHashMap<>();
	private boolean nulls;
	private List<Object> jaTratados = new ArrayList<>();
	
	private ToProperties(Object o, boolean nulls) {
		this.nulls = nulls;
		if (o == null) {
			return;
		}
		if (UType.isPrimitiva(o)) {
			map.put("?", o);
		} else {
			exec(o, "");
		}
		
	}
	private void exec(Object o, String prefixo) {
		if (o == null) {
			return;
		}
		if (UType.isPrimitiva(o)) {
			if (prefixo.endsWith(".")) {
				prefixo = UString.ignoreRigth(prefixo);
			}
			map.put(prefixo, o);
			return;
		}
		if (jaTratados.contains(o)) {
			return;
		}
		jaTratados.add(o);
		if (o instanceof Iterable) {
			Iterable<?> list = (Iterable<?>) o;
			exec(list, UString.ignoreRigth(prefixo));
			return;
		}
		
		Atributos as = ListAtributos.get(o);
		for (Atributo a : as) {
			Object value = a.get(o);
			if (value == null) {
				if (nulls) {
					map.put(prefixo + a.nome(), value);
				}
				continue;
			} else if (a.isPrimitivo()) {
				map.put(prefixo + a.nome(), value);
			} else {
				exec(value, prefixo + a.nome() + ".");		
			}
		}
	}
	
	private void exec(Iterable<?> list, String prefixo) {
		
		
		int index = 0;
		Iterator<?> iterator = list.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			exec(next, prefixo + "["+(index++)+"].");
		}
		if (index == 0) {
			map.put(prefixo, "VAZIO");
		}
	}

	public static Map<String, Object> exec(Object o, boolean nulls) {
		return new ToProperties(o, nulls).map;
	}
	
	public static void main(String[] args) {
		Exemplo e = new Exemplo();
		e.e = new Exemplo();
		e.e.list = new ArrayList<>();
		e.e.list.add(new Exemplo());
		
		Map<String, Object> exec = exec(e, true);
		for (String key : exec.keySet()) {
			System.out.println(key + " : " + exec.get(key));
		}
	}
	
}
