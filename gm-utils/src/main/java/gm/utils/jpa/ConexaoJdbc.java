package gm.utils.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.jdbc.PgConnection;

import com.microsoft.sqlserver.jdbc.SQLServerConnection;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.USystem;
import gm.utils.comum.UType;
import gm.utils.date.Cronometro;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.jpa.support.DriverJDBCGets;
import gm.utils.jpa.support.DriverJDBCPostgree;
import gm.utils.jpa.support.DriverJDBCSqlServer;
import gm.utils.map.MapSO;
import gm.utils.number.ListInteger;
import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

public class ConexaoJdbc {

	public static Map<String, ConexaoJdbc> conexoes = new HashMap<>();
	public static int limitDefault = 200;
	private static int idArquivo = 0;
	private static String nomeArquivo = ConexaoJdbc.getNomeArquivo();
	private final String banco;
	private DriverJDBCGets driverGets;
	
	public ListString fileLog;
	public boolean keep_alive = false;
	public boolean debugOnExec = false;
	private ListString alteracoes;
	private String user = "postgres";
	private String pass = "postgres";
	private Connection con;
	private final Map<Class<?>,UTable> tables = new HashMap<>();
	
	public boolean conseguiuConectarAPrimeiraVez;
	private final boolean reconectarAutomaticamente = true;
	
	public void criarViewsParaFuncionamento() {
		this.criarViewsParaFuncionamento(USchema.SCHEMA_DEFAULT);
	}
	public void criarViewsParaFuncionamento(final String schema) {
		
		new USchema(this).create(schema);
		
		if (this.driver == DriverJDBC.MSSQLServer) {
			final Integer id = new USchema(this).getId(schema);
			this.tryExec("create view "+schema+".banco_tabelas as select nome = name from sys.tables where schema_id = " + id);
			this.tryExec("create view "+schema+".banco_colunas as select tabela = t.name, coluna = c.name from sys.columns c inner join sys.tables t on c.object_id = t.object_id where t.schema_id = " + id);
			this.tryExec("create view "+schema+".banco_views as select nome = name from sys.views where schema_id = " + id);
		} else if (this.driver == DriverJDBC.PostgreSQL) {
			
			exec("drop view if exists "+schema+".banco_tabelas");
			
			exec(
				"create view "+schema+".banco_tabelas as "
				+ "select tablename as nome "
				+ "from pg_catalog.pg_tables "
				+ "where lower(schemaname) = '"+schema+"'"
			);
			
			exec("drop view if exists "+schema+".banco_colunas");
			
			exec(
				"create view "+schema+".banco_colunas as "
				+ "select "
				+ "a.table_name as tabela, "
				+ "a.column_name as coluna, "
				+ "case "
				+ "	when a.data_type = 'character varying' then 'String('||a.character_maximum_length||')' "
				+ "	when data_type = 'numeric' then 'Numeric('||numeric_precision||','||numeric_scale||')' "
				+ " when data_type = 'timestamp without time zone' then 'DateTime'"
				+ "	else "
				+ "		a.data_type end "
				+ "	as tipo "
				+ "from information_schema.columns as a "
				+ "inner join pg_catalog.pg_tables as b on a.table_name = b.tablename and a.table_schema = b.schemaname "
				+ "where a.table_schema = '" + schema + "'"
			);
			
		} else {
			throw UException.runtime("implementar");
		}
	}
	
	@Getter
	private final DriverJDBC driver;
	
	public void testaConexao() {
		try {
			this.selectInt("select 1 as x");
			ULog.info("ok " + this.banco);
		} catch (final Exception e) {
			ULog.error("erro " + this.banco);
			UException.printTrace(e);
		}
	}

