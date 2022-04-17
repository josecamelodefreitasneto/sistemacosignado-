package gm.utils.map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UObject;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.exception.UException;
import gm.utils.number.ListInteger;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UDouble;
import gm.utils.number.UInteger;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class Params implements Iterable<String> {
	private MapSO map;
	public Params(Params p){
		this(p.map);
	}
	public Params(Map<String, Object> map){
		this(new MapSO(map));
	}
	public Params(MapSO map){
		this.map = map;
	}
	public Params() {
		this(new MapSO());
	}
	public String getString(String key){
		return UString.toString( map.get(key) );
	}
	public ListInteger getInts(String key){
		return map.getInts(key);
	}	
	public int getInt(String key, int def){
		return UInteger.toInt( map.get(key), def );
	}
	public Integer getInt(String key){
		String o = getString(key);
		if (UString.isEmpty(o)) {
			return null;
		}
		o = o.replace(".", "");
		return UInteger.toInt(o);
	}
	public Boolean getBoolean(String key) {
		return UBoolean.toBoolean( map.get(key) );
	}
	public boolean getBoolean(String key, boolean def) {
		Boolean o = getBoolean(key);
		if (o == null) {
			return def;
		} else {
			return o;
		}
	}
	public boolean getBooleanObrig(String key) {
		Boolean o = getBoolean(key);
		if (o == null) {
			throw UException.runtime("getBooleanObrig: " + key + " == null");
		}
		return o;
	}
	public Object get(String key) {
		return getT(key);
	}
	public <T> T getT(String key) {
		return map.get(key);
	}
	public int getIntObrig(String key){
		if (isEmpty(key)) {
			throw UException.runtime("getIntObrig: " + key + " == null");
		}
		return getInt(key);
	}
	public String getStringObrig(String key){
		if (isEmpty(key)) {
			throw new MessageException("getStringObrig: " + key + " == null");
		}
		return getString(key);
	}
	public Integer id(){
		return getIntObrig("id");
	}
	public void add(String key, Object value) {
		
		if (key.contains(".")) {
			String s = UString.beforeFirst(key, ".");
			Object o = get(s);
			if (o != null && (o instanceof Params || o instanceof Map) ) {
				key = UString.afterFirst(key, ".");
				Params sub = getSub(s);
				sub.add(key, value);
				set(s, sub.getMap());
				return;
			}
		}
		
		put(key,value);
	}
	public void set(String key, Object value) {
		put(key,value);
	}	
	public void put(String key, Object value) {
		if (value == null) {
			map.remove(key);
		} else {
			map.put(key, value);
		}
	}
	public int pagina() {
		return getInt("pagina",0);
	}
	public int limit() {
		return getInt("limit", UConstantes.REGISTROS_POR_PAGINA_DEFAULT);
	}
	public boolean isTrue(String key) {
		return Boolean.TRUE.equals( getBoolean(key) );
	}
	public Params read(String s) {
		
		if (UString.isEmpty(s)) {
			return this;
		}
		
		ListString list = ListString.byDelimiter(s, ";", "&");
		for (String x : list) {
			String key = UString.beforeFirst(x, "=");
			String value = UString.afterFirst(x, "=");
			set(key, value);
		}
		
		return this;
		
	}
	
	public Params read(Object o) {
		
		if (o instanceof String) {
			return read((String) o);
		}
		
		Atributos as = ListAtributos.get(o.getClass());
		for (Atributo a : as) {
			Object value = a.get(o);
			if (UObject.isEmpty(value)) {
				remove(a.nome());
				continue;
			}
			
			if (a.is(BigDecimal.class)) {
				
				if (value instanceof String) {
					String s = (String) value;
					if (s.contains(",")) {
						s = s.replace(".", "");
						s = s.replace(",", ".");
						value = s;
					}
				}
				
			}
			
			set(a.nome(), value);
		}
		return this;
	}
	public void readPrimitivo(Object o) {
		Atributos as = ListAtributos.get( UClass.getClass(o) );
		
		Atributo id = as.getId();
		
		if (id != null) {
			set("id", id.get(o));
		}
		
		for (Atributo a : as) {
			
			if (a.isTransient()) {
				continue;
			}

			Object value = a.get(o);
			
			if (UObject.isEmpty(value)) {
				remove(a.nome());
				continue;
			}
			
			if (!a.isPrimitivo()) {
				IdObject obj = (IdObject) value;
				value = obj.getId();
			}
			
			set(a.nome(), value);
		}
	}
	public void read(Params params) {
		if (params == null) {
			return;
		}
		readMap(params.map);
	}
	public void readMap(MapSO map) {

		if (map == null) {
			return;
		}
		
		for (String key : map.keySet()) {
			Object value = map.get(key);
			set(key, value);
		}
		
	}
	public void read(Map<String, String[]> map) {
		
		if (map == null) {
			return;
		}
		
		for (String key : map.keySet()) {
			String[] value = map.get(key);
			set(key, value[0]);
		}
	}
	public boolean isEmpty(String key) {
		Object o = get(key);
		return UObject.isEmpty( o );
	}
	public void clear() {
		map.clear();
	}
	public String text() {
		return getStringObrig("text");
	}
	public MapSO getMap() {
		return map;
	}
	@Override
	public Iterator<String> iterator() {
		return map.keySet().iterator();
	}
	
	@SuppressWarnings("unchecked")
	public List<Params> getSubs(String key) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) get(key);
		List<Params> result = new ArrayList<>();
		if (list != null) {
			for (Map<String, Object> map : list) {
				result.add( new Params(map) );
			}
		}
		return result;
	}
	
	public Params getSub(String key) {
		Object o = get(key);
		
		if (o == null) {
			return null;
		}
		
		if ( o instanceof Params ) {
			return (Params) o;
		}
		
		if ( o instanceof Map ) {
			@SuppressWarnings("unchecked")
			Map<String, Object> x = (Map<String, Object>) o;
			MapSO m = new MapSO(x);
			return new Params(m);
		}
		
		throw UException.runtime("N"+UConstantes.a_til+"o "+UConstantes.e_agudo+" um map!" + key + " - " + o + " - " + o.getClass());
	}
	public Calendar getCalendar(String key) {
		Data data = getData(key);
		if (data == null) {
			return null;
		}
		return data.getCalendar();
	}
	public Data getDataObrig(String key) {
		Data data = null;
		try {
			 data = getData(key);
		} catch (Exception e) {
			throw new MessageException ("Data inv"+UConstantes.a_agudo+"lida: " + getString(key));
		}
		if (data == null) {
			throw new MessageException ("getDataObrig: " + key + " == null");
		}
		return data;
		
	}
	public Data getData(String key) {
		Object o = get(key);
		if (UObject.isEmpty(o)) {
			return null;
		}
		Data data = Data.to(o);
		return data;
	}
	public Double getDouble(String key) {
		return UDouble.toDouble( get(key) );
	}
	public Numeric1 getNumeric1(String key) {
		return new Numeric1( getDouble(key) );
		
	}
	public Numeric2 getNumeric2(String key) {
		return new Numeric2( getDouble(key) );
	}
	public Numeric2 getNumeric2Obrig(String key) {
		Numeric2 o = getNumeric2(key);
		if (o == null || o.isZero()) {
			throw UException.runtime("Obrigat"+UConstantes.o_agudo+"rio: " + key);
		}
		return o;
	}
	
	public BigDecimal getBigDecimal(String key){
		return UBigDecimal.toBigDecimal(getDouble(key));
	}
	public Object remove(String key) {
		Object o = get(key);
		set(key, (Object) null);
		return o;
	}
	public boolean has(String key) {
		return get(key) != null;
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		for (String key : this) {
			s += key + " = " + get(key) + " ; ";
		}
		
		return s;
	}
	/*
	public Tenancy empresa(boolean pegarDaSessaoSeVazio) {
		Integer id = getInt("empresa");
		if (id != null) {
			return FwSelect.tenancy(id);
		}
		if (pegarDaSessaoSeVazio) {
			return Sessao.getTenancy();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> checks(Class<T> classe, String key) {
		Bo<?> bo = GetBo.get(classe);
		List<T> result = new ArrayList<>();
		List<Params> subs = getSubs(key);
		for (Params p : subs) {
			if (p.isTrue("checked")) {
				T o = (T) bo.byId(p.id());
				result.add(o);
			}
		}
		return result;
	}
	*/	
}
