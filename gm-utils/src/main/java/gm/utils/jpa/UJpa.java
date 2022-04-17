package gm.utils.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.OneToMany;

import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;

public class UJpa {
	
	private UJpa() {
		throw new IllegalStateException("Classe utilitaria");
	}

	public static void normalize(Object p, Boolean associate) {
		try {
			normalize_(p, associate);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	private static void normalize_(Object p, Boolean associate) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class<?> beanClass = p.getClass();

		ULog.debug(beanClass.getName());

		Field[] fields = beanClass.getDeclaredFields();
		for (Field field : fields) {

			if (field.getAnnotation(OneToMany.class) != null) {

				if (field.getType() != List.class) {

					throw UException.runtime("The annotated field " + field.getName() + " in  " + beanClass + " is of unsupported type " + field.getType()
							+ ". Fields annotated as @OneToMany have to be a genericly typed " + List.class);
				}

				Class<?> oneToManyClass = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

				Field f = getMethod(beanClass, oneToManyClass);
				UAssert.notEmpty(f, "f == null");

				List<?> list = getList(p, beanClass, field);

				for (int i = 0; i < list.size(); i++) {
					Object chield = list.get(i);
					if (associate) {
						f.set(chield, p);
					} else {
						f.set(chield, null);
					}

				}
			}
		}
	}

	private static List<?> getList(Object p, Class<?> beanClass, Field field)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String name = field.getName();
		String method = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
		Method m = beanClass.getDeclaredMethod(method);

		return (List<?>) m.invoke(p);
	}

	private static Field getMethod(Class<?> beanClass, Class<?> oneToManyClass) {
		Field[] fields = oneToManyClass.getDeclaredFields();
		Field f = null;
		for (Field field : fields) {
			if (field.getType().equals(beanClass)) {
				f = field;
				f.setAccessible(true);
				break;
			}
		}
		return f;
	}
	
	public static void checaObrig(Object o, String nome, int id) {
		if (o == null) {
			throw UException.runtime( String.format("N"+UConstantes.a_til+"o encontrado: %s -> %d", nome, id));
		}
	}	
}
