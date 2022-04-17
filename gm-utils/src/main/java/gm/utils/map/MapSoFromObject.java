package gm.utils.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.comum.UType;
import gm.utils.number.Numeric;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.rest.GetMapStringString;
import gm.utils.string.UString;

public class MapSoFromObject {
	
	private final List<Object> jaComputados = new ArrayList<>();
	private final MapSO result;
	private final Map<Object, MapSO> memory = new HashMap<>();
	
	private MapSoFromObject(final Object o) {
		
		if (UType.isArray(o) || o instanceof IToList || o instanceof List) {
			this.result = new MapSO();
			trataList(result, "array", o);
		} else {
			this.result = this.exec(o);
		}
		
	}

	public static MapSO get(final Object o) {
		return new MapSoFromObject(o).result;
	}

	private static MapSO getFromMap(final Map<?, ?> map) {
		final MapSO mapSO = new MapSO();
		for (final Object key : map.keySet()) {
			mapSO.add(UString.toString(key), map.get(key));
		}
		return mapSO;
	}

	private MapSO exec(final Object o) {
		if (o == null) return null;
		
		if (o.getClass().getName().contains("$Lambda$")) {
			return null;
		}
		
		if (jaComputados.contains(o)) {
			return null;
		}
		
		jaComputados.add(o);
		
		final MapSO v = this.memory.get(o);
		if (v != null) return v;
		final MapSO x = new MapSO();
		this.memory.put(o, x);
		final MapSO y = this.exec2(o);
		x.add(y);
		return x;
	}

	private MapSO exec2(final Object o) {
		
		if (o instanceof MapSO) {
			return (MapSO) o;
		}
		if (o instanceof Map) {
			return MapSoFromObject.getFromMap((Map<?, ?>) o);
		}
		if (o instanceof GetMapStringString) {
			return MapSoFromObject.getFromMap(((GetMapStringString) o).getMapStringString());
		}
		final MapSO map = new MapSO();
		final Atributos as = ListAtributos.get(o);
		as.removeStatics();
		if (as.getId() != null) {
			as.add(0, as.getId());
		}

		for (final Atributo a : as) {
			
			if (a.getType().toString().startsWith("interface ")) {
				continue;
			}
			
//		a.getType()
			if (a.hasAnnotation(IgnoreJson.class)) {
				continue;
			}
			
			try {
				a.get(o);
			} catch (final Exception e) {
				continue;
			}
			
			final Object value = a.get(o);
//			System.out.println(a);
			if (value == null) {
				map.add(a.nome(), null);
			} else if (value instanceof byte[]) {
				map.add(a.nome(), value);
			} else if (a.isList() || UType.isArray(value) || value instanceof IToList) {
				trataList(map, a.nome(), value);
			} else if (a.isPrimitivo() || UType.isPrimitiva(value)) {
				map.add(a.nome(), value);
			} else if (value instanceof Numeric) {
				final Numeric<?> n = (Numeric<?>) value;
				map.add(a.nome(), n.toDouble());
//			} else if ("java.util.LinkedHashMap$Entry".equals(value.getClass().getName())) {
//				System.out.println(value);
			} else {
				map.add(a.nome(), this.exec(value));
			}
		}
		return map;
	}

	private void trataList(MapSO map, String nome, Object value) {
		
		List<?> list;
		
		if (UType.isArray(value)) {
			try {
				final Object[] os = (Object[]) value;
				list = Arrays.asList(os);
			} catch (final Exception e) {
				final char[] os = (char[]) value;
				list = Arrays.asList(os);
			}
		} else if (value instanceof IToList) {
			IToList toList = (IToList) value;
			list = toList.toList();
		} else {
			list = (List<?>) value;
		}
		
		if (list.isEmpty() || UType.isPrimitiva(list.get(0))) {
			map.add(nome, list);
		} else {
			final List<MapSO> lst = new ArrayList<>();
			for (final Object obj : list) {
				if (obj != null) {
					lst.add(this.exec(obj));
				}
			}
			map.add(nome, lst);
		}
		
	}
	
}
