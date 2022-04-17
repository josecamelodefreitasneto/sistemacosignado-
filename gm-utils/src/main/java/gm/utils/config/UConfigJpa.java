package gm.utils.config;

import java.sql.Connection;
import java.util.Collection;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;

import gm.utils.abstrato.IdObject;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.criterions.Criterio;

public abstract class UConfigJpa {
	
	public UConfigJpa() {
	}
	
	public abstract <T> T findById(Class<T> classe, Object id);
	
	public abstract void delete(Collection<?> list);

	public abstract void beforeSelect(Criterio<?> criterio, Class<?> classe);
	
	public abstract <T> TypedQuery<T> createQuery(final Class<T> classe);
	
	public abstract <T> TypedQuery<T> createQuery(final String sql, final Class<T> classe);
	
	@Deprecated
	public abstract Criteria createCriteria(Class<?> classe);
	public abstract Connection getConnection();
	public abstract String replaceParameters(String sql);

	public abstract void beforeInsert(IdObject o);

	public ConexaoJdbc jdbc() {
		return new ConexaoJdbc(getConnection());
	}
	
}
