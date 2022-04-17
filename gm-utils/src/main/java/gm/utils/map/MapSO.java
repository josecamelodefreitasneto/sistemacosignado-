package gm.utils.map;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.lambda.FTTT;
import gm.utils.number.ListInteger;
import gm.utils.number.Numeric15;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric3;
import gm.utils.number.Numeric4;
import gm.utils.number.Numeric5;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UDouble;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.Parametro;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class MapSO extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	@IgnoreJson
	private MapSO pai;
	
	public MapSO() {}
	
	public MapSO(final Map<String, Object> map) {
		this.add(map);
	}
	public static <T> T get(MapSO map, final String... keys){
		for (int i = 0; i < keys.length-1; i++) {
			final String key = keys[i];
			final Map<String, Object> mp = map.get(key);
			map = MapSO.toMapSO(mp);
		}
		final String key = keys[keys.length-1];
		return map.get(key);	
	}
	public <T> T get(final String... keys){
		return MapSO.get(this, keys);
	}
	public static MapSO toMapSO(final Map<String, Object> map) {
		if (map instanceof MapSO) {
			return (MapSO) map;
		} else {
			return new MapSO(map);
		}
	}	
	public ListInteger getInts(final String... keys){
		final Object o = this.get(keys);
		if (o == null) {
			return null;
		}
		if (o instanceof ListInteger) {
			return (ListInteger) o;
		}
		if (o instanceof Object[]) {
			final Object[] os = this.get(keys);
			final ListInteger list = new ListInteger();
			for (final Object i : os) {
				list.add(UInteger.toInt(i));
			}
			return list;
		}
		throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel converver o objeto para um ListInteger: " + o.getClass().getSimpleName());
	}
	public Integer getInt(final String key){
		return UInteger.toInt(this.get(key));
	}
	public Long getLong(final String key){
		return ULong.toLong(this.get(key));
	}
	public Integer getInt(final String key, final Integer def){
		return UInteger.toInt(this.get(key), def);
	}
	public String getString(final String key){
		return UString.toString(this.get(key));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final String key){
		Object o = super.get(key);
		if (o == null) {
			final Set<String> keySet = keySet();
			for (final String s : keySet) {
				if (s.equalsIgnoreCase(key)) {
					o = super.get(s);
					break;
				}
			}
		}
		return (T) o;
	}
	public <T> T getObrig(final String key){
		final T o = this.get(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}
	@Override
	public Object put(final String key, final Object value) {
		if (key == null) {
			throw UException.runtime("key == null");
		}
		return super.put(key, value);
	}
	public MapSO add(final String key, final Object value){
		put(key, value);
		return this;
	}
	public MapSO add(final Params params) {
		this.add(params.getMap());
		return this;
	}
	public MapSO add(final Map<String, Object> map) {
		final Set<String> ks = map.keySet();
		for (final String key : ks) {
			final Object value = map.get(key);
			put(key, value);
		}
		return this;
	}
	public <T> T as(final Class<T> classe) {
		final T o = UClass.newInstance(classe);
		setInto(o, false);
		return o;
	}
	
	public void setInto(final Object o, final boolean clear) {
		final Class<Object> classe = UClass.getClass(o);
		setInto(o, classe, clear);
	}
	public void setInto(final Object o, Class<?> classe, final boolean clear) {
		final Metodos metodos = ListMetodos.get(classe);
		final Atributos as = ListAtributos.get(classe);
		final Atributo id = as.getId();
		if (id != null) {
			as.add(0, id);
		}
		final Set<String> keys = keySet();
		for (final String key : keys) {
			
			Object value = this.get(key);
			
			final Atributo a = as.get(key);
			if (a != null) {
				if (value == null) {
					if (clear) {
						a.set(o, null);
					}
				} else if (a.isPrimitivo()){
					a.set(o, value);
				} else {
					if (value instanceof MapSO && !a.getType().equals(MapSO.class)) {
						final MapSO map = (MapSO) value;
						try {
							value = map.as(a.getType());
						} catch (final Exception e) {
							throw new RuntimeException(e);
						}
					}
					a.set(o, value);
				}
			} else {
				final Metodo metodo = metodos.get("set" + key);
				if (metodo != null) {
					final Parametro p = metodo.getParametros().get(0);
					if (value == null) {
						if (clear) {
							metodo.invoke(o, value);
						}
					} else if (UType.isPrimitiva(p.getType())){
						metodo.invoke(o, value);
					} else {
						if (value instanceof MapSO && !p.getType().equals(MapSO.class)) {
							final MapSO map = (MapSO) value;
							value = map.as(p.getType());
						}
						metodo.invoke(o, value);
					}
				}
				
			}
		}
	}
	public void print() {
		final Set<String> keys = keySet();
		for (final String key : keys) {
			final Object value = this.get(key);
			System.out.println(key + ": " + value);
		}
	}
	public ListString asJson() {
		return this.asJson(new ArrayList<>());
	}
	private ListString asJson(final List<MapSO> jaProcessados) {
		final ListString list = new ListString();
		if (jaProcessados.contains(this)) {
			return list;
		}
		jaProcessados.add(this);
		list.add("{");
		list.margemInc();
		final Set<String> keys = keySet();
		for (final String key : keys) {
			final Object value = this.get(key);
			if (value == null) {
				list.add("\"" + key + "\": null,");
			} else if (UType.isPrimitiva(value)) {
				list.add("\"" + key + "\": \"" + value + "\",");
			} else if (value instanceof MapSO) {
				final MapSO m = (MapSO) value;//jaProcessados.size()
				final ListString lst = m.asJson(jaProcessados);
				if (lst.isEmpty()) {
					list.add("\"" + key + "\": {},");
				} else {
					list.add("\"" + key + "\": {");
					lst.remove(0);
					list.add(lst);
				}
			} else if (value instanceof List) {
				final List<?> lst = (List<?>) value;
				if (lst.isEmpty()) {
					list.add("\"" + key + "\": [],");	
				} else if (UType.isPrimitiva(lst.get(0))) {
					list.add("\"" + key + "\": [");
					list.margemInc();
					for (final Object obj : lst) {
						list.add("\"" + obj + "\",");	
					}
					list.margemDec();
					list.add("]");
				} else {
					list.add("\"" + key + "\": [");
					list.margemInc();
					for (final Object obj : lst) {
						
						if (obj == null) {
							continue;
						}
						
						MapSO m;
						if (obj instanceof MapSO) {
							m = (MapSO) obj;
						} else {
							m = MapSoFromObject.get(obj);
						}
						
						if (m == null) {
							System.out.println(m);
						}
						
						ListString asJson = m.asJson(jaProcessados);
						list.add(asJson);
					}
					list.margemDec();
					list.add("]");
				}
			}
		}
		list.margemDec();
		list.add("},");
		return list;
	}
	
	public int getIntObrig(final String key) {
		return UInteger.toInt(this.getObrig(key));
	}
	public String getStringObrig(final String key) {
		return UString.toString(this.getObrig(key));
	}
	public boolean isEmpty(final String key) {
		final Object o = this.get(key);
		return UObject.isEmpty(o);
	}
	public Data getData(final String key) {
		Object o = this.get(key);
		return Data.to(o);
	}
	public Data getDataObrig(final String key) {
		Object o = this.getObrig(key);
		return Data.to(o);
	}
	public Double getDouble(final String key) {
		final Object o = this.get(key);
		return UDouble.toDouble(o);
	}
	public double getDoubleSafe(final String key) {
		Double o = getDouble(key);
		return o == null ? 0 : o;
	}
	public Calendar getCalendar(final String key) {
		final Data data = getData(key);
		return data == null ? null : data.getCalendar();
	}
	
	@SuppressWarnings("unchecked")
	public Numeric2 getNumeric2(final String key) {
		
		final Object o = this.get(key);
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Numeric2) {
			return (Numeric2) o;
		}
		
		if (o instanceof Map<?, ?>) {
			final Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric2".equals(map.get("tipo"))) {
				final String s = UString.toString(map.get("id"));
				if (s.contains(".")) {
					final int inteiros = UInteger.toInt(UString.beforeFirst(s, "."));
					final int centavos = UInteger.toInt(UString.afterFirst(s, "."));
					return new Numeric2(inteiros, centavos);
				} else {
					return new Numeric2(UInteger.toInt(s));
				}
			}
		}
		
		final String s = getString(key);
		if (s == null) return null;
		return new Numeric2(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric3 getNumeric3(final String key) {
		
		final Object o = this.get(key);
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Numeric3) {
			return (Numeric3) o;
		}
		
		if (o instanceof Map<?, ?>) {
			final Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric3".equals(map.get("tipo"))) {
				final String s = UString.toString(map.get("id"));
				if (s.contains(".")) {
					final int inteiros = UInteger.toInt(UString.beforeFirst(s, "."));
					final int centavos = UInteger.toInt(UString.afterFirst(s, "."));
					return new Numeric3(inteiros, centavos);
				} else {
					return new Numeric3(UInteger.toInt(s));
				}
			}
		}
		
		final String s = getString(key);
		if (s == null) return null;
		return new Numeric3(s);
	}
	
	@SuppressWarnings("unchecked")
	public Numeric4 getNumeric4(final String key) {
		
		final Object o = this.get(key);
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Numeric4) {
			return (Numeric4) o;
		}
		
		if (o instanceof Map<?, ?>) {
			final Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric4".equals(map.get("tipo"))) {
				final String s = UString.toString(map.get("id"));
				if (s.contains(".")) {
					final int inteiros = UInteger.toInt(UString.beforeFirst(s, "."));
					final int centavos = UInteger.toInt(UString.afterFirst(s, "."));
					return new Numeric4(inteiros, centavos);
				} else {
					return new Numeric4(UInteger.toInt(s));
				}
			}
		}
		
		final String s = getString(key);
		if (s == null) return null;
		return new Numeric4(s);
	}

	@SuppressWarnings("unchecked")
	public Numeric5 getNumeric5(final String key) {
		
		final Object o = this.get(key);
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Numeric5) {
			return (Numeric5) o;
		}
		
		if (o instanceof Map<?, ?>) {
			final Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric5".equals(map.get("tipo"))) {
				final String s = UString.toString(map.get("id"));
				if (s.contains(".")) {
					final int inteiros = UInteger.toInt(UString.beforeFirst(s, "."));
					final int centavos = UInteger.toInt(UString.afterFirst(s, "."));
					return new Numeric5(inteiros, centavos);
				} else {
					return new Numeric5(UInteger.toInt(s));
				}
			}
		}
		
		final String s = getString(key);
		if (s == null) return null;
		return new Numeric5(s);
	}
	
	@SuppressWarnings("unchecked")
	public Numeric15 getNumeric15(final String key) {
		
		final Object o = this.get(key);
		
		if (o == null) {
			return null;
		}
		
		if (o instanceof Numeric15) {
			return (Numeric15) o;
		}
		
		if (o instanceof Map<?, ?>) {
			final Map<String, Object> map = (Map<String, Object>) o;
			if ("Numeric15".equals(map.get("tipo"))) {
				final String s = UString.toString(map.get("id"));
				if (s.contains(".")) {
					final int inteiros = UInteger.toInt(UString.beforeFirst(s, "."));
					final int centavos = UInteger.toInt(UString.afterFirst(s, "."));
					return new Numeric15(inteiros, centavos);
				} else {
					return new Numeric15(UInteger.toInt(s));
				}
			}
		}
		
		final String s = getString(key);
		if (s == null) return null;
		return new Numeric15(s);
	}	
	
	public Numeric2 getNumeric2Obrig(final String key) {
		final Numeric2 o = getNumeric2(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}

	public Numeric3 getNumeric3Obrig(final String key) {
		final Numeric3 o = getNumeric3(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}

	public Numeric4 getNumeric4Obrig(final String key) {
		final Numeric4 o = getNumeric4(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}
	
	public Numeric5 getNumeric5Obrig(final String key) {
		final Numeric5 o = getNumeric5(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}

	public Numeric15 getNumeric15Obrig(final String key) {
		final Numeric15 o = getNumeric15(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}
	
	public BigDecimal getBigDecimal(final String key) {
		final Numeric2 o = getNumeric2(key);
		return o == null ? null : o.getValor();
	}
	public BigDecimal getBigDecimal(final String key, final int casas) {
		final Object o = this.get(key);
		if (o == null) return null;
		return UBigDecimal.toBigDecimal(o, casas);
	}

	public int id() {
		return getIntObrig("id");
	}

	public Boolean getBoolean(final String key) {
		return UBoolean.toBoolean( this.get(key) );
	}
	public boolean getBooleanObrig(final String key) {
		final Boolean o = getBoolean(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}
	
	public MapSO sub(final String key) {
		final MapSO o = new MapSO();
		o.pai = this;
		this.add(key, o);
		return o;
	}
	
	public MapSO out() {
		return pai;
	}
	public Lst<MapSO> getSubList(final String key) {
		final Lst<MapSO> list = new Lst<>();

		final List<?> lst = this.get(key);
		if (!UList.isEmpty(lst)) {
			for (final Object o : lst) {
				list.add(MapSoFromObject.get(o));
			}
		}
		return list;
	}
	public MapSO getSubObrig(final String key) {
		final MapSO o = getSub(key);
		if (o == null) {
			throw UException.runtime("key n"+UConstantes.a_til+"o encontrado: " + key);
		}
		return o;
	}
	public MapSO getSub(final String key) {
		final Object o = this.get(key);
		if (o instanceof MapSO) {
			return (MapSO) o;	
		} else {
			return MapSoFromObject.get(o);
		}
	}

	public ListString struct() {
		final ListString list = new ListString();
		list.add("new MapSO()");
		this.struct(list);
		return list;
	}
	
	private void struct(final ListString list) {
		final Set<String> keys = keySet();
		for (final String key : keys) {
			final Object value = this.get(key);
			if (value == null) {
				list.add(".add(\"" + key + "\", null)");	
			} else if (UType.isPrimitiva(value)) {
				if (value instanceof String) {
					list.add(".add(\"" + key + "\", \""+value+"\")");
				} else {
					list.add(".add(\"" + key + "\", "+value+")");
				}
			} else if (value instanceof MapSO) {
				list.add(".sub(\"" + key + "\")");
				list.getMargem().inc();
				final MapSO map = (MapSO) value;
				map.struct(list);
				list.getMargem().dec();
				list.add(".out()");
			} else if (value instanceof List) {
				
				final List<?> lst = (List<?>) value;
				
				if (lst.isEmpty()) {
					list.add(".add(\"" + key + "\", [])");	
				} else {
					
					list.add(".add(\"" + key + "\", Arrays.asList(");
					list.getMargem().inc();
					
					final Object first = lst.get(0);
					
					if (first instanceof String) {
						for (final Object o : lst) {
							list.add("\"" + o + "\",");
						}
					} else if (UType.isPrimitiva(first)) {
						for (final Object o : lst) {
							list.add(o + ",");
						}
					} else if (first instanceof MapSO) {
						for (final Object o : lst) {
							final MapSO map = (MapSO) o;
							list.add(map.struct());
							list.add(",");
						}
					} else {
						throw new RuntimeException("???");
					}

					String s = list.removeLast();
					s = UString.ignoreRigth(s);
					if (UString.notEmpty(s)) {
						list.add(s);
					}
					list.getMargem().dec();
					list.add("))");
					
				}
				
			} else {
				throw new RuntimeException("???");
			}
		}
	}

	public void save(final String file) {
		this.save(new File(file));
	}
	public void save(final File file) {
		final ListString list = new ListString();
		forEach((key, value) -> {
			list.add(key + "=" + UString.toString(value));
		});
		list.save(file);
	}

	public boolean loadIfExists(final File file) {
		if (UFile.exists(file)) {
			final ListString list = new ListString().load(file);
			list.trimPlus();
			list.removeEmptys();
			list.removeIfStartsWith("#");
			for (final String s : list) {
				this.add(UString.beforeFirst(s, "=").trim(), UString.afterFirst(s, "=").trim());
			}
			return true;
		} else {
			return false;
		}
		
	}
	public boolean loadIfExists(final String fileName) {
		return this.loadIfExists(new File(fileName));
	}

	public ListString getKeys() {
		final ListString list = new ListString();
		list.addAll(keySet());
		return list;
	}

	public MapSO setObrig(final String key, final Object value) {
		if (UString.isEmpty(key)) {
			throw new RuntimeException("key is empty");
		}
		if (UObject.isEmpty(value)) {
			throw new RuntimeException("value is empty");
		}
		return this.add(key, value);
	}

	public boolean isTrue(final String key) {
		return UBoolean.isTrue(getBoolean(key));
	}
	
	public <T> Lst<T> map(FTTT<T,String,Object> func) {
		Lst<T> result = new Lst<>();
		ListString keys = getKeys();
		for (String s : keys) {
			result.add(func.call(s, get(s)));
		}
		return result;
	}
	
}