	public static ConexaoJdbc get(final String url, final String user, final String pass){
		
		/*
		 jdbc:postgresql://localhost:5432/cooper-gestao-de-taxas
		*/
		
		if (!url.startsWith("jdbc:")) {
			throw new RuntimeException("???");
		}
		
		String s = UString.afterFirst(url, ":");

		final String nomeDriver = UString.beforeFirst(s, ":");
		
		s = UString.afterFirst(s, ":").substring(2);
		
		final DriverJDBC driver;
		
		if (nomeDriver.contentEquals("postgresql")) {
			driver = DriverJDBC.PostgreSQL;
		} else if (nomeDriver.contentEquals("sqlserver")) {
			driver = DriverJDBC.MSSQLServer;
		} else {
			throw new RuntimeException("???");
		}
		
		return ConexaoJdbc.get(s, driver, user, pass);
		
	}

	public static ConexaoJdbc get(final String banco, final DriverJDBC driver, final String user, final String pass){
		ConexaoJdbc o = ConexaoJdbc.conexoes.get(banco);
		if (o == null) {
			o = ConexaoJdbc.getNew(banco, driver, user, pass);
		}
		return o;
	}
	
	public static ConexaoJdbc getNew(final String banco, final DriverJDBC driver, final String user, final String pass){
		final ConexaoJdbc o = new ConexaoJdbc(banco, driver, user, pass);
		ConexaoJdbc.conexoes.put(banco, o);
		return o;
	}
	
	private static DriverJDBC getDriver(final Connection con) {
		if (con == null) {
			throw UException.runtime("con == null");
		} else if (con instanceof PgConnection) {
			return DriverJDBC.PostgreSQL;
		} else if (con instanceof SQLServerConnection) {
			return DriverJDBC.MSSQLServer;
		} else {
			throw UException.runtime("nao implementado " + con.getClass());
		}
	}
	
	public ConexaoJdbc(final Connection con) {
		if (con == null) {
			throw new RuntimeException("con == null");
		}
		this.con = con;
		this.banco = null;
		this.driver = ConexaoJdbc.getDriver(con);
		this.getDriverGets();
	}
	
	private void getDriverGets() {
		if (DriverJDBC.MSSQLServer.equals(this.driver)) {
			this.driverGets = new DriverJDBCSqlServer(this); 
		} else if (DriverJDBC.PostgreSQL.equals(this.driver)) {
			this.driverGets = new DriverJDBCPostgree(this);
		} else {
			throw UException.runtime("nao implementado");
		}
	}

	private ConexaoJdbc(final String banco, final DriverJDBC driver, final String user, final String pass){
		this.banco = banco;
		this.driver = driver;
		this.user = user;
		this.pass = pass;
		this.getDriverGets();
	}
	
	public String getUrl(){
		return this.banco;
	}

	private Connection connection(){
		if ( this.con == null ) {
			this.createConnection();		
			return this.con;
		}
		
		try {
			if ( this.con.isClosed() ) {
				this.createConnection();		
				return this.con;
			}
		} catch (final Exception e) {}
		
		return this.con;
	}
	
	String last_banco;
	
	private void createConnectionTry() {
		if (this.driver == DriverJDBC.PostgreSQL) {
			this.createConnection("postgresql", org.postgresql.Driver.class.getName());
		} else if (this.driver == DriverJDBC.MSSQLServer) {
			System.setProperty("java.net.preferIPv6Addresses", "true");
			this.createConnection("sqlserver", com.microsoft.sqlserver.jdbc.SQLServerDriver.class.getName());
		}
	}
	
	private void createConnection(final String nomeBanco, final String driver) {
		
		UAssert.notEmpty(this.banco, "banco is null");
		
		try {
			final String s = "jdbc:"+nomeBanco+"://" + this.banco;
			Class.forName(driver);
			ULog.debug(">> Conectando a "+s+" <<");
			this.con = DriverManager.getConnection(s, this.user, this.pass);
			this.conseguiuConectarAPrimeiraVez = true;
		} catch (final Exception e) {
			throw UException.runtime(e);
		}

	}	
	
