package gm.utils.comum;

import gm.utils.string.UString;

public class UObject {

	public static boolean isEmpty(Object o) {
		return o == null || UString.isEmpty(o.toString());
	}

	@SafeVarargs
	public static <T> T coalesce(T... list) {
		for (T s : list) {
			if (!isEmpty(s)) {
				return s;
			}
		}
		return null;
	}
	
}
