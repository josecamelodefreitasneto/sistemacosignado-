package gm.utils.jpa.constructor;

import java.util.Calendar;
import java.util.Date;

import gm.utils.classes.ListClass;
import gm.utils.comum.UAssert;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public abstract class CriarClasse {

	public static final String PACOTE = "gm.auto";

	// protected final String PASTA_AUTO =
	// UConfig.get().getPathRaizProjetoAtual() + "java/" +
	// PACOTE.replace(".", "/") + "/";

	private ListString imports = new ListString();

	protected Class<?> classe;

	private final ConstructorBackConfig config;

	public ListClass getEntidades() {
		return config.getEntidades();
	}

	public CriarClasse(ConstructorBackConfig config, Class<?> classe) {
		this.config = config;
		exec(classe);
	}

	protected CriarClasse(ConstructorBackConfig config, boolean exec) {
		this.config = config;
		save();
	}

	protected CriarClasse(ConstructorBackConfig config) {
		this.config = config;
		for (Class<?> classe : config.getEntidades()) {
			// ULog.print(classe);
			exec(classe);
		}
	}

	private void exec(Class<?> classe) {
		this.classe = classe;
		imports.clear();
		addImport(classe);
		try {
			save();
		} catch (Exception e) {
			throw UException.runtime("Erro ao criar a classe " + classe.getSimpleName(), e);
		}
	}

	public void addImport(String classe) {
		if (imports.contains(classe)) {
			return;
		}
		if (classe.startsWith("java.lang.")) {
			return;
		}
		imports.add(classe);
	}

	public void addImport(Class<?> classe) {
		UAssert.notEmpty(classe, "classe == null");
		if (classe.equals(byte[].class)) {
			return;
		}
		String s = classe.getName();
		addImport(s);

	}

	protected void addImportaData() {
		addImport(Data.class);
		addImport(Date.class);
		addImport(Calendar.class);
	}

	ListString cabecalho() {
		ListString list = new ListString();
		list.add("package " + PACOTE + ";");
		return list;
	}

	String nomeThis() {
		return classe.getSimpleName() + sufixo();
	}

	public abstract String sufixo();

	public boolean lombok() {
		return false;
	}

	void save() {
		ListString corpo = corpo();
		ListString list = cabecalho();
		for (String c : imports) {
			list.add("import " + c + ";");
		}

		if (lombok()) {
			list.add("import lombok.Getter;");
			list.add("import lombok.Setter;");
			list.add("@Getter @Setter");
		}

		list.add(corpo);
		list.add("}");
		list.juntarFimComComecos("{", "}", "");
		
		saveJava(list, nomeThis());
	}

	public void saveJava(ListString list, String nome) {
		String s = config.pastaSaveBack() + nome + ".java";
		list.save(s);
	}

	public abstract ListString corpo();

	public String getPrefixo() {
		return config.getPrefixo();
	}

	public boolean isFw() {
		return getPrefixo().equals("Fw");
	}

}
