package gm.utils.comum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Table;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.date.MyCalendar;
import gm.utils.exception.UException;
import gm.utils.lambda.FTTT;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UBigInteger;
import gm.utils.number.UDouble;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.number.UShort;
import gm.utils.reflection.ListMetodos;
import gm.utils.string.UString;

public class UType {
	
	public static Object[] asAutoParameters(final Class<?>[] parameterTypes) {
		
		final Object[] args = new Object[parameterTypes.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			final Class<?> c = parameterTypes[i];
			if ( UType.isPrimitivaBox(c) || !UType.isPrimitiva(c) || c == String.class ) {
				args[i] = null;
			} else if (c == int.class) {
				args[i] = 0;
			} else if (c == double.class) {
				args[i] = 0.0;
			} else if (c == boolean.class) {
				args[i] = false;
			} else if (c == long.class) {
				args[i] = 0L;
			} else if (c == char.class) {
				args[i] = ' ';
			} else if (c == float.class) {
				args[i] = 0;
			} else if (c.isEnum()) {
				final Object[] values = ListMetodos.get(c).get("values").invoke(null);
				args[i] = values[0];
			} else {
				throw UException.runtime("nao tratado: " + c);
			}
		}
		
		return args;
		
	}
	
	public static final ListClass PRIMITIVAS_JAVA_REAL = new ListClass();
	static {
		UType.PRIMITIVAS_JAVA_REAL.add(boolean.class);
		UType.PRIMITIVAS_JAVA_REAL.add(byte.class);
		UType.PRIMITIVAS_JAVA_REAL.add(char.class);
		UType.PRIMITIVAS_JAVA_REAL.add(double.class);
		UType.PRIMITIVAS_JAVA_REAL.add(float.class);
		UType.PRIMITIVAS_JAVA_REAL.add(int.class);
		UType.PRIMITIVAS_JAVA_REAL.add(short.class);
		UType.PRIMITIVAS_JAVA_REAL.add(long.class);
		UType.PRIMITIVAS_JAVA_REAL.add(void.class);
	}
	
	public static final ListClass PRIMITIVAS_JAVA = UType.PRIMITIVAS_JAVA_REAL.copy();
	static {
		UType.PRIMITIVAS_JAVA.add(Boolean.class);
		UType.PRIMITIVAS_JAVA.add(BigDecimal.class);				
		UType.PRIMITIVAS_JAVA.add(Byte.class);
		UType.PRIMITIVAS_JAVA.add(Character.class);
		UType.PRIMITIVAS_JAVA.add(Double.class);
		UType.PRIMITIVAS_JAVA.add(Float.class);
		UType.PRIMITIVAS_JAVA.add(Integer.class);
		UType.PRIMITIVAS_JAVA.add(BigInteger.class);
		UType.PRIMITIVAS_JAVA.add(Long.class);
		UType.PRIMITIVAS_JAVA.add(Short.class);
		UType.PRIMITIVAS_JAVA.add(Void.class);
		UType.PRIMITIVAS_JAVA.add(String.class);
	}

	public static boolean isArray(final Object o) {
		if (o == null) {
			return false;
		}
		final Class<?> classe = UClass.getClass(o);
		return classe.isArray();
	}
	
	public static boolean isPrimitiva(final Class<?> c) {
		if (UType.PRIMITIVAS_JAVA.contains(c)) {
			return true;
		}

		if (UType.isData(c))
			return true;
		// if (c.getName().startsWith("java.lang")) return true;
		// int.class
		// if (in(c.getSimpleName(), "int", "double", "boolean", "long")) return
		// true;
		if (c.isArray())
			return true;
		// if ( c.getName().startsWith(List.class.getName()) ) {
		// return true;
		// }

		if (c.isEnum()) {
			return true;
		}

		return false;
	}
	public static boolean isPrimitiva(final Object o) {
		if (o == null)
			return false;
		final Class<?> c = o.getClass();
		return UType.isPrimitiva(c);
	}
	public static boolean isPrimitivaBox(final Class<?> classe) {
		return UClass.instanceOf(classe, Numeric.class, Data.class);
	}
	public static boolean isMap(final Object o) {
		return UType.isMap(UClass.getClass(o));
	}
	public static boolean isMap(final Class<?> classe) {
		if (UClass.a_herda_b(classe, Map.class)) {
			return true;
		}
		if (classe.getName().contains("LinkedTreeMap")) {
			return true;
		}
		return false;
	}
	public static boolean isList(final Class<?> classe) {
		if (UClass.a_herda_b(classe, List.class)) {
			return true;
		}
		if (UClass.a_herda_b(classe, Set.class)) {
			return true;
		}
		final String name = classe.getName();
		return name.equals(List.class.getName()) || name.equals(Set.class.getName());
	}
	
	public static boolean isList(final Object o) {
		if (o == null) return false;
		if (o instanceof List) return true;
		if (o instanceof Set) return true;
		return UType.isList(o.getClass());
	}
	
	public static boolean isData(final Object o) {
		return UType.isData(o.getClass());
	}
	
