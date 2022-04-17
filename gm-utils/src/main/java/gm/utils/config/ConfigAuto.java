package gm.utils.config;

import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ConfigAuto extends UConfig {

	@Override
	public final boolean emDesenvolvimento() {
		
		return false;
	}

	@Override
	public final boolean onLine() {
		
		return false;
	}

	@Override
	public final String getOwnerBanco() {
		
		return null;
	}

	@Override
	protected final UConfigJpa loadConfigJpa() {
		
		return null;
	}

	@Override
	protected final UConfigJdbc loadConfigJdbc() {
		
		return null;
	}

	@Override
	protected final String loadPathRaizProjetoAtual() {
		return System.getProperty("user.dir")+"/";
	}

	@Override
	protected final String loadNomeProjetoGlobal() {
		return UString.afterLast(System.getProperty("user.dir"), "/");
	}

	@Override
	protected final ListString loadPathRaizProjetosVinculados() {
		ListString list = new ListString();
		return list;
	}

	@Override
	protected final ListString loadPackagesApp() {
		
		return null;
	}

	@Override
	public final void validaStop() {
		

	}

	@Override
	public final int execSql(String sql) {
		
		return 0;
	}

	public static ConfigAuto config() {
		return new ConfigAuto();
	}
	
	public static void main(String[] args) {
		config();
	}

}
