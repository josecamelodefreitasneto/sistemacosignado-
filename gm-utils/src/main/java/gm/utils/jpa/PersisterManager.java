package gm.utils.jpa;

import java.util.List;

public interface PersisterManager<T> {

	void deleteCast(List<?> list);

	void saveCast(List<?> list);

	T get(Integer id);

	void deleteCast(Object o);

}
