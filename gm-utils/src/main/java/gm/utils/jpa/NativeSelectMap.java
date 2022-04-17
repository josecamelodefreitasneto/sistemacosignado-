package gm.utils.jpa;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.config.UConfig;
import gm.utils.date.Cronometro;
import gm.utils.date.Data;
import gm.utils.date.UDate;
import gm.utils.exception.UException;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric2;
import gm.utils.number.UBigDecimal;
import gm.utils.number.UNumber;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class NativeSelectMap {
	private Connection connection;
	private String sql;
	private Map<String, String> colNamesMap = new HashMap<>();
	private List<MapSO> colDefinitions = new ArrayList<>();
	private Integer empresa;
	private DriverJDBC driver;
	
	public static Connection discoverConnection() {
		return UConfig.jpa().getConnection();
	}
	public NativeSelectMap(String sql) {
		this( sql, null );
	}
	public NativeSelectMap(String sql, ListString colNames) {
//		TODO verificar
		this( discoverConnection(), DriverJDBC.MSSQLServer, sql, colNames );
	}
	public NativeSelectMap(Connection connection, DriverJDBC driver, String sql) {
		this(connection, driver, sql, null);
	}
	public NativeSelectMap(Connection connection, DriverJDBC driver, String sql, ListString colNames) {
		this.connection = connection;
		this.driver = driver;
		this.sql = sql;
		if (colNames == null) {
			colNames = descobrirColNames(sql);
		}
		for (String value : colNames) {
			String key = value.toLowerCase();
			colNamesMap.put(key, value);
		}
	}
	public NativeSelectMap(MontarQueryNativa qn) {
		this(qn.getResult().toString(" "));
	}
	
	public static String sqlComLimit(String sql) {
		return sqlComLimit(sql, UConfig.con().getDriver());
	}
	
	// regra de registros para evitar desperdicio de memoria
	public static String sqlComLimit(String sql, DriverJDBC driver) {
		
		if (driver.equals(DriverJDBC.MSSQLServer)) {
			sql = UString.replacePalavra(sql, "false", "0");
			sql = UString.replacePalavra(sql, "true", "1");
			sql = sql.replace("||", "+");
		}
		
		if (!sql.startsWith("select")) {
			return sql;
		}
		
		if (driver.equals(DriverJDBC.PostgreSQL)) {
			if (!sql.contains("limit ")) sql += " limit 401";
		} else if (driver.equals(DriverJDBC.MSSQLServer)) {
			
			if (sql.contains("limit ")) {
				sql = sql.replace(" ", " $S$ ");
				sql = sql.replace("\n", " $N$ ");
				sql = sql.replace("\t", " $T$ ");
				ListString list = ListString.separaPalavras(sql);
				list.trimPlus();
				int index = list.indexOf("limit");
				list.remove(index);
				String s = list.get(index);
				while (s.equals("$N$") || s.equals("$T$") || s.equals("$S$")) {
					list.remove(index);
					s = list.get(index);
				}
				String valor = s;
				list.remove(index);
				index--;
				while (!list.get(index).equalsIgnoreCase("select")) {
					index--;
				}
				index++;
				if (list.get(index).equals("distinct")) {
					index++;	
				}
				list.add(index, valor);
				list.add(index, "$S$");
				list.add(index, "top");
				list.add(index, "$S$");
				sql = list.toString("");
				sql = sql.replace("$S$", " ");
				sql = sql.replace("$N$", "\n");
				sql = sql.replace("$T$", "\t");
				
			} else if (!sql.contains(" top ")) {
				sql = " " + sql;
				sql = sql.replace(" select ", "select top 401 ");
				sql = sql.replace("select top 401 distinct", "select distinct top 401");
			}
		}
		return sql;
	}
	
	private static final ListString TIPOS_STRING = ListString.array("nvarchar","text","varchar","unknown","name","char","uniqueidentifier");
	private static final ListString TIPOS_INT = ListString.array("serial","int","int2","int4","int8","bigint");
	
	public List<MapSO> map() {
	
		if (driver.equals(DriverJDBC.MSSQLServer)) {
			sql = sql.replace("= false", "= 0");
			sql = sql.replace("= true", "= 1");
			sql = sql.replace("ilike", "like");
		}
		
		sql = sqlComLimit(sql, driver);
		
		List<MapSO> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Cronometro cronometro = new Cronometro();
			
			stmt = connection.createStatement();
//			sql = UTableSchema.tratarSql(sql);
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			boolean first = true;
			while (rs.next()) {
				MapSO map = new MapSO();
				list.add(map);
				for (int i = 0; i < columns; i++) {
					String key = rsmd.getColumnName(i+1);
					key = key.toLowerCase();
					String nomeElegante = colNamesMap.get(key);
					if (nomeElegante != null) {
						key = nomeElegante;
					}
					Object value = rs.getObject(i+1);
					if (UObject.isEmpty(value)) {
						value = null;
					}
					map.put(key, value);
					
					if (first) {
						MapSO mapSO = new MapSO();
						mapSO.add("ordem", i+1);
						mapSO.add("nome", key);
						
						String s = rsmd.getColumnTypeName(i+1);
						Class<?> tipo;
						if (TIPOS_INT.contains(s)) {
							tipo = Integer.class;
						} else if (TIPOS_STRING.contains(s)) {
							tipo = String.class;
						} else if (s.equals("bool") || s.equals("bit")) {
							tipo = Boolean.class;
						} else if (s.equals("numeric") || s.equals("money") || s.startsWith("float")) {
							tipo = Numeric2.class;
						} else if (s.equals("timestamp") || s.equals("date") || s.equals("datetime") || s.equals("smalldatetime")) {
							tipo = Data.class;
						} else {
							throw UException.runtime("Nao tratado: " + key + ":" + s + " >> " + value);
						}
						mapSO.add("tipo", tipo.getSimpleName());
						colDefinitions.add(mapSO);
					}
				}
				first = false;
			}
			if (cronometro.tempo() > 10000) {
				UException.runtime("tempo > 10000: " + sql);
			}
			if (list.size() == 401) {
				//para demostrar que a consulta deveria ter retornado + e o limite foi imposto
				UException.runtime("A consulta retornou 401 resultados");
			}
			
		} catch (SQLException e) {
			ULog.error(sql);
			UException.printTrace(e);
			throw UException.runtime(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
		}
		return list;
	}
	public List<MapSO> mapFormatado() {
		return mapFormatado(true, true);
	}
	public List<MapSO> mapFormatado(boolean formatValor, boolean formatDate) {
		List<MapSO> list = map();
		if (list.isEmpty()) {
			return list;
		}
		Set<String> keys = list.get(0).keySet();
		if (formatDate) {
			for (MapSO o : list) {
				for (String key : keys) {
					Object value = o.get(key);
					if (!UObject.isEmpty(value) && UDate.isData(value)) {
						o.put(key, Data.to(value).format_dd_mm_yyyy());
					}
				}
			}
		}
		if (formatValor) {
			for (MapSO o : list) {
				for (String key : keys) {
					Object value = o.get(key);
					if (!(UObject.isEmpty(value) || value instanceof Integer || !UNumber.isNumber(value)) ) {
						BigDecimal b = UBigDecimal.toBigDecimal(value, 2);
						o.put(key, new Numeric2(b).toString());
					}
				}
			}
		}
		return list;
	}
	public MapSO unique() {
		return UList.getUnique(map());
	}
	public List<Object> getValues() {
		List<MapSO> map = map();
		List<Object> list = new ArrayList<>();
		for (MapSO o : map) {
			Set<String> keySet = o.keySet();
			if (keySet.size() > 1) {
				throw UException.runtime("A consulta retornou + de 1 coluna");
			}
			String key = keySet.iterator().next();
			list.add(o.get(key));
		}
		return list;
	}
	
	private static ListString descobrirColNames(String sql) {
		sql = sql.replace("\n", " ");
		sql = " " + sql;
		sql = sql.replace(" FROM ", " from ");
		sql = sql.replace(" SELECT ", " select ");
		if (sql.contains(" from ")) {
			sql = UString.beforeFirst(sql, " from ");
		}
		sql = UString.afterFirst(sql, "select ");
		ListString list = ListString.split(sql, ",");
		list.trimPlus();
		list.addLeft(" ");
		ListString result = new ListString();
		for (String s : list) {
			s = UString.afterLast(s, " ");
			if (s.contains(".")) {
				s = UString.afterLast(s, ".");	
			}
			result.add(s);
		}
		return result;
	}
}
