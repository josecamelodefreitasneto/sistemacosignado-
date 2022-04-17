package gm.utils.reflection;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.JSon;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UCompare;
import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.exception.UException;
import gm.utils.map.Params;
import gm.utils.number.Numeric2;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

//Dynamic Object
public abstract class DO<T> {

	public Integer getInt(String field, int def){
		Integer value = getInt(field);
		if (value == null) {
			return def;
		} else {
			return value;
		}
	}
	
	public Integer getInt(Atributo a){
		return UInteger.toInt(get(a));
	}
	public Integer getInt(String field){
		return UInteger.toInt(get(field));
	}
	public String getString(String field) {
		
		Object o = get(field);
		
		if (o == null){
			return null;
		}
		
		Atributo f = as().get(field);
		
		if (f == null) {
			return null;
		}
		
		if (f.isDate()) {
			
			Data data = Data.to(o);
			
			Temporal annotation = f.getAnnotation( Temporal.class );
			if (annotation != null) {
				if ( UCompare.equals(annotation.annotationType(), TemporalType.DATE) ) {
					return data.format("[dd]/[mm]/[yyyy] "+UConstantes.a_crase+"s [hh]:[nn]");
				}
			}
			
			return data.format("[dd]/[mm]/[yyyy] "+UConstantes.a_crase+"s [hh]:[nn]");			
		}
		return UString.toString(o);
		
	}
	public String getString(Atributo a) {
		return UString.toString( get(a) );
	}
	private Metodo getMetodo(String nome) {
		return ListMetodos.get(object.getClass()).get(nome);
	}
	public String json(String field) {
		Atributo a = atributo(field);
		if (a == null) {
			Metodo metodo = getMetodo("get"+field);
			if (metodo == null) {
				throw UException.runtime("Field ou Method nao encontrado : " + field);
			}
			Object value = metodo.invoke(object);
			return JSon.toString(value);
		}
		return json(a);
	}
	public String json(Atributo a) {
		Object o = get(a);
		return JSon.getString(o);
	}
	
	public void inc(Atributo field){
		inc(field.nome());
	}
	@SuppressWarnings("unchecked")
	public T inc(String field){
		Integer i = getInt(field);
		if (i == null) {
			i = 1;
		} else {
			i++;
		}
		set(field, i);
		return (T) object;
	}
	public void dec(Atributo field){
		dec(field.nome());
	}
	public void dec(String field){
		Integer i = getInt(field);
		if (i == null) {
			i = -1;
		} else {
			i--;
		}
		set(field, i);
	}
	
