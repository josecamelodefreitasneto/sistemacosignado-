package gm.utils.number;

import java.util.List;
import java.util.Set;

import gm.utils.abstrato.IdObject;
import gm.utils.abstrato.Lista;
import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ListInteger extends Lista<Integer>{

	private static final long serialVersionUID = 1;
	
	public static void main(String[] args) {
		ListInteger list = new ListInteger();
		list.add(1);
		list.add(3);
		list.add(7);
		list.add(2);
		list.sort();
		list.print();
	} 
	
	public ListInteger() {
	}
	
	public ListInteger(Set<Integer> keySet) {
		this();
		add(keySet);
	}
	
	public void inc(int x){
		ListInteger list = copy();
		clear();
		for (Integer o : list) {
			add(o+x);
		}
	}
	
	public void dec(int x){
		ListInteger list = copy();
		clear();
		for (Integer o : list) {
			add(o-x);
		}
	}

	public static ListInteger byDelimiter(String s, String delimiter) {
		
		ListInteger o = new ListInteger();
		
		ListString list = ListString.byDelimiter(s, delimiter);
		
		for (String string : list) {
			o.add( UInteger.toInt(string) );
		}
		
		return o;
		
	}

	public static ListInteger destrincha(String s) {
		if ( !UInteger.isLongInt(s) ) {
			throw UException.runtime("Deve ser 100% num"+UConstantes.e_agudo+"rico: " + s );
		}
		
		ListInteger list = new ListInteger();
		
		while ( !UString.isEmpty(s) ) {
			int x = UInteger.toInt(s.substring(0,1));
			s = s.substring(1);
			list.add(x);
		}
		
		return list;
	}

//	@Override
	public ListInteger removeLast(int quantidade) {
		return (ListInteger) super.removeLast_(quantidade);
	}

//	@Override
	public ListInteger remove(int index, int quantidade) {
		return (ListInteger) super.remove_(index, quantidade);
	}

	public ListInteger(int... is) {
		for (int i : is) {
			add(i);
		}
	}

	public ListInteger(List<Integer> list) {
		for (int i : list) {
			add(i);
		}
	}

	public ListInteger inverter() {
		ListInteger list = new ListInteger();
		for (int i : this) {
			list.add(0, i);
		}
		clear();
		addAll(list);
		return this;
	}
	
	public void print(){
		for (Integer i : this) {
			ULog.debug(i);
		}
	}

	public static ListInteger array(Integer... ints) {
		ListInteger list = new ListInteger();
		for (int i : ints) {
			list.add(i);
		}
		return list;
	}
//	public static ListInteger array(int... ints) {
//		ListInteger list = new ListInteger();
//		for (int i : ints) {
//			list.add(i);
//		}
//		return list;
//	}

	public ListLong toLong() {
		ListLong list = new ListLong();
		for (Integer o : this) {
			list.add(o);
		}
		return list;
	}

	public int sum() {
		int x = 0;
		for (Integer o : this) {
			x += o;
		}
		return x;
	}
	public static ListInteger split(String s, String delimitador) {
		ListString split = ListString.split(s, delimitador);
		ListInteger list = new ListInteger();
		for (String string : split) {
			list.add( UInteger.toInt(string) );
		}
		return list;
	}
	@Override
	public String toString() {
		String s = super.toString();
		s = s.replace("[", "(");
		s = s.replace("]", ")");
		return s;
	}
	public ListInteger removeFirsts(int count) {
		ListInteger list = new ListInteger();
		for (int i = 0; i < count; i++) {
			list.add( remove(0) );
			if (isEmpty()) {
				break;
			}
		}
		return list;
	}
	public void maisIgual(int i) {
		ListInteger list = copy();
		clear();
		for (Integer x : list) {
			add(x + i);
		}
	}
	public static ListInteger readIds(List<? extends IdObject> list) {
		ListInteger o = new ListInteger();
		for (IdObject e : list) {
			o.add(e.getId());
		}
		return o;
	}
	public void remover(int value) {
		Object o = value;
		remove(o);
	}
	public void sort() {
		sort(new ComparatorInteger());
	}
}
