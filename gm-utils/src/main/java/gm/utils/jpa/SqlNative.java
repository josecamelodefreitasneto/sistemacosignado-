package gm.utils.jpa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.map.MapSO;
import gm.utils.number.ListInteger;
import gm.utils.number.UInteger;
import gm.utils.reflection.DO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class SqlNative {

	public static Object getObject(String sql) {
		return UList.getUnique(getObjects(sql));
	}
	
	public static List<Object> getObjects(String sql) {
		return new NativeSelectMap(sql).getValues();
	}
	
	public static ListString getStrings(String sql) {
		List<Object> list = getObjects(sql);
		ListString li = new ListString();
		for (Object o : list) {
			String s = UString.toString(o);
			li.add(s);
		}
		return li;
	}
	public static List<Data> getDates(String sql) {
		List<Object> list = getObjects(sql);
		List<Data> li = new ArrayList<>();
		for (Object o : list) {
			Data data = Data.to(o);
			li.add(data);
		}
		return li;
	}
	
	public static ListInteger getInts(String sql) {
		List<Object> list = getObjects(sql);
		ListInteger li = new ListInteger();
		for (Object o : list) {
			Integer i = UInteger.toInt(o);
			li.add(i);
		}
		return li;
	}
	
	public static void disconectedQuery(String sql) {
		try {
			UConfig.jpa().getConnection().createStatement().executeQuery(sql);
		} catch (SQLException e) {
			UException.printTrace(e);
		}
	}
	
//	private static SQLQuery createSqlQuery(String sql) {
//		Dao dao = Dao.get();
//		return dao.createSqlQuery(sql);
//	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> selectTo(Class<T> classe, String sql) {
		List<MapSO> select = new NativeSelectMap(sql).map();
		List<T> list = new ArrayList<>();
		for (Object o : select) {
			T t = UClass.newInstance(classe);
			list.add(t);
			DO<T> novo = (DO<T>) DO.novo(t);
			novo.read(o);
		}
		return list;
	}	
	
	
	public static <T> T selectTo(Class<T> classe, int id) {
		String sql = "select * from " + UTableSchema.get(classe) + " where id = " + id;
		List<T> list = selectTo(classe, sql);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public static ListInteger selectInts(String sql) {
		ListInteger list = new ListInteger();
		ListString strings = SqlNative.selectStrings(sql);
		for (String s : strings) {
			list.add( UInteger.toInt(s) );
		}
		return list;
	}
	
	public static ListString selectStrings(String sql) {
		List<Object> values = new NativeSelectMap(sql).getValues();
		ListString list = new ListString();
		for (Object o : values) {
			list.add(UString.toString(o));
		}
		return list;
	}
	
	public static String selectString(String sql) {

		if (!sql.contains(" limit ")) {
			sql += " limit 2";
		}

		ListString list = SqlNative.selectStrings(sql);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw UException.runtime("A lista retornou + de 1 resultado (3)");
		}
		return list.get(0);
	}

	public static void execSQL(ListString sql) {
		execSQL(sql.toString("\n"));
	}

	public static Integer getIntTry(String sql, Integer def) {
		try {
			return getInt(sql);
		} catch (Exception e) {
			return def;
		}
	}
	
	public static Integer getInt(String sql, Integer def) {
		Integer o = getInt(sql);
		if (o == null) {
			return def;
		} else {
			return o;
		}
	}
	
	public static Integer getInt(String sql) {
		try {
			return selectInt(sql);
		} catch (Exception e) {
			ULog.error("Erro ao executar o seguinte sql:\n"+sql);
			throw e;
		}
	}
	public static Integer selectInt(String sql) {
		ListInteger list = SqlNative.getInts(sql);
		return UList.getUnique(list);
	}

	public static Data selectData(String sql) {
		String s = SqlNative.selectString(sql);
		if (s == null) {
			return null;
		}
		if (s.length() == 23) {
			s = UString.beforeLast(s, ".");
		}
		if (s.length() == 19) {
			return Data.unformat("[yyyy]-[mm]-[dd] [hh]:[nn]:[ss]", s);	
		} else {
			return Data.unformat("[yyyy]-[mm]-[dd]", s);
		}
	}
	
	public static ListInteger ints(String sql) {
		return SqlNative.selectInts(sql);
	}

	public static int execSQL(String sql) {
//		sql = Dao.substituirNomesEntidades(sql);
		return execSQLDirect(sql);
	}
	
	public static int execSQLDirect(String sql) {
		sql = preparaSql(sql);
		if (UConfig.get().onLine()) {
			return UConfig.get().execSql(sql);
		} else {
			return UConfig.con().exec(sql);	
		}
	}
	
	public static String preparaSql(String sql) {
		return preparaSql(sql, UConfig.con().getDriver());
	}
	public static String preparaSql(String sql, DriverJDBC driver) {
		
		sql = sql.replace("delete frm ", "delete from ");
		if (sql.toLowerCase().startsWith("update") && !sql.contains("where")) {
			throw UException.runtime("update sem where: " + sql);
		}
		if (sql.toLowerCase().startsWith("delete") && !sql.toLowerCase().contains("where")) {
			throw UException.runtime("delete sem where: " + sql);
		}
		if (sql.endsWith(";")) {
			sql = UString.beforeLast(sql, ";");
		}
		
		sql = NativeSelectMap.sqlComLimit(sql, driver);

		if (sql.contains("@now")) {
			String value = Data.now().format_sql(true);
			if (driver.equals(DriverJDBC.PostgreSQL)) {
				value = "cast(" + value + " as timestamp)";
			}
			sql = sql.replace("@now", value);
		}
		
		
		
		return sql;
	}

//	public static QueryExecutor getExecutor() {
//		return new JpaExecutor();
//	}

	public static String replaceParameters(String sql) {
		return UConfig.jpa().replaceParameters(sql);
	}

	public static Data nowDatabase() {
		return selectData("select now()");
	}
	
}



