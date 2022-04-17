package gm.utils.comum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UMap {
	public static void putIds(List<Map<String, Object>> list){
		int i = 1;
		for (Map<String, Object> map : list) {
			map.put("id", i++);
		}
	}
	
	public static List<Map<String, Object>> toMap(List<?> list) {
		List<Map<String, Object>> maps = new ArrayList<>();
		for (Object o : list) {
			if ( UType.isList(o) ) {
				throw UException.runtime("Objeto "+UConstantes.e_agudo+" uma Lista");
			}
			maps.add(toMap(o));
		}
		return maps;
	}
	
	public static Map<String, Object> toMap(Object o) {
		
		if ( UObject.isEmpty(o) ) {
			return null;
		}
		
		if ( UType.isList(o) ) {
			throw UException.runtime("Objeto "+UConstantes.e_agudo+" uma Lista");
		}
		
		Map<String, Object> result = new HashMap<>();
		
		if (o instanceof Map) {
			
			Map<?, ?> map = (Map<?, ?>) o;
			for (Object key : map.keySet()) {
				String k = UString.toString(key);
				Object v = map.get(key);
				result.put(k, v);
			}
			
			return result;
			
		}
		
		Class<Object> classe = UClass.getClass(o);
		Atributos atributos = ListAtributos.get(classe);
		atributos.removeStatics();
		
		for (Atributo a : atributos) {
			
			Object value = a.get(o);
			
			if (UObject.isEmpty(value)) {
				continue;
			}
			
			if (a.isPrimitivo()) {
				result.put(a.nome(), value);
			} else if (a.isDate()) {
				Data data = Data.to(value);
				value = data.formatTela();
				result.put(a.nome(), value);
			} else if (a.isList()) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
				List<Map<String, Object>> map = toMap(list);
				result.put(a.nome(), map);
			} else {
				result.put(a.nome(), toMap(value));
			}
			
		}
		
		return result;
	}

	public static Properties asProperties(String s, String delimiter) {
		ListString list = ListString.byDelimiter(s, delimiter);
		Properties p = new Properties();
		for (String string : list) {
			String key = UString.beforeFirst(string, "=");
			String value = UString.afterFirst(string, "=");
			p.put(key, value);
		}
		return p;
	}
	
}
