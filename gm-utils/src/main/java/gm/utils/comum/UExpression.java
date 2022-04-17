package gm.utils.comum;

import java.util.Map;
import java.util.Set;

import gm.utils.classes.UClass;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UExpression {

	public static String getString(String field, Object o) {
		return UString.toString(getValue(field, o));
	}
	
	public static Object getValue(String expression, Object o) {
		
		if (o instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) o;
			Set<String> keySet = map.keySet();
			expression = expression.toLowerCase();
			for (String key : keySet) {
				if (key.toLowerCase().equals(expression)) {
					return map.get(key);
				}
			}
			return null;
		}
		
		Class<? extends Object> classe = o.getClass();
		Atributos as = ListAtributos.get(classe);
		if (expression.contains(".")) {
			ListString fields = ListString.byDelimiter(expression, ".");
			expression = fields.remove(0);
			if (expression.endsWith("()")) {
				expression = UString.beforeLast(expression, "()");
				return ListMetodos.get(classe).get(expression).invoke(o);
			}
			Atributo a = as.getObrig(expression);
			o = a.get(o);
			// o = getValue(getField(field, o.getClass()), o);
			if (o == null) {
				return null;
			}
			expression = fields.toString(".");
			return getValue(expression, o);
		} else if (expression.endsWith("()")) {
			expression = UString.beforeLast(expression, "()");
			return ListMetodos.get(classe).get(expression).invoke(o);
		} else {
			Atributo a = as.get(expression);
			if (a == null) {
				Metodos metodos = ListMetodos.get(classe);
				Metodo metodo = metodos.get("get" + expression);
				if (metodo != null) {
					return metodo.invoke(o);
				}
			} else {
				return a.get(o);
			}
			return o;
		}
	}

	public static void setValue(Object o, String field, Object value) {
		ListAtributos.get( UClass.getClass(o) ).getObrig(field).set(o, value);
	}

	public static Atributo getAtributo(Class<?> classeOrigem, String expression) {
		Atributos as = ListAtributos.get(classeOrigem);
		if (expression.contains(".")) {
			Atributo a = as.getObrig( UString.beforeFirst(expression, ".") );
			expression = UString.afterFirst(expression, ".");
			return getAtributo(a.getType(), expression);
		} else {
			return as.getObrig(expression);
		}
	}

	public static String toJavaExpression(String s) {
		return s;
	}
	
}