	private void createConnection() {
		
		if (!this.conseguiuConectarAPrimeiraVez) {
			this.createConnectionTry();
			return;
		}
		
		if (this.reconectarAutomaticamente) {

			Exception ex = null;
			
			for (int i = 0; i < 100; i++) {

				try {
					this.createConnectionTry();
					return;
				} catch (final Exception e) {
					
					if (i == 0) {
						ex = e;
						UException.printTrace(e);
					}
					
					try {
						if (this.con != null) {
							this.con.close();
						}
					} catch (final Exception e2) {}
					
					this.con = null;
					ULog.error(">> A conexao caiu - tentando recuperar em 10 minutos - tentativa " + i + " de 100");
					USystem.sleepMinutos(10);
					
				}
				
			}

			throw UException.runtime(ex);
			
		}

		throw UException.runtime("Conexao perdida!");

	}
	
	UDataSource ds = new UDataSource() {
		@Override
		public Connection getConnection() {
			try {
				return ConexaoJdbc.this.connection();
			} catch (final Exception e) {
				throw UException.runtime("bla");
			}
		}
	};
	
	public int tryExec(final String sql) {
		return this.tryExec(sql, true);
	}
	
	public int tryExec(final String sql, final boolean printStackTrace) {
		try {
			return this.exec(sql);
		} catch (final Exception e) {
			if (printStackTrace) {
				UException.printTrace(e);
			}
			return -1;
		}
	}
	
	private Thread thread;
	private int threadResult;
	
	public void execThread(final String sql) {
		this.threadResult = -1;
		this.thread = new Thread() {
			@Override
			public void run() {
				ConexaoJdbc.this.threadResult = ConexaoJdbc.this.exec(sql);
			}
		};
		this.thread.start();
	}
	
	public boolean isRodando() {
		return this.thread != null && this.thread.isAlive();
	}
	
	public int getThreadResult() {
		while (this.isRodando());
		return this.threadResult;
	}
	
	public int exec(String sql) {
		
		final Cronometro cron = new Cronometro();
		
		try (Statement stmt = this.connection().createStatement()) {
			
			sql = this.preparaSql(sql);
			
			if (!sql.trim().endsWith(";")) {
				sql += ";";
			}
			
			if (ULog.debug && !this.debugOnExec) {
				ULog.debug(sql);
			}
			
			int result = stmt.executeUpdate(sql);
			
			if (sql.contains("alter") || sql.contains("drop") || sql.contains("create")) {
				result = 1;
			}
			
			if (this.debugOnExec && result > 0) {
				ULog.debug = true;
				ULog.debug(sql);
				ULog.debug = false;
				
				if (this.alteracoes == null) {
					this.alteracoes = new ListString();
				}
				
				final ListString rows = ListString.split(sql, "\n");
				this.alteracoes.add(rows);
				this.alteracoes.save(ConexaoJdbc.nomeArquivo);
				
				if (this.alteracoes.size() > 5000) {
					this.alteracoes.clear();
					ConexaoJdbc.idArquivo++;
					ConexaoJdbc.nomeArquivo = ConexaoJdbc.getNomeArquivo();
				}
				
			}
			
			Cronometro.print(cron, sql);
			
			if (this.fileLog != null) {
				this.fileLog.add(sql + " = " + result);
			}
			
			return result;
		} catch (final Exception e) {
			System.out.println("erro:>>" + sql);
			System.out.println(">>>>>>>" + this.banco);
			throw UException.runtime(e);
		}
	}
	
