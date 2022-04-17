package gm.utils.jpa;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public abstract class UDataSource implements javax.sql.DataSource{

	@Override
	public final PrintWriter getLogWriter() throws SQLException {
		
		return null;
	}

	@Override
	public final void setLogWriter(PrintWriter out) throws SQLException {
		
		
	}

	@Override
	public final void setLoginTimeout(int seconds) throws SQLException {
		
		
	}

	@Override
	public final int getLoginTimeout() throws SQLException {
		
		return 0;
	}

	@Override
	public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
		
		return null;
	}

	@Override
	public final <T> T unwrap(Class<T> iface) throws SQLException {
		
		return null;
	}

	@Override
	public final boolean isWrapperFor(Class<?> iface) throws SQLException {
		
		return false;
	}

	@Override
	public abstract Connection getConnection();

	@Override
	public final Connection getConnection(String username, String password) throws SQLException {
		
		return null;
	}

}
