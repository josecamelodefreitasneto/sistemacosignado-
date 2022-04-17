package gm.utils.jpa.support;

import gm.utils.jpa.ConexaoJdbc;
import lombok.Getter;

@Getter
public abstract class DriverJDBCGets {
	private ConexaoJdbc con;
	public DriverJDBCGets(ConexaoJdbc con) {
		this.con = con;
	}
	public abstract String getNomeColunaId(String ts);
}