	public static boolean isData(final Class<?> c) {
		if (c.equals(java.util.Calendar.class)) {
			return true;
		} else if (c.equals(java.util.GregorianCalendar.class)) {
			return true;
		} else if (c.equals(MyCalendar.class)) {
			return true;
		} else if (c.equals(java.util.Date.class)) {
			return true;
		} else if (c.equals(java.sql.Date.class)) {
			return true;
		} else if (c.equals(java.sql.Timestamp.class)) {
			return true;
		} else if (c.equals(Data.class)) {
			return true;
		} else if (c.equals(java.time.LocalDate.class)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static FTTT<Object, Object, Class<?>> tryCast; 
	
	public static Object tryCast(Object o, final Class<?> classe) {

		try {

			if (o == null) {
				return null;
			}
			if (o.getClass() == classe) {
				return o;
			}
			if (UClass.isInstanceOf(o, classe)) {
				return o;
			}
			if (classe.equals(String.class)) {
				
				if (o.getClass().equals(java.sql.Timestamp.class)) {
					final java.sql.Timestamp x = (Timestamp) o;
					return new Data(x).format("[ddd] - [dd]/[mm]/[yyyy] [hh]:[nn]");
				}

				// return o.toString() + o.getClass();
				return o.toString();

			}
			if (classe.equals(Long.class) || classe.equals(long.class)) {
				return ULong.toLong(o);
			}
			if (classe.equals(Double.class) || classe.equals(double.class)) {
				return UDouble.toDouble(o);
			}
			if (classe.equals(Integer.class) || classe.equals(int.class)) {
				return UInteger.toInt(o);
			}
			if (classe.equals(BigInteger.class)) {
				return UBigInteger.toBigInteger(o);
			}
			if (classe.equals(Boolean.class) || classe.equals(boolean.class)) {
				return UBoolean.toBoolean(o);
			}
			if (classe.equals(Short.class) || classe.equals(short.class)) {
				return UShort.toShort(o);
			}
			if (classe.equals(Data.class)) {
				final Data data = Data.to(o);
				return data;
			}
			if (classe.equals(Date.class)) {
				final Data data = Data.to(o);
				return data.toDate();
			}
			if (classe.equals(java.sql.Date.class)) {
				final Date data = new java.sql.Date(new Data((Date) o).toDate().getTime());
				return data;
			}
			if (classe.equals(Calendar.class)) {
				final Data data = Data.to(o);
				return data.getCalendar();
			}
			if (classe.equals(LocalDate.class)) {
				final Data data = Data.to(o);
				return data.toLocalDate();
			}
			if (classe.equals(Numeric1.class)) {
				final BigDecimal money = UBigDecimal.toMoney(o);
				final Numeric1 d = new Numeric1(money);
				return d;
			}
			if (classe.equals(Numeric2.class)) {
				final BigDecimal money = UBigDecimal.toMoney(o);
				final Numeric2 d = new Numeric2(money);
				return d;
			}
			if (classe.equals(BigDecimal.class)) {
				return UBigDecimal.toMoney(o);
			}
			if (classe.getAnnotation(Table.class) != null) {
				if (o instanceof Map) {
					@SuppressWarnings("unchecked")
					final
					Map<String, Object> map = (Map<String, Object>) o;
					o = map.get("id");
				}
				return UConfig.jpa().findById(classe, o);
			}
		} catch (final Exception e) {
		}
		
		if (UType.tryCast != null) {
			try {
				return UType.tryCast.call(o, classe);
			} catch (final Exception e) {
				// TODO: handle exception
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T parse(Object o, final Class<T> classe) {
		if (o == null) {
			return null;
		}
		if (UClass.isInstanceOf(o, classe)) {
			return (T) o;
		}
		if (classe.equals(Integer.class) || classe.equals(int.class)) {
			o = UInteger.toInt(o);
			return (T) o;
		}
		if (classe.equals(Short.class) || classe.equals(short.class)) {
			final Integer i = UInteger.toInt(o);
			o = i.shortValue();
			return (T) o;
		}
		if (classe.equals(Long.class) || classe.equals(long.class)) {
			o = ULong.toLong(o);
			return (T) o;
		}
		if (classe.equals(String.class)) {
			o = UString.toString(o);
			return (T) o;
		}
		if (classe.equals(Data.class)) {
			if (o instanceof Calendar) {
				final Calendar c = (Calendar) o;
				o = new Data(c);
				return (T) o;
			}
			o = new Data(o.toString());
			return (T) o;
		}

		if (classe.equals(Boolean.class)) {
			return (T) UBoolean.toBoolean(o);
		}

		if (classe.equals(boolean.class)) {

			Boolean v = UBoolean.toBoolean(o);

			if (v == null) {
				v = false;
			}

			return (T) v;

		}

		throw UException.runtime("N"+UConstantes.a_til+"o "+UConstantes.e_agudo+" poss"+UConstantes.i_agudo+"vel parse(" + o + ") to " + classe.getSimpleName());
		
	}

	public static Object cast(Object o, final Class<?> classe) {
		if (o == null) {
			return null;
		}
		o = UType.tryCast(o, classe);
		if (o == null) {
			throw UException.runtime("Utils.cast(" + o + ") - N"+UConstantes.a_til+"o implementado: " + classe);
		}
		return o;
	}
	
}
