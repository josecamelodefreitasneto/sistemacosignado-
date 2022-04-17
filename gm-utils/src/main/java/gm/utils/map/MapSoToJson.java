package gm.utils.map;

import java.util.List;

import gm.utils.comum.ToJson;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.string.ListString;
import gm.utils.string.StringBox;
import gm.utils.string.UString;

public class MapSoToJson {
	
	private static String getValue(final Object v, final List<Object> jaProcessados) {

		if (v == null) {
			return "null";
		} else if (jaProcessados.contains(v)) {
			return null;
		}
		
		System.out.println("ddd");
		jaProcessados.add(v);
		
		if (v instanceof String) {
			final String x = (String) v;
			if (UString.isEmpty(x)) {
				return "null";
			} else {
				return "\""+v+"\"";
			}
		} else if (UType.isData(v)) {
			final Data data = Data.to(v);
			return "\""+data.format("[yyyy]/[mm]/[dd]")+"\"";
		} else if (UType.isArray(v)) {
			final Object[] list = (Object[]) v;
			if (list.length == 0) {
				return "[]";
			}
			final StringBox s = new StringBox("");
			for (final Object obj : list) {
				s.add(", " + MapSoToJson.getValue(obj, jaProcessados));
			}
			return "[" + s.get().substring(2) + "]";
		} else if (UType.isPrimitiva(v)) {
			return UString.toString(v);
		} else if (UType.isList(v)) {
			final List<?> list = (List<?>) v;
			if (list.isEmpty()) {
				return "[]";
			}
			final StringBox s = new StringBox("");
			for (final Object obj : list) {
				s.add(", " + MapSoToJson.getValue(obj, jaProcessados));
			}
			return "[" + s.get().substring(2) + "]";
		} else if (v instanceof MapSO) {
			final MapSO x = (MapSO) v;
			return MapSoToJson.get(x, jaProcessados);
		} else {
			return ToJson.get(v).trimPlus().toString(" ");
		}
		
	}
	
	private static String get(final MapSO map, final List<Object> jaProcessados) {

		final StringBox s = new StringBox("{");
		map.forEach((k,v) -> {
			s.add("\""+k+"\": " + MapSoToJson.getValue(v, jaProcessados) + ", ");
		});
		if (!map.isEmpty()) {
			s.set(UString.ignoreRight(s.get(), 2));
		}
		return s.get() + "}";

	}
	
	public static String get(final MapSO map) {
		final ListString list = map.asJson();
		list.trimPlus();
		String s = list.toString(" ");
		if (UString.isEmpty(s)) {
			return null;
		}
		
		String original;
		
		do {
			original = s;
			s = s.replace(" }", "}");
			s = s.replace("{ ", "{");
			s = s.replace(",}", "}");

			s = s.replace(" ]", "]");
			s = s.replace("[ ", "[");
			s = s.replace(",]", "]");
			
			s = s.replace(": \"false\"", ": false");
			s = s.replace(": \"true\"", ": true");
			s = s.replace("{}", "null");
		} while (!original.contentEquals(s));
		
		if (s.endsWith(",")) {
			s = UString.ignoreRigth(s);
		}
		return s;
	}
	
	
	public static void main(final String[] args) {
		final MapSO map = new MapSO();
		map.put("a", "a");
		MapSoToJson.get(map);
	}
	
}
