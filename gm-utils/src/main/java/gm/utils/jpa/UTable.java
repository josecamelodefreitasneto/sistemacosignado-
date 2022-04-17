package gm.utils.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;

import gm.utils.anotacoes.ValorSeNulo;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.comum.UBoolean;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.map.MapSO;
import gm.utils.number.UInteger;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UTable {

	private ConexaoJdbc con;
	private Class<?> classe;
	private String ts;
	private String schema;
	private String name;
	
	UTable(ConexaoJdbc conexao, Class<?> classe){
		con = conexao;
		this.classe = classe;
		ts = UTableSchema.get(classe);
		schema = UString.beforeFirst(ts, ".");
		name = UString.afterFirst(ts, ".");
	}
	
	UTable(ConexaoJdbc conexao, String schema, String name){
		con = conexao;
		this.schema = schema;
		this.name = name;
		ts = schema + "." + name;
	}
	
	private Boolean exists;
	
	public boolean exists(){
		if (exists == null) {
			if (con.getDriver() == DriverJDBC.PostgreSQL) {
				UTable catalog = new UTable(con, "pg_catalog", "pg_tables");
				exists = catalog.exists("lower(schemaname) = '"+schema.toLowerCase()+"' and lower(tablename) = '"+name.toLowerCase()+"'");	
			} else if (con.getDriver() == DriverJDBC.MSSQLServer) {
				Integer schemaId = new USchema(con).getId(schema);
				exists = con.selectInt("select top 1 1 from sys.tables where schema_id = "+schemaId+" and lower(name) = '"+name.toLowerCase()+"'") != null; 
			} else {
				throw UException.runtime("driver nao configurado");
			}
		}
		return exists;	
	}
	public boolean exists(String where){
		return count(where) > 0;
	}
	public int count() {
		return count("0=0");
	}
	public int count(String where) {
		String s = "select count(*) from " + ts;
		if (!UString.isEmpty(where)) s += " where " + where;
		return con.selectInt(s);
	}
	public int maxId() {
		String s = "select max(id) from " + ts;
		return UInteger.toInt(con.selectInt(s),0);
	}
	public boolean exists(int id) {
		if (classe == null) {
			throw new RuntimeException("classe == null");
		}
		String columnName = ListAtributos.get(classe).getId().getColumnName();
		String where = columnName + " = " + id;
		return exists(where);
	}
	public <T> T byId(int id) {
		return selectUniqueAs("id = " + id);
	}
	public <T> T save(T o) {
		UAssert.notEmpty(o, "o == null");
		Integer id = getId(o);
		if (id == null) {
			return insert(o, id);
		} else {
			return save(o, id);
		}
	}
	private Integer getId(Object o) {
		return ListAtributos.get(o).getId().get(o);
	}
	private void setId(Object o, Integer id) {
		ListAtributos.get(o).getId().set(o, id);		
	}
	public <T> T save(T o, int id) {
		if (exists(id)) {
			return update(o, id);
		} else {
			return insert(o, id);
		}
	}
	private <T> T update(T o, int id) {
		Atributos as = ListAtributos.persist(classe, false);
		String s = "";
		for (Atributo a : as) {
			s += ", " + a.nome() + " = "+ nativeValue(a, o);
		}
		s = "update " + ts + " set " + s.substring(1) + " where id = " + id;
		con.exec(s);
		return o;
	}

	private String nativeValue(Atributo a, Object o) {
	
		Object x = a.get(o);
		
		if (UObject.isEmpty(x)) {
			
			ValorSeNulo valorSeNulo = a.getAnnotation(ValorSeNulo.class);
			if (valorSeNulo != null) {
				x = valorSeNulo.value();
			} else {
				if (a.nome().equals("excluido") || a.nome().equals("registroBloqueado")) {
					if (con.getDriver() == DriverJDBC.MSSQLServer) {
						return "0";	
					} else {
						return "false";
					}
				} else {
					return "null";
				}
			}
		}
		
		if ( a.isDate() ) {
			
			Temporal temporal = a.getAnnotation(Temporal.class);
			
			if (temporal == null) {
				throw UException.runtime("temporal == null : " + classe.getSimpleName() + " >> " + a);
			}
			
			Data data = Data.to(x);
			return data.format_sql(true);
			
		}
		
		if (con.getDriver() == DriverJDBC.MSSQLServer) {
			if ( a.isBoolean() ) {
				Boolean b = UBoolean.toBoolean(x);
				if (b == null) {
					return "null";
				}
				return b ? "1" : "0";	
			}
		}
		
		if ( a.isPrimitivo() ) {
			return "'" + x.toString().replace("'", "''") + "'";
		} else {
			return ListAtributos.persist(x.getClass(), false).getId().get(x).toString();
		}		
		
	}
	
	private <T> T insert(T o, Integer id) {

		boolean semSequence = !ListAtributos.get(o.getClass()).getId().hasAnnotation(GeneratedValue.class);
		
		if (semSequence) {
			if (id == null) {
				id = getId(o);
				if (id == null) {
					id = UInteger.toInt(con.selectInt("select max(id) from " + ts),0) + 1;
					setId(o, id);
				}
			}
		} else {
			if (id != null) {
				throw UException.runtime("Tabela com sequence: " + o.getClass().getSimpleName());
			}
		}
		
		//UConfig.jpa().beforeInsert(o);
		Atributos as = ListAtributos.persist(classe, false);
		Atributo campoId = as.getId();
		
		String colunasInsert = "";
		String valoresInsert = "";

		for (Atributo a : as) {
			String nativeValue = nativeValue(a, o);
			if (UString.isEmpty(nativeValue) || nativeValue.equals("null")) {
				if (a.nome().equals("excluido") || a.nome().equals("registroBloqueado")) {
					if (con.getDriver() == DriverJDBC.MSSQLServer) {
						nativeValue = "0";	
					} else {
						nativeValue = "false";
					}
				} else {
					continue;	
				}
			}
			colunasInsert += "," + a.getColumnName();
			valoresInsert += ", " + nativeValue + " as " + a.nome();
		}
		
		String s = "insert into " + ts + "(";		
		
		if (id == null) {
			s += colunasInsert.substring(1) + ") select " + valoresInsert.substring(1);
			con.exec(s);
			id = UInteger.toInt(con.selectInt("select max(id) from " + ts),0);
			setId(o, id);			
		} else {
			
			s += campoId.getColumnName() + colunasInsert + ") select " + id + valoresInsert;
			
			if (semSequence) {
				con.exec(s);
			} else  {
				if (con.getDriver() == DriverJDBC.MSSQLServer) {
					con.exec("set IDENTITY_INSERT "+ts+" on");
					con.exec(s);
					con.exec("set IDENTITY_INSERT "+ts+" off");
					con.tryExec("DBCC CHECKIDENT(\""+ts+"\", RESEED, "+id+")");
				} else {
					ULog.debug("????");
				}
			}
			
		}
		
		
		return o;		
	}
	
	public void drop(String column){
		con.exec("alter table "+ts+" drop column "+column);
	}
	
	public void drop(){
		if (exists()) {
			con.exec("drop table "+ts);
		}
	}
//	public void create() {
//		if (exists()) {
//			throw UException.runtime(ts + " ja existe!");
//		}
//		String sql = new UCreateTable(con).exec(classe);
//		con.exec(sql);
//		exists = true;
//		Atributos as = ListAtributos.persist(classe);
//		for (Atributo a : as) {
//			a.setExisteNoBanco(con);
//		}
//	}
//	public boolean createIfNotExists() {
//		if (!exists()) {
//			create();
//			return true;
//		} else {
//			return false;
//		}
//	}
	public int freeId() {
		return FreeId.get(classe);
	}
	public List<MapSO> select() {
		return select(null);
	}
	private String lastQuery;
	
	public <T> List<T> selectAs() {
		return selectAs(null);
	}
	
	public <T> List<T> selectAs(String where) {
		return convert(select(where));
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> convert(List<MapSO> list) {
		List<T> result = new ArrayList<>();
		for (MapSO mapSO : list) {
			T t = (T) UClass.newInstance(classe);
			mapSO.setInto(t, false);
			result.add(t);
		}
		return result;
	}
	
	public List<MapSO> select(String where) {
		return select(where, "1");
	}
	
	public List<MapSO> select(String where, String orderBy) {
		
		String s = "select * from " + ts;
		if (!UString.isEmpty(where)) {
			s += " where " + where;
		}
		s += " order by 1";
		s += " limit 5000";
		
		lastQuery = s;
		return con.selectMap(s);
		
	}

	public void ajustSequence() {
		Integer x = con.selectInt("select max(id) from " + ts);
		x++;
		String s = UString.afterFirst(ts, ".");
		s += "_id_seq";
		con.exec("alter sequence " + s + " restart with " + x);
	}
	
	public String getLastQuery() {
		return lastQuery;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T selectUniqueAs(String where) {
		MapSO map = selectUnique(where);
		if (map == null) {
			return null;
		}
		T t = (T) UClass.newInstance(classe);
		if (t == null) {
			throw UException.runtime("t == null: " + classe.getSimpleName());
		}
		try {
			map.setInto(t, false);
			return t;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public MapSO selectUnique(String where) {
		List<MapSO> select = select(where);
		if (select.isEmpty()) {
			return null;
		}
		if (select.size() > 1) {
			throw UException.runtime("A lista retornou + de 1 resultado: select * from " + ts + " where " + where );
		}
		return select.get(0);
	}
	public int update(String set, int id) {
		return update(set, "id = " + id);
	}
	public int update(String set, String where) {
		String s = "update " + ts + " set " + set + " where " + where;
		return con.exec(s);
	}
	public int delete(String where) {
		String s = "delete from " + ts + " where " + where;
		return con.exec(s);
	}
}
