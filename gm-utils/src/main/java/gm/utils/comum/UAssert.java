package gm.utils.comum;

import java.util.List;

import gm.utils.exception.UException;

public class UAssert {

	public static <T> T notEmpty(T o, String message) {
		if (UObject.isEmpty(o)) {
			throw UException.runtime(message);
		}
		return o;
	}

	public static void isNull(Object o, String name) {
		if (!UObject.isEmpty(o)) {
			throw UException.runtime(name + " != null");
		}
	}
	
	public static boolean isTrue(Boolean o, String message) {
		if (!UBoolean.isTrue(o)) {
			throw UException.runtime(message);
		}
		return true;
	}
	public static boolean isFalse(Boolean o, String message) {
		if (!UBoolean.isFalse(o)) {
			throw UException.runtime(message);
		}
		return true;
	}
	
	public static void notEmpty(List<?> list, String message) {
		if (list == null || list.isEmpty()) {
			throw UException.runtime(message);
		}
	}

	public static void eq(Object a, Object b, String message) {
		if (UCompare.ne(a, b)) {
			throw UException.runtime(message + " : a = " + a + " != b = " + b);
		}
	}
	public static void ne(Object a, Object b, String message) {
		if (UCompare.eq(a, b)) {
			throw UException.runtime(message + " : a = " + a + " == b = " + b);
		}
	}
	
}
