package gm.utils.reflection;

import java.util.HashMap;
import java.util.Map;

public class ListMetodos {

	private static Map<Class<?>, Metodos> map = new HashMap<>();
	
	public static Metodos get(Class<?> classe) {
		Metodos metodos = map.get(classe);
		if (metodos == null) {
			metodos = new Metodos(classe);
			map.put(classe, metodos);
		}
		return metodos.clone();
	}
	
}
