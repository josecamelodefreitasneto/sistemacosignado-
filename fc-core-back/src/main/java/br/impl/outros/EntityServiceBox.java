package br.impl.outros;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Component;

import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBC;
import gm.utils.jpa.UDataSource;
import gm.utils.jpa.UResultSet;
import gm.utils.jpa.criterions.Criterio;
import lombok.Getter;

@Component
public class EntityServiceBox {

	@PersistenceContext @Getter
	private EntityManager entityManager;
	
	public EntityManager em() {
		try {
			return this.getEntityManager();
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}

	public Connection getConnection() {
		try {
			return this.getDataSource().getConnection();
		} catch (final SQLException e) {
			throw UException.runtime(e);
		}
	}

	public DataSource getDataSource() {
		return new UDataSource() {
			@Override
			public Connection getConnection() {
				return EntityServiceBox.this.em().unwrap(SessionImpl.class).connection();
			}
		};
	}
	
	private DriverJDBC driver;
	
	public DriverJDBC getDriver() {
		if (this.driver == null) {
			this.driver = new ConexaoJdbc(this.getConnection()).getDriver();
		}
		return this.driver;
	}
	
	public UResultSet rs(final String sql){
		try (Statement stmt = this.getDataSource().getConnection().createStatement()){
			return new ConexaoJdbc(this.getConnection()).rs(sql, stmt, true);
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}

	public void persist(final Object o) {
		try {
			this.em().persist(o);
		} catch (final Exception e) {
			final Throwable trata = UException.trata(e);
			throw new RuntimeException(trata);
		}
	}

	public <T> T merge(final T o) {
		return this.em().merge(o);
	}

	public <T> T find(final Class<T> classe, final Integer id) {
		return this.em().find(classe, id);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(final Class<T> classe) {
	    final Query query = this.em().createQuery("SELECT e FROM "+classe.getSimpleName()+" e");
	    return query.getResultList();
	}
	
	public <T> Criterio<T> criterio(final Class<T> classe) {
		return new Criterio<>(classe, this.em());
	}
	
}