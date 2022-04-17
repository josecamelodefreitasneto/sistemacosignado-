package gm.utils.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.config.UConfig;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UException {

	public static Throwable getTarget(final Throwable e) {
		final Atributo a = UException.getAtributoTarget(e);
		if (a == null) return null;
		return (Throwable) a.get(e);
	}

	private static Atributo getAtributoTarget(final Throwable e) {
		return ListAtributos.get( UClass.getClass(e) ).get("target");
	}

	private static void setTarget(final Throwable e, final Throwable value) {
		final Atributo a = UException.getAtributoTarget(e);
		if (a != null) {
			a.set(e, value);
		}
	}
	
	public static RuntimeException runtime(final String message) {
		RuntimeException e = new RuntimeException(message);
		e = (RuntimeException) UException.trata(e);
		return e;
	}
	
	public static RuntimeException runtime(Throwable e) {

		if (e instanceof InvocationTargetException) {
			final InvocationTargetException x = (InvocationTargetException) e;
			e = x.getTargetException();
		}

		RuntimeException r;
		if (e instanceof RuntimeException) {
			r = (RuntimeException) e;
		} else {
			r = new RuntimeException(e);
		}
		return r;

	}
	
	public static RuntimeException runtime(final String message, final Throwable e) {
		return new RuntimeException(message, e);
	}
	
	public static ListString trace(final Throwable e) {
		return new ListString(GetTrace.getList(e));
	}
	
	public static Throwable trata(final Throwable e) {
		return UException.trata(e, null);
	}
	
	public static Throwable trata(Throwable e, final String mensagemExtra) {

		if (e instanceof InvocationTargetException) {
			final InvocationTargetException x = (InvocationTargetException) e;
			e = x.getTargetException();
		}
		
		final Throwable original = e;
		
		final ListString messages = new ListString();
		
		if (mensagemExtra != null) {
			messages.add(mensagemExtra);
		}
		
		final ListString list = UException.trace(e);
//		list.print();
		list.remove(0);
		list.remove(0);
		
		final List<StackTraceElement> traces = new ArrayList<>();
		
		while (e != null && !list.isEmpty()) {
			
			if (!UString.isEmpty(e.getMessage())) {
				messages.addIfNotContains(e.getMessage());
			}
			
			StackTraceElement[] stackTrace = e.getStackTrace();
			final List<StackTraceElement> tracesJoin = new ArrayList<>();
			for (final StackTraceElement stackTraceElement : stackTrace) {
				tracesJoin.add(stackTraceElement);
			}
			
			final Throwable target = UException.getTarget(e);
			
			if (target != e && target != null) {
				stackTrace = target.getStackTrace();
				for (final StackTraceElement stackTraceElement : stackTrace) {
					tracesJoin.add(stackTraceElement);
				}
			}				

			while (!list.isEmpty()) {
				String s = list.remove(0);				

				for (final StackTraceElement o : tracesJoin) {
//					if (o.toString().contains("StringIndexOutOfBoundsException")) {
//						System.out.println(o);
//					}
					if (s.equals(o.toString())) {
						traces.add(o);
						if (list.isEmpty()) {
							break;
						}
						s = list.remove(0);
					}
				}
				
			}
			
			e = e.getCause();
			
		}
		
		messages.removeEmptys();
		
		final StackTraceElement[] trace = new StackTraceElement[traces.size()+messages.size()];
		
		int index = 0;
		
		
		for (final String m : messages) {
			final StackTraceElement ste = new StackTraceElement(m, "", "", 0);
			trace[index] = ste;
			index++;
		}
		
//		if (traces.isEmpty()) {
//			ULog.debug("c");
//		}
		
		for (int j = 0; j < traces.size(); j++) {
			trace[j+index] = traces.get(j);
		}
		
		e = original;
		if (e != null) {
			e.setStackTrace(trace);
			UException.setTarget(e, null);
		}
		return e;
	}
	
	
	public static void printTrace(final Exception e) {
		e.printStackTrace();
	}

	public static RuntimeException business(final String message) {
		return UException.runtime(message);
	}

	private static Throwable lastPrinted;
	
	public static void printTrace(Throwable e){

		if (UConfig.get() == null || !UConfig.get().onLine()) {
			e.printStackTrace();
			return;
		}
		
		if (UException.lastPrinted == e) {
			return;
		}
		
		e = UException.trata(e);
		
		if (!(e instanceof MessageException)) {
			e.printStackTrace();
		}
		
		UException.lastPrinted = e;
		
	}

	public static void printTrace(final String message) {
		UException.printTrace( UException.runtime(message) );
	}	
	
}
