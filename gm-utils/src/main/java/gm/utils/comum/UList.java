package gm.utils.comum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.lambda.FTT;
import gm.utils.lambda.FTTT;
import gm.utils.number.ListInteger;
import gm.utils.number.UInteger;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UList {
	
	private static Atributos getAtributos(List<?> list) {
		if (isEmpty(list)) {
			return null;
		} else {
			return ListAtributos.get( UClass.getClass( list.get(0) ) );
		}
	}
	private static Atributo getAtributo(List<?> list, String nome) {
		if (isEmpty(list)) {
			return null;
		} else {
			return getAtributos(list).getObrig(nome);
		}
	}
	private static Metodos getMetodos(List<?> list) {
		if (isEmpty(list)) {
			return null;
		} else {
			return ListMetodos.get( UClass.getClass( list.get(0) ) );
		}
	}
	private static Metodo getMetodo(List<?> list, String nome) {
		if (isEmpty(list)) {
			return null;
		} else {
			return getMetodos(list).getObrig(nome);
		}
	}

	public static <T> List<T> getWhere(List<T> list, String fieldName, Object value){
		List<T> l = new ArrayList<>();
		if (list.isEmpty()) {
			return l;
		}
		Atributo atributo = getAtributo(list, fieldName);
		for (T o : list) {
			Object v = atributo.get(o);
			if (UCompare.equals(v, value)) {
				l.add(o);
			}
		}
		return l;
	}
	
	public static <T> boolean exists(List<T> list, Predicate<T> predicate) {
		return !filter(list, predicate).isEmpty();
	}
	
	public static boolean exists(List<?> list, String fieldName, Object value){
		return !getWhere(list, fieldName, value).isEmpty();
	}

	public static void sortByMethod(List<?> list, String nomeMetodo) {

		if (isEmpty(list) || list.size() == 1) return;
		
		try {
			
			final Metodo metodo = getMetodo(list, nomeMetodo);
			
			Comparator<Object> c = (a, b) -> {
				try {
					Object va = metodo.invoke(a);	
					Object vb = metodo.invoke(b);
					return UCompare.compare(va, vb);
				} catch (Exception e) {
					throw UException.runtime(e);
				}
			};
			
			list.sort(c);
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void inverteOrdem(List list) {
		List list2 = new ArrayList<>();
		for (Object o : list) {
			list2.add(0, o);
		}
		list.clear();
		list.addAll(list2);
	}
	
	public static void sort(List<?> list, Atributos as) {
		orderByField(list, map(as, a -> a.nome()));
	}
	
	public static void orderByField(List<?> list, final List<String> fields) {
	
		if ( isEmpty(list) || list.size() == 1 || isEmpty(fields) ) return;
		
		try {
			
			Comparator<Object> c = (a, b) -> {
				
				try {
					
					for (String field : fields) {

						Object va = UExpression.getValue(field, a);	
						Object vb = UExpression.getValue(field, b);
						int i = UCompare.compare(va, vb);
						
						if (i != 0) {
							return i;
						}
						
					}
					
					return 0;
					
				} catch (Exception e) {
					throw UException.runtime(e);
				}
				
			};
			
			list.sort(c);
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}
	
	public static void orderByField(List<?> list, String... fields) {
		orderByField(list, ListString.newFromArray(fields));
	}

	public static void clear(List<?>... listas) {

		for (List<?> list : listas) {
			
			if (list != null) {
				list.clear();
			}
			
		}
		
	}

	public static boolean isEmpty(Collection<?> list){
		return list == null || list.isEmpty();
	}
	
	public static String hashFiltro(List<?> list) {
		
		if (isEmpty(list)) {
			return "";
		}

		//TODO melhorar
		return list.toString();
		
	}

	public static <T> T last(List<T> list) {
		if ( isEmpty(list) ) {
			return null;
		}
		T o = list.get( list.size() -1 );
		return o;
	}
	public static <T> T removeLast(List<T> list) {
		if ( isEmpty(list) ) {
			return null;
		}
		T o = list.remove( list.size() -1 );
		return o;
	}
	public static <T> T getLast(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		T o = list.get( list.size() -1 );
		return o;
	}

	public static <T> List<T> copy(List<T> list) {
		List<T> copy = new ArrayList<>();
		copy.addAll(list);
		return copy;
	}

	public static void remove(List<?> list, Object o) {
		while (list.contains(o)) {
			list.remove(o);
		}
	}

	public static void addUniqueNotNull(List<Object> list, Object o) {
		
		if (o == null) {
			return;
		}
		
		if (list.contains(o)) {
			return;
		}
		
		list.add(o);
		
	}

	public static void formatData(List<?> list, String format, Atributos as) {

		if (isEmpty(list)) {
			return;
		}
		
		for (Object o : list) {
			
			for (Atributo a : as) {
			
				String s = a.getString(o);
				
				if (UString.isEmpty(s)) {
					s = "";
				} else {
					s = Data.fromSql(s).format(format);
				}
				
				a.set(o, s);
				
			}
		}
	}
	
	public static void formatData(List<?> list, String format, String... fields) {

		if (isEmpty(list)) {
			return;
		}
		
		Atributos as = getAtributos(list);
		Atributos atributos = new Atributos();
		
		for (String field : fields) {
			atributos.add( as.getObrig(field) );
		}
		
		formatData(list, format, atributos);
		
	}

	public static void toNomeProprio(List<?> list, Atributo a) {
		for (Object o : list) {
			String s = a.getString(o);
			if (UString.isEmpty(s)) {
				a.set(o, "");
			} else {
				a.set(o, UString.toNomeProprio(s));
			}
		}
		
	}
	
	public static void toNomeProprio(List<?> list, String field) {
		if (isEmpty(list)) {
			return;
		}
		Atributo atributo = getAtributo(list, field);
		toNomeProprio(list, atributo);
	}

	public static void remove(List<?> list, String field, final Object value) {
		
		if (isEmpty(list)) {
			return;
		}
		
		final Atributo a = getAtributo(list, field);
		
		Predicate<Object> filter = o -> UCompare.equals(a.get(o), value);
		list.removeIf(filter);
	}

	public static <T> List<T> asList(T[] os) {
		if (os == null) {
			return new ArrayList<T>();
		}
		//obs: n"+UConstantes.a_til+"o usar Arrays.asList
		List<T> list = new ArrayList<>();
		for (T t : os) {
			list.add(t);
		}
		return list;
	}

	public static void sort(List<?> list, String field) {
		Atributo a = getAtributo(list, field);
		sort(list, a);
	}
	
	public static void sort(List<?> list, final Atributo atributo) {
		Comparator<Object> c = (a, b) -> UCompare.compare(a, b, atributo);
		list.sort(c);
	}	
	
	public static <T> List<T> removeEmptys(Collection<T> list) {
		if (list == null) return null;
		ArrayList<T> l = new ArrayList<>();
		for (T o : list) {
			if (!UObject.isEmpty(o)) {
				l.add(o);
			}
		}
		return l;
	}

	public static <T> T getUnique(List<T> list) {
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw UException.runtime("A lista retornou + de 1 resultado (1)");
		}
		return list.get(0);		
	}
	
	public static Atributo getId(Collection<?> list) {
		if (isEmpty(list)) {
			throw UException.runtime("A lista estah vazia");
		}
		Object exemplo = list.iterator().next();
		Class<?> classe = UClass.getClass(exemplo);
		return ListAtributos.get(classe).getId();
	}
	/* retorna os itens que est"+UConstantes.a_til+"o respectivamente em ambas as listas */
	public static <T> List<T> intersecao(List<T> a, List<T> b) {
		return a.stream().filter(o -> b.contains(o)).collect(Collectors.toList());
	}

	public static <T> T get(List<T> list, int index) {
		try {
			return list.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
		list = copy(list);//para evitar ConcurrentModificationException em listas estaticas
		return list.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public static <T> T filterFirst(List<T> list, Predicate<T> predicate) {
		list = filter(list, predicate);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public static <T> T filterFirstObrig(List<T> list, Predicate<T> predicate) {
		T o = filterFirst(list, predicate);
		if (o == null) {
			throw new RuntimeException("o == null");
		} else {
			return o;
		}
	}
	
	public static <T> T filterUnique(List<T> list, Predicate<T> predicate) {
		list = filter(list, predicate);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() > 1) {
			throw new RuntimeException("A lista retornou + de 1 resultado (2)");
		} else {
			return list.get(0);
		}
	}
	
	public static <T> T filterUniqueObrig(List<T> list, Predicate<T> predicate) {
		T o = filterUnique(list, predicate);
		if (o == null) {
			throw new RuntimeException("o == null");
		} else {
			return o;
		}
	}
	public static <T> boolean contains(List<T> list, Predicate<T> predicate) {
		return filterFirst(list, predicate) != null;
	}
	
	public static <T,TT> Lst<T> map(List<TT> list, FTT<T,TT> func) {
		Lst<T> result = new Lst<>();
		list.forEach(o -> result.add(func.call(o)));
		return result;
	}
	public static <T> List<T> distinct(List<T> list) {
		List<T> lst = new ArrayList<>();
		for (T t : list) {
			if (!lst.contains(t)) {
				lst.add(t);
			}
		}
		return lst;
	}
	
	public static <TARRAY, TRESULT> Lst<TRESULT> distinct(List<TARRAY> array, FTT<TRESULT, TARRAY> func) {
		final Lst<TRESULT> list = new Lst<>();
		array.forEach(o -> {
			TRESULT value = func.call(o);
			if (!list.contains(value)) {
				list.add(value);
			}
		});
		return list;
	}
	
	public static <RESULT, T> RESULT reduce(List<T> list, FTTT<RESULT, RESULT, T> func, RESULT startValue) {
		for (T t : list) {
			startValue = func.call(startValue, t);
		}
		return startValue;
	}
	
	public static void main(String[] args) {
		ListInteger list = ListInteger.array(2,3,4,1,-2);
		Integer reduce = reduce(list, (a, b) -> UInteger.menor(a, b), list.get(0));
		System.out.println(reduce);
	}

}
