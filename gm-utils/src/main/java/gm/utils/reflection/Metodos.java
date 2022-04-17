package gm.utils.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class Metodos extends ArrayList<Metodo>{
	
	private static final long serialVersionUID = 1L;
	private Class<?> classe;
	
	public Metodos(){
		
	}
	protected Metodos(Class<?> classe){
		this.classe = classe;
		
		List<Method> methods = read(classe);
		
		ListString adicionados = new ListString();
		
		while ( classe != null && classe != Object.class ) {
			
			for (Method method : methods) {
				if (method.isSynthetic()) {
					continue;
				}
				if (method.getDeclaringClass() != classe) {
					continue;
				}
				if (method.getDeclaringClass().equals(Object.class)) {
					continue;
				}
				String s = method.toString();
				s = UString.beforeFirst(s, "(");
				s = UString.afterLast(s, " ");
				s = UString.beforeFirst(method.toString(), " " + s + "(");
				s = UString.afterFirst(method.toString(), s + " ");
				s = UString.afterFirst(s, classe.getName() + ".");
				
				if (adicionados.contains(s)) {
					continue;
				}
				
//				resolver metodos genericos sobrescritos
//				nao serve para todos os casos, depois terei que aperfei"+UConstantes.cedilha+"oar
				if (s.endsWith("(java.lang.Object)")) {
					String nome = UString.beforeFirst(s, "(");
					if (adicionados.contains(o -> o.startsWith(nome + "(") && !o.contains(","))) {
						continue;	
					}
				}
				
				add( new Metodo(this, method) );
				adicionados.add(s);
				
			}
			classe = classe.getSuperclass();
		}
	}
	
	private static List<Method> read(Class<?> c) {
		
		List<Method> result = new ArrayList<>();
		
		if (c.getName().contains("$ReflectUtils$")) {
			return result;
		}
		
		Class<?>[] interfaces = c.getInterfaces();
		
		while (c != null && c != Object.class) {
			
			Method[] list = c.getDeclaredMethods();
			
			for (Method o : list) {
				String n = o.getName();
				if (n.equals("finalize") || n.equals("clone") || n.equals("register")) {
					continue;
				}
//				if ( java.lang.reflect.Modifier.isVolatile(o.getModifiers()) ) {
//					continue;
//				}
				o.setAccessible(true);
				result.add(o);
			}
			
			c = c.getSuperclass();
			
		}
		for (Class<?> i : interfaces) {
			result.addAll( read(i) );
		}
		return result;
	}	
	
	public void removeIfClass(final Class<?> classe){
		Predicate<Metodo> filter = new Predicate<Metodo>() {
			@Override
			public boolean test(Metodo t) {
				return t.getClasseReal().equals(classe);
			}
		};
		removeIf(filter);
	}
	
	public Metodos sobrescritos(){
		Metodos metodos = new Metodos();
		metodos.classe = this.classe;
		for (Metodo m : this) {
			if (m.isOverride()) {
				metodos.add(m);
			}
		}
		return metodos;
	}
	public void removeSobrescritos() {
		remove(sobrescritos());
	}
	public void remove(Metodos list){
		removeAll(list);
	}
	public Metodos remove(String nome){
		Metodos list = find(nome);
		remove(list);
		return list;
	}
	public Metodos find(String nome) {
		Metodos list = new Metodos();
		for (Metodo o : this) {
			if ( o.nome().equalsIgnoreCase(nome) ) {
				list.add(o);
			}
		}
		return list;
	}
	public Metodo getObrig(String nome) {
		Metodo metodo = get(nome);
		if (metodo == null) {
			throw UException.runtime("M"+UConstantes.e_agudo+"todo n"+UConstantes.a_til+"o encontrado na classe: " + getClasse().getSimpleName() + "." + nome);
		}
		return metodo;
	}
	public Metodo get(String nome, int parameters) {
		List<Metodo> list = find(nome);
		if (list.isEmpty()) {
			return null;
		}
		List<Metodo> list2 = new ArrayList<>();
		for (Metodo metodo : list) {
			if ( metodo.getParameterCount() == parameters ) {
				list2.add(metodo);
			}
		}
		if (list2.isEmpty()) {
			return null;
		}		
		if (list2.size() > 1) {
			throw UException.runtime("Foi encontrado mais de um metodo com o nome " + nome);
		}
		return list2.get(0);
		
	}
	public Metodo get(String nome) {
		Metodos list = find(nome);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() > 1) {
			throw UException.runtime("mais de um metodo com o nome " + nome);
		} else {
			return list.get(0);
		}
	}
	public Metodo get(String nome, Class<?>... parametros) {
		List<Metodo> list = find(nome);
		for (Metodo metodo : list) {
			if (metodo.isParameters(parametros)) {
				return metodo;
			}
		}
		return null;
	}
	
	public void removeSeContemParametros() {
//		removeIf( o -> !o.getParameters().isEmpty() );
		Predicate<Metodo> filter = new Predicate<Metodo>() {
			@Override
			public boolean test(Metodo t) {
				return t.getParameterCount() > 0;
			}
		};
		removeIf(filter);
	}
	public void removeVoids() {		
//		removeIf( o -> o.returnVoid() );
		Predicate<Metodo> filter = new Predicate<Metodo>() {
			@Override
			public boolean test(Metodo t) {
				return t.returnVoid();
			}
		};
		removeIf(filter);
	}
	public void removeHerdados() {
//		removeIf( o -> o.getClasseReal().equals(classe) );
		
		final Class<?> classe = this.classe;
		Predicate<Metodo> filter = new Predicate<Metodo>() {
			@Override
			public boolean test(Metodo t) {
				return !t.getClasseReal().equals(classe);
			}
		};
		removeIf(filter);
	}
	public void print() {
//		forEach( o -> o.print() );
		for (Metodo o : this) {
			o.print();
		}
	}
	public Metodos getWhereNomeEndsWith(String s) {
		Metodos list = new Metodos();
		for (Metodo a : this) {
			if (a.nome().endsWith(s)) {
				list.add(a);
			}
		}
		return list;
	}
	public void removeifRetorno(final Class<?> classe) {
//		removeIf( o -> o.retorno().equals(classe) );
		Predicate<Metodo> filter = new Predicate<Metodo>() {
			@Override
			public boolean test(Metodo t) {
				return t.retorno().equals(classe);
			}
		};
		removeIf(filter);
	}
	public void sort() {
//		sort( (a,b) -> a.nome().compareTo(b.nome()) );
		Comparator<Metodo> c = new Comparator<Metodo>() {
			@Override
			public int compare(Metodo a, Metodo b) {
				return a.nome().compareTo(b.nome());
			}
		};
		sort(c);
	}
	@Override
	public Metodos clone() {
		return (Metodos) super.clone();
	}
	public void removeAbstracts() {
		removeIf(o -> o.isAbstract());
	}
	public Metodos filter(Predicate<Metodo> predicate) {
		List<Metodo> list = stream().filter(predicate).collect(Collectors.toList());
		Metodos metodos = new Metodos();
		metodos.addAll(list);
		return metodos;
	}
	public boolean contains(Predicate<Metodo> predicate) {
		return !filter(predicate).isEmpty();
	}
	public boolean contains(String nome) {
		return get(nome) != null;
	}
	public ListString getNames() {
		ListString list = new ListString();
		for (Metodo o : this) {
			list.add(o.nome());
		}
		return list;
	}
}
