package gm.utils.jpa.constructor;

import gm.utils.config.UConfigJdbc;

public abstract class ConstructorContextConfig extends ConstructorBackConfig {

	@Override
	public final String pastaSaveFront() {
		
		return null;
	}

	@Override
	public final String getPathRaizProjetoFw() {
		
		return null;
	}

	@Override
	protected final UConfigJdbc loadConfigJdbc() {
		
		return null;
	}

	@Override
	public final int execSql(String sql) {
		
		return 0;
	}
	
	@Override
	public final boolean criarUpdate() {
		return false;
	}

	@Override
	public final boolean criarPojo() {
		return true;
	}

	@Override
	public final boolean usaFramework() {
		return false;
	}
}
