package gm.utils.number;

public class UShort {

	public static Short toShort(Object o) {
		if (o == null) {
			return null;
		}
		Class<?> classe = o.getClass();
		if (classe.equals(Integer.class)) {
			Integer i = (Integer) o;
			return i.shortValue();
		}
		return Short.parseShort(o.toString());
	}	
	
}
