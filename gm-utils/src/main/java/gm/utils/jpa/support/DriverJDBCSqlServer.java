package gm.utils.jpa.support;

import gm.utils.jpa.ConexaoJdbc;
import gm.utils.string.UString;

public class DriverJDBCSqlServer extends DriverJDBCGets {

	public DriverJDBCSqlServer(ConexaoJdbc con) {
		super(con);
	}

	private static final String SQL_GET_COLUMN_NAME = ""
		+ "  SELECT COLUMN_NAME"
		+ "  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE"
		+ "  WHERE OBJECTPROPERTY(OBJECT_ID(CONSTRAINT_SCHEMA + '.' + QUOTENAME(CONSTRAINT_NAME)), 'IsPrimaryKey') = 1"
		+ "  AND TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s'"
	;

	@Override
	public String getNomeColunaId(String ts) {
		String schema = UString.beforeFirst(ts, ".");
		String table = UString.afterFirst(ts, ".");
		String sql = String.format(SQL_GET_COLUMN_NAME, table, schema);
		return getCon().selectString(sql);
	}
	
}
