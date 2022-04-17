package gm.utils.classes;

import java.util.Comparator;

public class ClassSortComparator implements Comparator<Class<?>>{
	@Override
	public int compare(Class<?> a, Class<?> b) {
		return a.getName().compareTo(b.getName());
	}
}