	public void addExecute(String sql, int result) {

		if (sql.contains("alter") || sql.contains("drop") || sql.contains("create") || sql.contains("select setval(")) {
			result = 1;
		}
		
		if (this.debugOnExec && result > 0) {
			ULog.debug = true;
			ULog.debug(sql);
			ULog.debug = false;
			
			if (this.alteracoes == null) {
				this.alteracoes = new ListString();
			}
			
			sql = sql.trim();
			if (!sql.endsWith(";")) {
				sql += ";";
			}
			
			final ListString rows = ListString.split(sql, "\n");
			this.alteracoes.add(rows);
			this.alteracoes.save(ConexaoJdbc.nomeArquivo);
			
			if (this.alteracoes.size() > 1000) {
				this.alteracoes.clear();
				ConexaoJdbc.idArquivo++;
				ConexaoJdbc.nomeArquivo = ConexaoJdbc.getNomeArquivo();
			}
			
		}
	}

	private static String getNomeArquivo() {
		return "/tmp/script/script"+ UNumber.format00(ConexaoJdbc.idArquivo) +".txt";
	}

	public Integer selectInt(final String sql, final Integer def){
		return UInteger.toInt( this.selectInt(sql), def );
	}
	public Integer selectInt(final String sql){
		return UInteger.toInt( this.selectField1(sql) );
	}
	public Data selectData(final String sql) {
		return Data.to( this.selectField1(sql) );
	}
	public String selectString(final String sql){
		return UString.toString( this.selectField1(sql) );
	}
	public Object selectField1(final String sql){
		final Map<String, Object> map = this.selectMapUnique(sql);
		if (map == null) {
			return null;
		}
		return map.entrySet().iterator().next().getValue();
	}
	public MapSO selectMapUnique(final String sql) {
		final List<MapSO> select = this.selectMap(sql);
		if (select.isEmpty()) {
			return null;
		}
		if ( select.size() > 1 ) {
			throw UException.runtime("A consulta retornou + de 1 resultado - " + sql);
		}
		return select.get(0);
	}
	public ListString selectStrings(final String sql) {
		final ListString list = new ListString();
		for (final Object[] os : this.rs(sql).dados) {
			final Object o = os[0];
			if (UObject.isEmpty(o)) {
				continue;
			}
			list.add(UString.toString(o));
		}
		return list;
	}
	public ListInteger selectInts(final String sql) {
		final ListInteger list = new ListInteger();
		for (final Object[] os : this.rs(sql).dados) {
			final Object o = os[0];
			final Integer i = UInteger.toInt(o);
			list.add(i);
		}
		return list;
	}
	public UResultSet rs(final String sql) {
		return this.rs(sql, true);
	}
	public UResultSet rs(final String sql, final boolean prepare) {
		try {
			final Statement statement = this.connection().createStatement();
			return this.rs(sql, statement, prepare);
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}
	
	public UResultSet rs(String sql, final Statement stmt, final boolean prepare) {
		
		if (prepare) {
			sql = this.preparaSql(sql);
		}
		
		final UResultSet result = new UResultSet();

		try (ResultSet rs = stmt.executeQuery(sql)) {
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columns = rsmd.getColumnCount();
			
			int pular = -1;

			for (int i = 0; i < columns; i++) {
				final String name = rsmd.getColumnName(i+1);
				if (name.equalsIgnoreCase("txcontrato")) {
					pular = i;
				} else {
					result.titulos.add(name);
				}
			}
			
			while (rs.next()) {
				final List<Object> os = new ArrayList<>();
				for (int i = 0; i < columns; i++) {
					
					if (pular == i) {
						continue;
					}
					
					final Object value = rs.getObject(i+1);
					os.add(value);
				}
				result.dados.add(os.toArray());
			}
			
		} catch (final SQLException e) {
			ULog.debug(sql);
			UException.printTrace(e);
			throw UException.runtime(e);
		}
		
		return result;
		
	}
	public List<Object[]> selectArray(final String sql) {
		return this.rs(sql).dados;
	}
	private String preparaSql(final String sql) {
		return SqlNative.preparaSql(sql, this.getDriver());
	}

	public <T> List<T> get(final Class<T> classe, final String sql) {

		final List<T> list = new ArrayList<>();
		
		try (Statement stmt = this.connection().createStatement();
				
			ResultSet rs = stmt.executeQuery(this.preparaSql(sql))) {
			
			final Atributos atributos = ListAtributos.persist(classe, false);
			final Atributo fieldId = atributos.getId();
			
//			if (fieldId == null) {
//				throw Utils.exception("fieldId == null");
//			}
			
			while (rs.next()) {
				final T o = UClass.newInstance(classe);
				
				if (fieldId != null) {
					final Object x = rs.getObject( fieldId.getColumnName() );
					fieldId.set(o, x);
				}
				
				for (final Atributo a : atributos) {
					
					Object value = null;
					
					try {
						value = rs.getObject( a.getColumnName() );	
					} catch (final Exception e) {
						continue;
//						throw Utils.exception("Campo nao encontrado na query: " + a.getColumnName());
					}
					
					value = UType.parse(value, a.getType());

					try {
						a.set(o, value);
					} catch (final Exception e) {
						final Object v = UClass.newInstance( a.getType() );
						final Atributos as = ListAtributos.get( a.getType() );
						final Atributo id = as.getId();
						id.set(v, value);
						a.set(o, v);
					}
				}
				list.add(o);
			}
		} catch (final SQLException e) {
			throw UException.runtime(e);
		}
		
		return list;

	}

	public void close() {
		try {
			this.con.close();
			ULog.debug("Close " + this.banco);
		} catch (final Exception e2) {}
		this.con = null;
	}

	public void dropTriggers() {
		final List<MapSO> list = this.selectMap( "select distinct trigger_name tg, event_object_schema||'.'||event_object_table tb from information_schema.triggers" );
		for (final MapSO map : list) {
			final String tg = map.getObrig("tg");
			final String tb = map.getObrig("tb");
			final String s = "drop trigger "+tg+" on "+tb;
			this.exec(s);
		}
	}
	
	public UTable table(final String name){
		return this.table("public", name);
	}
	public UTable table(final String schema, final String name){
		return new UTable(this, schema, name);
	}
	
	public UTable table(final Class<?> classe){
		UTable o = this.tables.get(classe);
		if (o == null) {
			o = new UTable(this, classe);
			this.tables.put(classe, o);
		}
		return o;
	}
	
	public ConexaoJdbc copy() {
		return new ConexaoJdbc(this.banco, this.driver, this.user, this.pass);
	}
	public ConexaoJdbc us(final String user, final String pass) {
		this.user = user;
		this.pass = pass;
		return this;
	}
	public String tamanho() {
		return this.selectString("SELECT pg_size_pretty(pg_database_size(current_database()))");
	}
	public String schemaConstraint(final String constraint, final String table){
		
		if (table.contains(".")) {
			return UString.beforeFirst(table, ".");
		}
		
		return "public";
		/*
		String sql =  
			" SELECT pg_namespace.nspname as s"
			+ " FROM pg_namespace"
			+ " JOIN pg_class ON pg_namespace.oid=pg_class.relnamespace"
			+ " JOIN pg_constraint ON pg_class.oid=pg_constraint.conrelid"
			+ " WHERE conname = ':constraint'"
			+ "   and pg_class.relname = ':table'"
		;
		
		sql = sql.replace(":constraint", constraint).replace(":table", table);
		return selectString(sql);
		*/
	}
	
	private static final String sqlColumnReference = ""
			+ " SELECT pg_get_constraintdef(pg_constraint.oid)"
			+ " FROM pg_namespace"
			+ " JOIN pg_class ON pg_namespace.oid=pg_class.relnamespace"
			+ " JOIN pg_constraint ON pg_class.oid=pg_constraint.conrelid"
			+ " WHERE pg_class.relkind='r'"
			+ " and relname = ':tableOrigem'"
			+ " and ( pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES :tableReferenciada(%'"
		;
	
	public ListString columnReference(String tableOrigem, String tableReferenciada, final String constraint){
		
		tableReferenciada = tableReferenciada.toLowerCase();
		tableOrigem = tableOrigem.toLowerCase();
		
		String s = ConexaoJdbc.sqlColumnReference;
		s = s.replace(":tableOrigem", tableOrigem);
		s = s.replace(":tableReferenciada", tableReferenciada);
		if (tableReferenciada.contains(".")) {
			tableReferenciada = UString.afterFirst(tableReferenciada, ".");
			s += "  or pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES " + tableReferenciada + "(%' ";
		}
		s += "  or pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES %." + tableReferenciada + "(%' ";
		s += ")";
		s = s.replace(":tableReferenciada", tableReferenciada);
		
		if (!UString.isEmpty(constraint)) {
			s += " and conname = '" + constraint + "'";
		}
		
		final ListString list = new ListString();
		
		final ListString selectStrings = this.selectStrings(s);
		for (final String string : selectStrings) {
			ULog.debug(string);
			s = UString.afterFirst(string, "FOREIGN KEY (");
			s = UString.beforeFirst(s, ")");
			list.add(s);
		}
		return list;
	}
	public Connection getCon() {
		return this.connection();
	}
	public void startTransaction() {
		try {
			this.con.setAutoCommit(false);
		} catch (final SQLException e) {
			throw UException.runtime(e);
		}
	}
	public void commit() {
		try {
			this.con.commit();
			this.con.setAutoCommit(true);
		} catch (final SQLException e) {
			throw UException.runtime(e);
		}
	}
	public void rollback() {
		try {
			this.con.rollback();
		} catch (final SQLException e) {
			throw UException.runtime(e);
		}
	}
	public List<MapSO> selectMap(final String sql) {
		return new NativeSelectMap(this.connection(), this.driver, sql).map();
	}
	public void dropColumn(final Class<?> classe, final String nome){
		if (this.existsColumn(classe, nome)){
			final String s = "alter table " + UTableSchema.get(classe) + " drop column " + nome;
			this.exec(s);
		}
	}
	public boolean exists(final Class<?> classe, final Integer id){
		final String sql = "select count(*) from " + UTableSchema.get(classe) + " where id = " + id;
		final int i = this.selectInt(sql);
		return i > 0;
	}
	public boolean existsColumn(final Class<?> classe, final String nome) {
		final String schema = USchema.SCHEMA_DEFAULT;
		String s = "select count(*)";
		s += " from "+schema+".banco_colunas ";
		s += " where lower(tabela) = '"+ classe.getSimpleName().toLowerCase() +"' ";
		s += "   and lower(coluna) = '"+ nome.toLowerCase() +"' ";
		final int i = this.selectInt(s);
		return i > 0;
	}
	public boolean existsView(final Class<?> classe, final String nome) {
		final String schema = USchema.SCHEMA_DEFAULT;
		String s = "select count(*)";
		s += " from "+schema+".banco_views ";
		s += " where lower(nome) = '"+ classe.getSimpleName().toLowerCase() +"' ";
		final int i = this.selectInt(s);
		return i > 0;
	}
	
	
	
//	private QueryExecutor executor;
//
//	public QueryExecutor getExecutor() {
//		if (executor == null) {
//			executor = new JdbcQueryExecutor(this);
//		}
//		return executor;
//	}

	public <T extends IdObject> T save(T o) {
		final Class<?> classe = UClass.getClass(o);
		o = this.table(classe).save(o);
		return o;
	}

	public String getNomeColunaId(final String ts) {
		final String s = this.driverGets.getNomeColunaId(ts);
		if (s == null) {
			throw UException.runtime("Nao encontrada a columnId " + ts);
		}
		return s;		
	}

	@Setter
	private String owner;
	
	public String getOwner() {
		if (this.owner == null) {
			this.setOwner(this.user);
		}
		return this.owner;
	}

}
