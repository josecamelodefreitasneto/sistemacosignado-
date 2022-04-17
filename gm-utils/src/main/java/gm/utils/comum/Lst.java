package gm.utils.comum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.lambda.FTT;
import gm.utils.lambda.FTTT;

public class Lst<T> implements List<T> {
	
	private final List<T> list;
	
	public Lst() {
		this.list = new ArrayList<>();
	}
	public Lst(final List<T> list) {
		this.list = UList.distinct(list);
	}
		
	public Lst<T> filter(final Predicate<T> predicate) {
		return new Lst<>(stream().filter(predicate).collect(Collectors.toList()));
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean contains(final Object o) {
		return this.list.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.list.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(final T[] a) {
		return this.list.toArray(a);
	}

	@Override
	public boolean add(final T e) {
		return this.list.add(e);
	}

	@Override
	public boolean remove(final Object o) {
		return this.list.remove(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public T get(final int index) {
		return this.list.get(index);
	}

	@Override
	public T set(final int index, final T element) {
		return this.list.set(index, element);
	}

	@Override
	public void add(final int index, final T element) {
		this.list.add(index, element);
	}

	@Override
	public T remove(final int index) {
		return this.list.remove(index);
	}

	@Override
	public int indexOf(final Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		return this.list.listIterator(index);
	}

	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}
	
	public <TT> Lst<TT> map(final FTT<TT,T> func) {
		return UList.map(this, func);
	}
	public T getLast() {
		return UList.getLast(list);
	}
	public T uniqueObrig(Predicate<T> predicate) {
		return UList.filterUniqueObrig(list, predicate);
	}
	public T unique(Predicate<T> predicate) {
		return UList.filterUnique(list, predicate);
	}
	public boolean exists(Predicate<T> predicate) {
		return filter(predicate).size() > 0;
	}
	public <RESULT> RESULT reduce(FTTT<RESULT, RESULT, T> func, RESULT startValue) {
		return UList.reduce(this, func, startValue);
	}
	@Override
	public String toString() {
		return list.toString();
	}
}
