package gm.utils.javaCreate;

import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JcAtributo extends JcParametro {
	
	private String inicializacao;
	private String acesso = "private";
	private boolean statico = false;
	private boolean getter = false;
	private boolean setter = false;
	private boolean getterLombok = false;
	private boolean setterLombok = false;
	private boolean transient_ = false;
	private boolean builder = false;
	private String builderNome;
	
	public JcAtributo(final String nome, final Class<?> tipo) {
		super(nome, tipo.getName());
	}
	public JcAtributo(final String nome, final String tipo) {
		super(nome, tipo);
	}
	public JcAtributo(final String nome, final JcClasse tipo) {
		super(nome, tipo);
	}
	public JcAtributo(final String nome, final JcTipo tipo) {
		super(nome, tipo);
	}
	
	@Override
	public String toString() {
		
		String s = getAnotacoes().toString();
		
		if (!UString.isEmpty(s)) {
			s += " ";
		}
		return s + getAssinatura();
	}
	
	private String getAssinatura() {
		String s = acesso + " "
		+ (statico?"static ":"")
		+ (final_?"final ":"")
		+ (transient_?"transient ":"")
		+ getTipo()
		+ " " + getNome();
		if (inicializacao != null) {
			s += " = " + inicializacao;
		}
		s += ";";
		return s.trim();
	}
	
	public ListString toListString() {
		final ListString list = new ListString();
		list.add(getAnotacoes().toListString());
		
		list.sort((a, b) -> {
			if (a.length() < b.length()) return -1;
			if (b.length() < a.length()) return 1;
			return a.compareTo(b);
		});
		
		list.add(getAssinatura());
		list.removeEmptys();
		return list;
		
	}
	
	public JcAtributo inicializacao(final String s) {
		setInicializacao(s);
		return this;
	}
	
	public JcAtributo public_() {
		setAcesso("public");
		return this;
	}
	public JcAtributo protected_() {
		setAcesso("protected");
		return this;
	}
	public JcAtributo default_() {
		setAcesso("");
		return this;
	}
	public JcAtributo static_() {
		statico = true;
		return this;
	}
	public JcAtributo transient_() {
		transient_ = true;
		return this;
	}
	public JcAtributo final_() {
		final_ = true;
		return this;
	}
	@Override
	public final JcAtributo generics(final Class<?> tipo) {
		this.setGenerics(tipo);
		return this;
	}
	@Override
	public final JcAtributo generics(final JcClasse tipo) {
		this.setGenerics(tipo);
		return this;
	}
	@Override
	public JcAtributo generics(final String tipo) {
		return (JcAtributo) super.generics(tipo);
	}
	public JcAtributo getterLombok() {
		this.addAnotacao(Getter.class);
		getterLombok = true;
		return this;
	}
	public JcAtributo setterLombok() {
		this.addAnotacao(Setter.class);
		setterLombok = true;
		return this;
	}
	public JcAtributo getter() {
		getter = true;
		return this;
	}
	public JcAtributo setter() {
		setter = true;
		return this;
	}
	public JcAtributo builder() {
		builder  = true;
		return this;
	}
	public JcAtributo builder(String nome) {
		builderNome = nome;		
		return this;
	}
	
}
