package gm.utils.comum;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import gm.utils.classes.ClassComGenerics;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UGenerics {
	
	public static ClassComGenerics getClassComGenerics(Field field) {
		Class<?> classe = UClass.getClass(field);
//		field.getGenericType();
		Atributos as = ListAtributos.get(classe);
		Atributo a = as.get("signature");
		if (a == null) {
			return null;
		}
		String signature = a.getString(field);
		if (signature == null) {
			ClassComGenerics o = new ClassComGenerics();
			o.setClasse(field.getType());
			return o;
		}
		return getClassComGenericsBySignature(signature);
	}
	
	public static ClassComGenerics getClassComGenericsBySignature(String s) {
		s = UString.trimPlus(s);
		s = s.replace("/", ".");
		s = s.replace(";>", ">");
		if (s.endsWith(";")) {
			s = UString.ignoreRigth(s);
		}
		return getClassComGenericsBySignatureRecursivo(s);
	}
	
	private static ClassComGenerics getClassComGenericsBySignatureRecursivo(String s) {
		
		ClassComGenerics o = new ClassComGenerics();
		
		if (s.startsWith("+")) {
			s = s.substring(1);	
		}

		if (s.equals("*")) {
			return o;
		}
		
		s = s.substring(1);
		
		if (!s.contains("<")) {
			o.setClasse(UClass.getClassObrig(s));
			return o;
		}
		
		o.setClasse(UClass.getClassObrig(UString.beforeFirst(s, "<")));
		
		s = UString.afterFirst(s, "<");
		s = UString.beforeLast(s, ">");
		ListString list = ListString.byDelimiter(s, ";");
		o.setGenerics(new ArrayList<>());
		for (String string : list) {
			o.getGenerics().add(getClassComGenericsBySignatureRecursivo(string));
		}
		return o;
	}
	
	@Deprecated//use getClassComGenerics
	public static ListClass getGenericClasses(Field field) {
		Atributo a = ListAtributos.get( UClass.getClass(field) ).get("signature");
		if (a == null) {
			return null;
		}
		String signature = a.getString(field);
		if (signature == null) {
			return null;
		}
		signature = UString.afterFirst(signature, "<L");
		if (signature == null) {
			return null;
		}
		signature = UString.beforeLast(signature, ";>");
		
		while (signature.contains("<L")) {
			String before = UString.beforeFirst(signature, "<L");
			String s = UString.afterFirst(signature, "<L");
			int opens = 1;
			while (opens > 0) {
				if (s.startsWith(";>")) {
					opens--;
					s = s.substring(2);
				} else if (s.startsWith("<L")) {
					opens++;
					s = s.substring(2);
				} else {
					s = s.substring(1);
				}
			}
			signature = before + s;
		}
		
		signature = signature.replace("/", ".");
		signature = signature.replace(";L", ";");
		
		ListClass classes = new ListClass();
		ListString list = ListString.byDelimiter(signature, ";");
		for (String s : list) {
			classes.add( UClass.getClassObrig(s) );
		}
		return classes;
		
	}
	
	public static Class<?> getGenericClass(Field field) {
		
		ListClass classes = getGenericClasses(field);
		
		if (classes == null || classes.isEmpty()) {
			return null;
		}
		
		if (classes.size() > 1) {
			throw UException.runtime("A classe possui mais de um tipo Generico");
		}
		
		return classes.get(0);
		
	}

	public static <T> Class<T> getGenericClass(Object o) {
//		o.getClass().getGenericSuperclass()
		return getGenericClass(UClass.getClass(o));
	}

	public static <T> Class<T> getGenericClass(Class<?> classe) {
		return getGenericClass(classe, 0);
	}

	public static <T> Class<T> getGenericClass(Object o, int index) {
		Class<?> classe = o.getClass();
		return getGenericClass(classe, index);
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericClass(Class<?> classe, int index) {
		if (UClass.isAbstract(classe)) {
			throw UException.runtime("A classe n"+UConstantes.a_til+"o pode ser abstrata: " + classe.getSimpleName());
		}
		Type tipo = classe.getGenericSuperclass();
//		if (tipo.equals(Object.class)) {
//			throw UException.runtime("A classe deve ser herdada: " + classe);
//		}
		if (!(tipo instanceof ParameterizedType)) {
			return null;
		}
		ParameterizedType pt = (ParameterizedType) tipo;
		Type[] fieldArgTypes = pt.getActualTypeArguments();
		try {
			return (Class<T>) fieldArgTypes[index];
		} catch (Exception e) {
			return null;
		}
	}
	
//	public static ListString getGenericNames(Class<?> classe) {
//		classe.getGenericInterfaces();
//	}
	
	
}
