package gm.utils.jpa;

import lombok.Getter;

@Getter
public enum DriverJDBC {
	PostgreSQL("public"), MSSQLServer("dbo");
	
	private String schemaPadrao;

	DriverJDBC(String schemaPadrao) {
		this.schemaPadrao = schemaPadrao;
	}
}
