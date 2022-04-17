package gm.utils.config;

import gm.utils.comum.UAssert;
import gm.utils.comum.ULog;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public abstract class UConfig {

	private static UConfig instance;
	private final String pathRaizProjetoAtual;
	private final ListString pathRaizProjetosVinculados;
	private final ListString pathRaizProjetos;
	private final String nomeProjetoGlobal;
	private final ListString packagesApp = ListString.array("gm");
	private final UConfigJpa jpa;
	private final UConfigJdbc jdbc;
	
	public UConfig() {
		
		if (instance != null) {
			if (instance.getClass().equals(this.getClass())) {
				ULog.warn("Jah foi criada uma instancia de UConfig");
			}
		} else {
			instance = this;
		}
		String s = loadPathRaizProjetoAtual();
		
		if (s == null) {
			this.pathRaizProjetoAtual = null;
			this.pathRaizProjetosVinculados = null;
			this.pathRaizProjetos = null;
			this.nomeProjetoGlobal = null;
		} else {
			
			if (!s.endsWith("/")) {
				s += "/";
			}
			this.pathRaizProjetoAtual = s +  "src/main/";
			
			this.pathRaizProjetosVinculados = new ListString();
			ListString list = loadPathRaizProjetosVinculados();
			if (list != null) {
				for (String ss : list) {
					if (!ss.startsWith("/")) {
						ss = "/" + ss;
					}
					this.pathRaizProjetosVinculados.addIfNotContains(ss);
				}
			}
			
			this.packagesApp.addAll(loadPackagesApp());
			this.packagesApp.setBloqueada(true);
			
			this.nomeProjetoGlobal = loadNomeProjetoGlobal();
			UAssert.notEmpty(this.nomeProjetoGlobal, "nomeProjetoGlobal == null - loadNomeProjetoGlobal retornou vazio");
			
			this.pathRaizProjetos = this.pathRaizProjetosVinculados.copy(); 
			this.pathRaizProjetos.addIfNotContains(pathRaizProjetoAtual);
			this.pathRaizProjetos.setBloqueada(true);
			
		}

		this.jpa = loadConfigJpa();
		this.jdbc = loadConfigJdbc();
		
	}
	
	public static UConfig get() {
//		if (instance == null) {
//			throw UException.runtime("UConfig nao configurado");
//		}
		return instance;
	}

	public abstract boolean emDesenvolvimento(); 
	public abstract boolean onLine();

	public abstract String getOwnerBanco();
	
	protected abstract UConfigJpa loadConfigJpa();
	protected abstract UConfigJdbc loadConfigJdbc();
	protected abstract String loadPathRaizProjetoAtual();
	protected abstract String loadNomeProjetoGlobal();
	protected abstract ListString loadPathRaizProjetosVinculados();
	protected abstract ListString loadPackagesApp();
	public void onStartApplication() {};
	
	public abstract void validaStop();

	public static UConfigJpa jpa() {
		return get().getJpa();
	}
	public static ConexaoJdbc con() {
		
		UConfig config = get();
		
		if (config.onLine()) {
			return new ConexaoJdbc(config.getJpa().getConnection());
		} else {
			UConfigJdbc jdbc = config.getJdbc();
			if (jdbc == null) {
				return null;
			} else {
				return config.getJdbc().getCon();	
			}
		}
		
	}
	public static void checaStop() {
		if (get() != null) {
			get().validaStop();
		}
	}

	public abstract int execSql(String sql);

//	public static ListString returnPathRaizProjetos() {
//		UConfig config = UConfig.get();
//		if (config == null) {
//			ListString list = new ListString();
//			return list;
//		} else {
//			return config.getPathRaizProjetos();
//		}
//	}
//	public static void main(String[] args) {
//		 String s = System.getProperty("user.dir");
//		 System.out.println(s);
//	}

}
