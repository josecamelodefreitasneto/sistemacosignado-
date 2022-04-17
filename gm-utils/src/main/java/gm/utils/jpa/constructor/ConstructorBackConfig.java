package gm.utils.jpa.constructor;

import java.lang.annotation.Annotation;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.config.UConfig;
import gm.utils.config.UConfigJpa;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class ConstructorBackConfig extends UConfig {
	
	protected ConstructorBackConfig() {
		if (!pastaSaveBack().endsWith("/gm/auto/")) {
			throw UException.runtime("pastaSaveBack deve terminar com /gm/auto/");
		}
		if (!pastaModel().endsWith("/")) {
			throw UException.runtime("pastaModel deve terminar com /");
		}
		if (pastaSaveFront() != null && !pastaSaveFront().endsWith("/auto/")) {
			throw UException.runtime("pastaSaveFront deve terminar com /auto/");
		}
	}

	public abstract String pastaSaveBack();
	public abstract String pastaSaveFront();
	public abstract String getPathRaizProjetoFw();
	public abstract boolean criarPojo();
	public abstract boolean criarUpdate();
	public abstract boolean usaFramework();
	public abstract Class<? extends Annotation> statelessClass();
	public abstract Class<?> injectClass();
	public abstract Class<?> classeSelect();
	
	protected abstract String pastaModel();
	
	public String getPrefixo() {
		return "";
	}
	private ListClass entidades;
	public ListClass getEntidades() {
		if (entidades == null) {
			entidades = UClass.fromPath(pastaModel());	
		}
		return entidades;
	}

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
	protected String loadPathRaizProjetoAtual() {
		return System.getProperty("user.dir") + "/";
	}

	@Override
	protected String loadNomeProjetoGlobal() {
		return UString.afterLast(System.getProperty("user.dir"), "/");
	}

	@Override
	protected ListString loadPathRaizProjetosVinculados() {
		ListString list = new ListString();
		list.add(pastaModel());
		return list;
	}

	@Override
	protected ListString loadPackagesApp() {
		
		return null;
	}

	@Override
	public void validaStop() {
		
		
	}
	

}
