package gm.utils.jpa.support;

import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;

public class DriverJDBCPostgree extends DriverJDBCGets {

	public DriverJDBCPostgree(ConexaoJdbc con) {
		super(con);
	}

	@Override
	public String getNomeColunaId(String ts) {
		throw UException.runtime("implemntar");
	}

}
