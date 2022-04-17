package gm.utils.classes;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ListClass extends ArrayList<Class<?>>{
	
	private boolean locked = false;

	public ListClass(){
		super();
	}
	
	public ListClass(final Class<?>... classes){
		this();
		for (final Class<?> classe : classes) {
			this.add(classe);
		}
	}
	
	private static final long serialVersionUID = 1;

	@Override
	public Class<?> remove(int index) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.remove(index);
	}
	
	@Override
	public boolean remove(Object o) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.remove(o);
	}
	
	@Override
	public boolean add(final Class<?> e) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.add(e);
	}
	
	public ListClass getByAnnotation(final Class<? extends Annotation> a) {
		final ListClass list = new ListClass();
		for (final Class<?> c : this) {
			if ( c.isAnnotationPresent(a) ) {
				list.add(c);
			}
		}
		return list;
	}	

	public void print() {
		for (final Class<?> c : this) {
			ULog.debug(c.getName());
		}
		ULog.debug("Total: " + size() + " classes");
	}

	public ListClass whereExtends(final Class<?> classe) {
		final ListClass list = new ListClass();
		for (final Class<?> c : this) {
			if (UClass.instanceOf(c, classe)) {
				list.add(c);
			}
		}
		return list;
	}
	public ListClass whereNotExtends(final Class<?> classe) {
		final ListClass list = new ListClass();
		for (final Class<?> c : this) {
			if (!UClass.instanceOf(c, classe)) {
				list.add(c);
			}
		}
		return list;
	}

	public ListClass copy() {
		final ListClass list = new ListClass();
		list.addAll(this);
		return list;
	}
		
	public void sortAsImport() {
		final Comparator<Class<?>> importComparator = (o1, o2) -> {
			final String a = UString.beforeLast(o1.getName(),".");
			final String b = UString.beforeLast(o2.getName(),".");
			
			if (a.equals(b)) {
				return o1.getSimpleName().compareTo(o2.getSimpleName());
			}
			
			if (a.startsWith("java.")) {
				if (b.startsWith("java.")) {
					return a.compareTo(b);
				} else {
					return -1;
				}
			}
			
			if (b.startsWith("java.")) {
				return 1;
			}

			return a.compareTo(b);
		};
		this.sort(importComparator);
		this.sort(importComparator);
		this.sort(importComparator);
	}

	public boolean removeIfPackage(final String s) {
		final String x = s + ".";
		final Predicate<Class<?>> filter = t -> t.getName().startsWith(x);
		return removeIf(filter);
	}
	public boolean removeIfNotPackage(final String s) {
		final String x = s + ".";
		final Predicate<Class<?>> filter = t -> !t.getName().startsWith(x);
		return removeIf(filter);
	}
	
	public boolean removeIfSimpleNameStartsWith(final String s) {
		final Predicate<Class<?>> filter = t -> t.getSimpleName().startsWith(s);
		return removeIf(filter);
	}

	public void sort() {
		final ClassSortComparator comparator = new ClassSortComparator();
		this.sort(comparator);
	}
	public void addIfNotContains(final Class<?> classe) {
		if (!this.contains(classe)) {
			this.add(classe);
		}
	}
	public void addIfNotContains(final List<?> list) {
		for (final Object classe : list) {
			if (!this.contains(classe)) {
				this.add((Class<?>) classe);
			}
		}
	}
	public Class<?> get(final GetClassName getter) {
		return this.get(getter.getClassName());
	}
	public <T> Class<T> getObrig(final GetClassName getter) {
		return this.getObrig(getter.getClassName());
	}
	public <T> Class<T> getObrig(final String name) {
		final Class<T> classe = this.get(name);
		if (classe == null) {
			throw UException.runtime("Classe n"+UConstantes.a_til+"o encontrada: " + name);
		}
		return classe;
	}
	@SuppressWarnings("unchecked")
	public <T> Class<T> get(final String name) {
		if (UString.isEmpty(name)) {
			throw UException.runtime("O nome da classe est"+UConstantes.a_agudo+" em branco");
		}
		for (final Class<?> classe : this) {
			if (classe.getSimpleName().equalsIgnoreCase(name) || classe.getName().equalsIgnoreCase(name)) {
				return (Class<T>) classe;
			}
		}
		return null;
	}

	public Class<?> removeLast() {
		return this.remove(size() - 1);
	}

	public ListClass filter(final Predicate<Class<?>> predicate) {
		final List<Class<?>> collect = stream().filter(predicate).collect(Collectors.toList());
		final ListClass list = new ListClass();
		list.addAll(collect);
		return list;
	}

	public ListClass join(final ListClass list) {
		final ListClass copy = copy();
		for (final Class<?> classe : list) {
			copy.addIfNotContains(classe);
		}
		return copy;
	}

	public ListString getNames() {
		final ListString list = new ListString();
		for (final Class<?> classe : this) {
			list.addIfNotContains(classe.getSimpleName());
		}
		return list;
	}

	public static ListClass safe(final ListClass list) {
		return list == null ? new ListClass() : list;
	}
	
	public boolean contains(final String s) {
		return getNames().contains(s);
	}

	public void lock() {
		locked = true;
	}
	
}
