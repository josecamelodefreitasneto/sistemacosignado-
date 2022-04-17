package gm.utils.config;

import gm.utils.exception.UException;
import gm.utils.string.ListString;

public class UConfigDefault extends UConfig {

	@Override
	public boolean emDesenvolvimento() {
		return true;
	}

	@Override
	public boolean onLine() {
		return false;
	}

	@Override
	protected UConfigJpa loadConfigJpa() {
		return null;
	}

	@Override
	protected UConfigJdbc loadConfigJdbc() {
		return null;
	}
	
	@Override
	protected ListString loadPackagesApp() {
		throw exception();
	}

	@Override
	public void validaStop() {
		
	}

	@Override
	public String loadNomeProjetoGlobal() {
		throw exception();
	}

	private RuntimeException exception() {
		return UException.runtime("implementar");
	}

	@Override
	protected String loadPathRaizProjetoAtual() {
		
		return null;
	}

	@Override
	protected ListString loadPathRaizProjetosVinculados() {
		
		return null;
	}

	@Override
	public String getOwnerBanco() {
		
		return null;
	}

	@Override
	public int execSql(String sql) {
		throw UException.runtime("offline");
	}

}
