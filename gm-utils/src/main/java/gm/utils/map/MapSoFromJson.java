package gm.utils.map;

import java.util.ArrayList;
import java.util.List;

import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class MapSoFromJson {
	
	private static final String $STRING = "$STRING";
	private static final String $OBJ = "$OBJ";
	private static final String $ARRAY = "$ARRAY";
	private static final String ASPA = "\"";
	private static final String $QUEBRA = "$QUEBRA";
	
	private final ListString strings = new ListString();
	private final ListString objetos = new ListString();
	private final ListString arrays = new ListString();
	private final MapSO result;
	
	private MapSoFromJson(String s) {
		if (s.startsWith("[")) {
			s = "{\"array\":" + s + "}";
		}
		this.result = this.exec(s);
	}

	private MapSO exec(String s) {
		
		final MapSO o = new MapSO();
		if (UString.isEmpty(s) || s.contentEquals("{}")) {
			return o;
		}
		
		s = s.trim();
		
		if (!s.startsWith("{") || !s.endsWith("}")) {
			throw new RuntimeException("deve iniciar com '{' e terminar com '}': " + s);
		}

		s = UString.ignoreRigth(s.substring(1));
		
		s = s.replace("\\\"", "$ASPA$");
		
		while (s.contains(MapSoFromJson.ASPA)) {
			final String before = UString.beforeFirst(s, MapSoFromJson.ASPA);
			s = UString.afterFirst(s, MapSoFromJson.ASPA);
			String conteudo = UString.beforeFirst(s, MapSoFromJson.ASPA);
			s = UString.afterFirst(s, MapSoFromJson.ASPA);
			s = before + MapSoFromJson.$STRING+UNumber.format00(this.strings.size(),4)+ s;
			conteudo = conteudo.replace("\n", MapSoFromJson.$QUEBRA);
			this.strings.add(conteudo);
		}
		
		s = s.replace("$ASPA$", "'");
//		System.out.println(s);
		
		while (s.contains("{") || s.contains("[")) {
			
			boolean array;
			
			if (s.contains("[")) {
				if (s.contains("{")) {
					array = s.lastIndexOf("[") > s.lastIndexOf("{");
				} else {
					array = true;
				}
			} else {
				array = false;
			}
			
			if (array) {
				final String before = UString.beforeLast(s, "[");
				s = UString.afterLast(s, "[");
				final String conteudo = UString.beforeFirst(s, "]");
				s = UString.afterFirst(s, "]");
				s = before + MapSoFromJson.$ARRAY+UNumber.format00(this.arrays.size(),4) + s;
				this.arrays.add(conteudo);
			} else {
//				final String x = s;
				final String before = UString.beforeLast(s, "{");
				s = UString.afterLast(s, "{");
				final String conteudo = UString.beforeFirst(s, "}");
//				if (conteudo == null) {
//					System.out.println(x);
//				}
				s = UString.afterFirst(s, "}");
				s = before + MapSoFromJson.$OBJ+UNumber.format00(this.objetos.size(),4) + s;
				this.objetos.add(conteudo);
			}
			
		}
		
		s += ",";
		
		s = s.replace(",  ,", ",");
		
		while (UString.notEmpty(s)) {
			final String key = this.getReal(UString.beforeFirst(s, ":"));
			s = UString.afterFirst(s, ":").trim();
			String value = UString.beforeFirst(s, ",").trim();
			if (UString.isEmpty(this.getReal(value))) {
				o.add(key, null);
			} else if (value.equals("[]")) {
				o.add(key, new ArrayList<>());	
			} else if (value.startsWith("[")) {
				throw new RuntimeException("nao tratado");
			} else if (value.startsWith(MapSoFromJson.$ARRAY)) {
				final String x = UString.afterFirst(value, MapSoFromJson.$ARRAY);
				value = this.arrays.get(UInteger.toInt(x));
				if (UString.isEmpty(value)) {
					o.add(key, new ArrayList<>());	
				} else {
					final ListString itens = ListString.split(value, ",");
					if (itens.get(0).startsWith(MapSoFromJson.$OBJ)) {
						final List<MapSO> lst = new ArrayList<>();
						for (String ss : itens) {
							ss = this.getRealObj(ss);
							lst.add(this.exec("{"+ss+"}"));
						}
						o.add(key, lst);					
					} else if (itens.get(0).startsWith(MapSoFromJson.$STRING)) {
						final ListString lst = new ListString();
						for (final String ss : itens) {
							lst.add(this.getReal(ss));
						}
						o.add(key, lst);					
					} else {
						o.add(key, itens);
					}
				}
			} else if (value.startsWith(MapSoFromJson.$OBJ)) {
				final String real = this.getRealObj(value);
				o.add(key, this.exec("{"+real+"}"));
			} else if (value.startsWith(MapSoFromJson.$STRING)) {
				value = this.getReal(value);
				o.add(key, value);
			} else {
				o.add(key, value);
			}
			s = UString.afterFirst(s, ",").trim();
		}
		
		return o;
	}
	
	private String getRealObj(String s) {
		s = UString.afterFirst(s, MapSoFromJson.$OBJ);
		return this.objetos.get(UInteger.toInt(s));
	}
	private String getReal(String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = s.trim();
		if (s.contentEquals("null")) {
			return null;
		}
		if (s.startsWith(MapSoFromJson.$STRING)) {
			s = UString.afterFirst(s, MapSoFromJson.$STRING);
			return this.strings.get(UInteger.toInt(s)).replace(MapSoFromJson.$QUEBRA, "\n");
		}
		return s;
	}

	public static MapSO get(final String s) {
		try {
			return new MapSoFromJson(s).result;
		} catch (final Exception e) {
			System.out.println(s);
			new MapSoFromJson(s);
			throw e;
		}
	}
	
	public static void main(final String[] args) {
		MapSoFromJson.get("{\"a\":\"a \n b\"}").print();
		
	}
	
}
