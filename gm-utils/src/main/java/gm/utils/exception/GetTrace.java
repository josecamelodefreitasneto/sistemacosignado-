package gm.utils.exception;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTrace {
	
	private final List<String> list = new ArrayList<>();
	private final List<String> result = new ArrayList<>();
	private final List<String> l = new ArrayList<>();
	private static final List<String> pacotesValidos = new ArrayList<>();

	public static boolean addPacote(String s) {
		s += ".";
		if (GetTrace.pacotesValidos.contains(s)) {
			return false;
		} else {
			GetTrace.pacotesValidos.add(s);
			return true;
		}
	}
	public static boolean removePacote(final String s) {
		return GetTrace.pacotesValidos.remove(s);
	}
	
	static {
		GetTrace.addPacote("br");
		GetTrace.addPacote("gm");
		GetTrace.addPacote("app");
		GetTrace.addPacote("src");
		GetTrace.addPacote("html");
		GetTrace.addPacote("react");
		GetTrace.addPacote("reacts");
	}
	
	private GetTrace(Throwable e) {
		
		final Throwable original = e;
		
		while (e != null) {
			this.add(e);
			final Throwable x = e.getCause();
			e = x != e ? x : null;
		}

		e = original;
		
		while (e != null) {
			this.add(e);
			final Throwable x = this.getTarget(e);
			e = x != e ? x : null;
		}
		
		for (final String s : this.list) {
			if (!this.result.contains(s)) {
				this.result.add(s);
			}
		}

	}
	
	private static Map<Class<?>, Field> targets = new HashMap<>();
	private static List<Class<?>> classesSemTarget = new ArrayList<>();
	
	private static Field getFieldTarget(final Throwable e) {
		final Class<?> classe = e.getClass();
		Field field = GetTrace.targets.get(classe);
		if (field != null) {
			return field;
		}
		if (GetTrace.classesSemTarget.contains(classe)) {
			return null;
		}
		field = GetTrace.findField(classe);
		if (field == null) {
			GetTrace.classesSemTarget.add(classe);
			return null;
		} else {
			GetTrace.targets.put(classe, field);
			return field;
		}
	}
	
	private Throwable getTarget(final Throwable e) {
		final Field field = GetTrace.getFieldTarget(e);
		if (field == null) {
			return null;
		}
		try {
			return (Throwable) field.get(e);
		} catch (final Throwable e1) {
			return null;
		}
	}

	private void add(final Throwable e) {
		this.l.clear();
		this.add(e.getClass().getName());
		final StackTraceElement[] stack = e.getStackTrace();
		for (final StackTraceElement o : stack) {
			this.add(o.toString());
		}
		while (!this.l.isEmpty()) {
			this.addList(this.removeLast());
		}
		this.addList(e.getMessage());
		this.addList(e.getClass().getSimpleName());
	}

	private void addList(final String s) {
		if (!GetTrace.isEmpty(s)) {
			this.list.remove(s);
			this.list.add(0, s);
		}
	}

	private String removeLast() {
		final int index = this.l.size()-1;
		final String s = this.l.get(index);
		this.l.remove(index);
		return s;
	}
	
	private static boolean isEmpty(final String s) {
		return s == null || s.trim().isEmpty();
	}

	private void add(String s) {
		if (GetTrace.isEmpty(s)) {
			return;
		}
		if (s.contains("$$")) {
			return;
		}
		s = s.trim();
		if (s.equals("null")) {
			return;
		}
		for (final String pacote : GetTrace.pacotesValidos) {
			if (s.startsWith(pacote)) {
				this.l.add(s);
				return;
			}
		}
	}
	
	private static Field findField(Class<?> c){
		while (c != null) {
			final Field[] list = c.getDeclaredFields();
			for (final Field field : list) {
				if (field.getName().equals("target")) {
					field.setAccessible(true);
					return field;
				}
			}
			c = c.getSuperclass();
		}
		return null;
	}
	
	public static List<String> getList(final Throwable e) {
		return new GetTrace(e).result;
	}
	public static String getString(final Throwable e) {
		final List<String> list = GetTrace.getList(e);
		String s = "";
		for (final String string : list) {
			s += "\n" + string;
		}
		return s.trim();
	}
	
	public static void main(final String[] args) {
		final long inicio = System.currentTimeMillis();
		for (int i = 0; i < 1_000_000; i++) {
			GetTrace.getList(new Exception());
		}
		final long fim = System.currentTimeMillis();
		System.out.println("milisegundos: " + (fim - inicio));
	}
	
	
}
