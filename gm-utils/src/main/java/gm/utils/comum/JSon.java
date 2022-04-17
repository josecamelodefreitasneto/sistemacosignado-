package gm.utils.comum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import gm.utils.date.UDate;
import gm.utils.exception.UException;
import gm.utils.jpa.UJpa;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric5;
import gm.utils.string.UString;

@SuppressWarnings("rawtypes")
public class JSon {

	private static JsonSerializer<java.util.Date> data = new JsonSerializer<java.util.Date>() {
		@Override
		public JsonElement serialize(java.util.Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(UDate.javaToJsDate(src));
		}
	};
	
	private static JsonSerializer<Numeric> numero = new JsonSerializer<Numeric>() {
		@Override
		public JsonElement serialize(Numeric src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive( src.getValor() );
		}
	};
	
	private static GsonBuilder builder;
	
	static {
		builder = new GsonBuilder();
		builder.registerTypeAdapter(java.util.Date.class, data);
		builder.registerTypeAdapter(java.sql.Date.class, data);
		builder.registerTypeAdapter(Numeric1.class, numero);
		builder.registerTypeAdapter(Numeric2.class, numero);
		builder.registerTypeAdapter(Numeric5.class, numero);
	}
	
	public static Gson createGson() {
		return builder.create();
	}	
	
	public static String getMap(Map<?, ?> map) {

		Set<?> set = map.keySet();

		String s = "";

		for (Object key : set) {
			try {

				Object x = map.get(key);
				if (UObject.isEmpty(x)) {
					continue;
				}
				Object o = get(x);

				if (UObject.isEmpty(o)) {
					continue;
				}

				String nome = UString.toString(key);
				// if (nome.contains(" ")) {
				nome = "\"" + nome + "\"";
				// }

				if (!o.toString().startsWith("{")) {
					o = "'" + o + "'";
				}

				s += ", " + nome + ":" + o;

			} catch (Exception e) {
				throw e;
			}

		}

		s = s.substring(2);
		s = "{" + s + "}";
		return s;

	}

	public static String get(Object o) {
		String s = toString(o);
		s = s.substring(1, s.length()-1);
		return s;
	}

	public static String getString(Object o) {
		return toString(o);
	}
	
	public static String getString(List<?> list) {
		String s = toString(list);
		s = s.substring(1, s.length()-1);
		return s;
	}

	public static String toString(Object o) {
		if (UType.isPrimitiva(o)) {
			return UString.toString(o);
		}
		return toJson(o);
	}
	
	public static String json(Object o) {
		if (o == null) {
			return "null";
		}
		String s;
		if (UType.isPrimitiva(o)) {
			s = UString.toString(o);
		} else {
			s = toJson(o);
		}
		if (s == null) {
			return "null";
		}
		return s;
	}
	
	public static String toJson(Object o) {
		ArrayList<Object> list = new ArrayList<>();
		list.add(o);
		return toJson(list);
	}

	public static String toJson(List<?> list) {

		list = UList.removeEmptys(list);

		if (UList.isEmpty(list)) {
			return "[]";
		}

		if (list.get(0).getClass().isAnnotationPresent(Table.class)) {
			try {
				UJpa.normalize(list, false);
			} catch (Exception e) {
				UException.printTrace(e);
			}
		}

		Gson gson = createGson();
		String json = gson.toJson(list);
		return json;
	}

}
