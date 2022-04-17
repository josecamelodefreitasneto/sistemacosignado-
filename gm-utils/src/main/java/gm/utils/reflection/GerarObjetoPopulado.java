package gm.utils.reflection;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Max;

import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.UType;
import gm.utils.number.UInteger;

public class GerarObjetoPopulado {

	private static Map<Class<?>, Object> objects = new HashMap<>();
	
	private static Object exec(Class<?> classe){
		
		if (UType.isPrimitiva(classe)) {
			return Aleatorio.get(classe);
		}
		
		Object o = objects.get(classe);
		
		if (o != null) {
			return o;
		}
		
		o = UClass.newInstance(classe);
		objects.put(classe, o);
		Atributos atributos = ListAtributos.persist(classe, false);
		for (Atributo atributo : atributos) {
			Object value;
			if (atributo.is(String.class)) {
				String s = classe.getSimpleName() + " " + atributo.nome();
				while (s.length() < atributo.getLength()) {
					s += " " + Aleatorio.getPalavra();
				}
				s = s.substring(0, atributo.getLength());
				value = s;
//				value = Aleatorio.getString(0, atributo.getLength());
			} else if (atributo.is(Integer.class)) {
				Max max = atributo.getAnnotation(Max.class);
				if (max == null) {
					value = Aleatorio.getInteger();
				} else {
					Integer m = UInteger.toInt(max.value());
					if (m > 50000) {
						m = 50000;
					}
					if (m < 0) {
						value = -Aleatorio.get(0, -m);
					} else {
						value = Aleatorio.get(0, m);
					}
				}
			} else if (atributo.isNumeric1()) {
				value = Aleatorio.getBigDecimal(2, 1);
			} else if (atributo.isNumeric2()) {
				value = Aleatorio.getBigDecimal(3, 2);
			} else {
				value = exec(atributo.getType());
			}
			atributo.set(o, value);
		}
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> classe){
		return (T) exec(classe);
	}
	
}
