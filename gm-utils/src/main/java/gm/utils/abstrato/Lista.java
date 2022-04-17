package gm.utils.abstrato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.classes.UClass;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.exception.UException;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Lista<T> extends ArrayList<T>{

	private boolean bloqueada = false;
	
	public Lista() {
		
	}

	static final long serialVersionUID = 1;
	boolean aceitaRepetidos = true;
	
//	public abstract Lista<T> remove(int index, int quantidade);
	
	protected final Lista<T> remove_(int index, int quantidade) {

		Lista<T> list = newInstance();
		
		while (quantidade > 0) {
			quantidade--;
			list.add(remove(index));
		}
		
		return list;

	}
	@SuppressWarnings("unchecked")
	public <X extends Lista<T>> X copy(){
		Lista<T> list = newInstance();
		list.addAll(this);
		return (X) list;
	}
	@SuppressWarnings("unchecked")
	Lista<T> newInstance(){
		return UClass.newInstance( this.getClass() );
	}
	
//	public abstract Lista<T> removeLast(int quantidade);
	
	protected final Lista<T> removeLast_(int quantidade) {

		Lista<T> list = newInstance();
		
		while (quantidade > 0) {
			quantidade--;
			list.add(0, removeLast());
		}
		
		return list;

	}
	
	public T getLast() {
		return get(size() - 1);
	}
	
	public T getPenultimo() {
		return get(size() - 2);
	}

	public T removeLast() {
		return remove(size() - 1);
	}
	
	@Override
	public boolean addAll(Collection<? extends T> list) {
		if (UList.isEmpty(list)) {
			return false;
		}
		if (!aceitaRepetidos) {
			addIfNotContains(list);
			return true;
		}
		for (T t : list) {
			add(t);
		}
		return true;
	}

	public void addIfNotContains(Collection<? extends T> list) {
		for (T o : list) {
			addIfNotContains(o);
		}
	}
	
	public Lista<T> addIfNotContains(T s) {
		if (!contains(s)) {
			add(s);
		}
		return this;
	}

	public int codePoint(int index){
		return get(index).toString().codePointAt(0);
	}
	
	@Override
	public T get(int index) {

		if (index < 0) {
			index += size();
		}

		return super.get(index);

	}
	
	public Lista<T> get(int index, int count) {
		Lista<T> list = newInstance();
		for (int i = index; i < index + count; i++) {
			list.add( get(i) );
		}
		return list;
	}
	
	public final String concat() {
		String s = "";
		for (T t : this) {
			s += t;
		}
		return s;
	}
	
	public void add(Collection<T> keySet) {
		for (T x : keySet) {
			add(x);
		}
	}
	
	public final String toString(String separador) {
		if (isEmpty()) {
			return "";
		}		
		String s = stream().map(String::valueOf).collect(Collectors.joining("|=()=|"));
		s = s.replace("\n", "|=()=|");
		return s.replace("|=()=|", separador);
	}
	
	@Override
	public boolean equals(Object o) {
		return eq(o);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (aceitaRepetidos ? 1231 : 1237);
		return result;
	}
	
	public boolean eq(Object o) {
		if (o == null) {
			return false;
		}
		if ( super.equals(o) ) {
			return true;
		}
		return UString.equals(UString.toString(o), UString.toString(this));
	}

	public boolean equals(Lista<T> o) {
		if (eq(o)) {
			return true;
		}
		if (o == null) {
			return false;
		}
		return o.toString("").equals(toString(""));
	}
	
	private void checaBloqueio() {
		if (bloqueada) {
			throw UException.runtime("A lista est"+UConstantes.a_agudo+" bloqueada!");
		}
	}
	
	@Override
	public T remove(int index) {
		checaBloqueio();
		return super.remove(index);
	}
	
	@Override
	public boolean remove(Object o) {
		checaBloqueio();
		return super.remove(o);
	}
	
//	private static int iii = 0;
	
	@Override
	public boolean add(T e) {
//		if (e.toString().endsWith("= requestType;")) {
//			e = (T) (e.toString() + iii);
//			iii++;
//		}
		checaBloqueio();
		return super.add(e);
	}
	
	@Override
	public void add(int index, T element) {
		checaBloqueio();
		super.add(index, element);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		checaBloqueio();
		return super.addAll(index, c);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		checaBloqueio();
		return super.removeAll(c);
	}
	
	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		checaBloqueio();
		return super.removeIf(filter);
	}
	
	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		checaBloqueio();
		super.removeRange(fromIndex, toIndex);
	}

	public boolean contains(Predicate<T> predicate) {
		return !filter(predicate).isEmpty();
	}
	
	public List<T> filter(Predicate<T> predicate) {
		return stream().filter(predicate).collect(Collectors.toList());
	}
	public List<T> removeAndGet(Predicate<T> predicate) {
		List<T> list = filter(predicate);
		removeAll(list);
		return list;
	}
	
	public T unique(Predicate<T> predicate) {
		List<T> filter = filter(predicate);
		if (filter.isEmpty()) {
			return null;
		} else if (filter.size() > 1) {
			throw UException.runtime("O filtro retornou + de 1 resultado");
		} else {
			return filter.get(0);
		}
	}
	public boolean some(Predicate<T> predicate) {
		return !filter(predicate).isEmpty();
	}
	
//	public void setBloqueada(boolean bloqueada) {
//		if (this.bloqueada == bloqueada) {
//			return;
//		}
//		this.bloqueada = bloqueada;
//		if (!bloqueada) {
//			System.out.println("A lista foi desbloqueada");
//		}
//	}
	
}
