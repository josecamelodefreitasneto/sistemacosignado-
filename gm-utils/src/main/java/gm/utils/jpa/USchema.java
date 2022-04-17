package gm.utils.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.string.UString;

public class USchema {
	
	public static String SCHEMA_DEFAULT;
	
	private final ConexaoJdbc con;
	private final DriverJDBC driver;
	private static Map<Class<?>, String> map = new HashMap<>();

	public USchema(final ConexaoJdbc con) {
		this.con = con;
		this.driver = con.getDriver();
	}

	public void create(final String nome) {
		if (!this.exists(nome)) {
			this.con.exec("create schema " + nome);
//			con.criarViewsParaFuncionamento();
		}
	}	
	
	public boolean exists(final String nome) {
		return this.getId(nome) != null;
	}

	public void create(final Table table) {
		this.create(USchema.get(table));
	}
	
	public static String get(final Class<?> classe) {
		String s = map.get(classe);
		if (s != null) {
			return s;
		}
		
		final Table table = classe.getAnnotation(Table.class);
		if (table == null) {
			throw UException.runtime("A classe "+ classe.getSimpleName() + " est"+UConstantes.a_agudo+" sem a anotacao @Table");
		}
		s = get(table);
		map.put(classe, s);
		return s;
	}

	public static String get(final Table table) {
		final String s = table.schema();
		if (UString.isEmpty(s)) {
			if (SCHEMA_DEFAULT == null) {
				throw UException.runtime("table schema is empty");
			} else {
				return SCHEMA_DEFAULT;
			}
		}
		return s;
	}

	public Integer getId(final String schema) {
		if (this.driver == DriverJDBC.PostgreSQL) {
			return this.con.selectInt("select 1 from pg_catalog.pg_namespace where lower(nspname) = '" + schema.toLowerCase() + "'");
		} else if (this.driver == DriverJDBC.MSSQLServer) {
			return this.con.selectInt("select schema_id from sys.schemas where lower(name) = '" + schema.toLowerCase() + "'");
		} else {
			throw UException.runtime("driver nao configurado: " + this.driver);
		}
	}
	
}