	public Object get(String field){
		try {
			Atributo a = atributo(field);
			if (a != null) {
				return a.get(object);
			}
			Metodo metodo = getMetodo("get" + field);
			if (metodo != null) {
				return metodo.invoke(object);
			}
			return null;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
//		Atributo a = atributo(field);
//		if (a == null) {
//			throw UException.runtime("Campo n"+UConstantes.a_til+"o encontrado: " + getClass().getSimpleName() + " : " + field );
//		}
//		return a.get(object);
	}
	public Object get(Atributo a){
		return get(a.nome());
	}
	
	public T set(Atributo field, Object value){
		return set(field.nome(), value);
	}
	
	@SuppressWarnings("unchecked")
	public T set(String field, Object value){
		atributo(field).set(object, value);
		return (T) this;
	}
	
	@Transient
	private transient Object object = this;
	
	@Transient
	private transient Atributos as;
	public Atributos as() {
		if ( as == null ) {
			as = ListAtributos.get(UClass.getClass(object));
		}
		return as;
	}
	
	@Transient
	private transient Atributos asPersist;
	public Atributos asPersist() {
		if ( as == null ) {
			as = ListAtributos.persist(UClass.getClass(object), false);
		}
		return as;
	}
	
	public boolean contemAtributo(Atributo a) {
		return contemAtributo(a.nome());
	}
	public boolean contemAtributo(String nome) {
		if (object instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, ?> map = (Map<String, ?>) object;
			Set<String> keySet = map.keySet();
			for (String k : keySet) {
				if (k.equalsIgnoreCase(nome)) {
					return true;
				}
			}
			return false;
		} else {
			return atributo(nome) != null;
		}
	}
	public Atributo atributo(Atributo field){
		return atributo(field.nome());
	}
	
	public Atributo atributo(String field){
		return as().get(field);
	}
	
	public void read(Map<String, ?> map) {
		for (String key : map.keySet()) {
			if (!as().contains(key)) {
				continue;
			}
			try {
				Object value = map.get(key);
				if ( value instanceof Map ) {
					@SuppressWarnings("unchecked")
					Map<String, Integer> m = (Map<String, Integer>) value;
					
					if (m.keySet().isEmpty()) {
						value = null;
					} else {
						String k = m.keySet().iterator().next();
						value = m.get(k);
					}
				}
				try {
					set(key, value);
				} catch (Exception e) {
					ULog.warn(key);
					throw e;
				}
				
			} catch (Exception e) {
				
				if (e instanceof MessageException) {
					throw e;
				}
//				U.printStackTrace(e);
				throw UException.runtime("Erro: " + key, e);
			}
			
		}
	}

	public void read(Params params) {
//		@SuppressWarnings("unchecked")
//		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) params.get("o");
		read(params.getMap());
	}
	
	public static DO<?> novo(Object o){
		
		DO<?> x;
		if ( o instanceof DO ) {
			x = (DO<?>) o;
		} else {
			x = new DO<Object>() {};
			x.object = o;
		}
		
		return x;
	}
	
	public void read(Object o) {
		DO<?> d;
		if ( o instanceof DO ) {
			d = (DO<?>) o;
		} else {
			d = novo(o);
		}
		read(d);
	}
	
	public void read(DO<?> o) {
		
		Atributo id = as().getId();
		
		if (id != null) {
			set(id, o.get(id));
		}
		
		for (Atributo a : as()) {
			if (o.contemAtributo(a)) {
				set(a, o.get(a));
				continue;
			}
		}
		
	}
	public boolean has(String field) {
		return atributo(field) != null || getMetodo("get"+field) != null;
	}
	
	public Map<String, Object> asMap(){
		
		Map<String, Object> map = new HashMap<>();
		
		for (Atributo a : as()) {
			
			if ( a.getField().getDeclaringClass().equals( DO.class ) ) {
				continue;
			}
			
			String key = a.nome();
			
			Object value = a.get(object);
			
			if (value == null) {
				value = "null";
			} else if ( !a.isPrimitivo() ) {
				IdObject obj = (IdObject) value;
				value = obj.getId();
			} else if ( a.isDate() ) {
				Data data = Data.to(value);
				value = data.format("[ddd] - [dd]/[mm]/[yyyy] [hh]:[nn]");
			}
			
			if ( a.is(Integer.class) ) {
				value = UInteger.toInt(value);
			} else if ( a.is(BigDecimal.class) ) {
				BigDecimal b = UBigDecimal.toBigDecimal(value, a.getAnnotation(Digits.class).fraction());
				value = new Numeric2(b).toDouble();
			} else if ( a.is(Boolean.class) ) {
				value = UBoolean.isTrue(value);
			} else {
				value = UString.toString(value);
			}
			
			map.put(key, value);
		}
		
		return map;
		
	}
	public Integer id() {
		return getInt("id");
	}
	public String text() {
		return getString("text");
	}
	public Object object() {
		return object;
	}
	public Data getData(String s) {
		return Data.to(get(s));
	}
	public String dataSql(String s) {
		Data data = getData(s);
		if (data == null) {
			return "null";
		} else {
			return data.format_sql(true);
		}
	}

	public boolean isTrue(String field) {
		return UBoolean.isTrue( get(field) );
	}
	
	public void print(){
		Map<String, Object> map = asMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			ULog.debug(key + " = " + map.get(key));
		}
	}
	

}
