package gm.utils.jpa;

public interface PersisterMaster {
	PersisterManager<?> get(Class<?> classe);
}
