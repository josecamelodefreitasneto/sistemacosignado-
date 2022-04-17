package gm.utils.javaCreate;

import java.util.ArrayList;
import java.util.List;

import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class JcTipo {
	
	private boolean isGenerics;
	private Boolean primitivo;
	private boolean extender;
	private final String name;
	private List<JcTipo> generics;

	public JcTipo(String name) {
		name = UString.trimPlus(name);
		if (name.startsWith("? extends ")) {
			this.extender = true;
			name = UString.afterFirst(name, "? extends ");
		}
		if (name.startsWith("java.lang.") && !name.contentEquals("java.lang.Class")) {
			name = UString.afterLast(name, ".");
			this.primitivo = true;
		}
		this.name = name;
		if (!this.isPrimitivo()) {
			if (!name.contains(".")) {
				isGenerics = name.toUpperCase().contentEquals(name);
				if (!isGenerics) {
					throw new RuntimeException("O nome deve ser completo: " + name);
				}
			}
			if (name.contains("<")) {
				throw new RuntimeException("use addGenerics " + name);
			}
		}
		if (this.extender) {
			this.setExtends();
		}
	}
	
	public JcTipo(final Class<?> classe) {
		this(classe.getName());
	}

	public JcTipo(final Class<?> classe, final Class<?>... generics) {
		this(classe);
		for (final Class<?> item : generics) {
			this.addGenerics(item);
		}
	}

	public boolean isPrimitivo() {
		if (this.primitivo == null) {
			this.primitivo = this.calculaPrimitivo();
		}
		return this.primitivo;
	}
	
	private boolean calculaPrimitivo() {
		if (this.name.equals("?")) return true;
		if (this.isPrimitivoMesmo()) return true;
		if (this.name.equals("String")) return true;
		return false;
	}

	private boolean isPrimitivoMesmo() {
		if (this.name.equals("boolean")) return true;
		if (this.name.equals("int")) return true;
		if (this.name.equals("double")) return true;
		if (this.name.equals("float")) return true;
		if (this.name.equals("long")) return true;
		return false;
	}

	public JcTipo setExtends() {
		if (this.isPrimitivo()) {
			throw new RuntimeException();
		}
		this.extender = true;
		return this;
	}
	public JcTipo addGenericsExtends(final Class<?> classe) {
		final JcTipo tipo = new JcTipo(classe);
		tipo.setExtends();
		return this.addGenerics(tipo);
	}
	public JcTipo addGenerics(final Class<?> classe) {
		return this.addGenerics(new JcTipo(classe));
	}
	public JcTipo addGenerics(final String nome) {
		return this.addGenerics(new JcTipo(nome));
	}
	public JcTipo addGenerics(final JcTipo tipo) {
		if (this.isPrimitivo()) {
			throw new RuntimeException();
		}
		if (tipo.isPrimitivoMesmo() && !tipo.getName().contentEquals("?")) {
			throw new RuntimeException(tipo.getName());
		}
		if (this.generics == null) {
			this.generics = new ArrayList<>();
		}
		this.generics.add(tipo);
		return this;
	}
	
	@Override
	public String toString() {
		String s;
		if (this.isPrimitivo()) {
			s = this.name;
		} else {
			s = UString.afterLast(this.name, ".");
		}
		if (this.extender) {
			s = "? extends " + s;
		}
		if (this.generics != null) {
			String x = "";
			for (final JcTipo jcTipo : this.generics) {
				x += ", " + jcTipo;
			}
			x = x.substring(2);
			s += "<" + x + ">";
		}
		return s;
	}
	
	public static void main(final String[] args) {
		final JcTipo o = new JcTipo(JcTipo.class);
		o.setExtends();
		final JcTipo o2 = new JcTipo(JcTipo.class);
		o2.addGenerics(String.class);
		o2.addGenerics(Double.class);
		o.addGenerics(Integer.class).setExtends();
		o.addGenerics(Integer.class);
		o.addGenerics(o2);
		System.out.println(o);
	}

	public void clearGenerics() {
		this.generics = null;
	}

	public void addTipos(final ListString list) {
		if (this.isPrimitivo()) {
			return;
		}
		list.addIfNotContains(this.name);
		if (this.generics != null) {
			for (final JcTipo o : this.generics) {
				o.addTipos(list);
			}
		}
	}
	
	public ListString getTipos() {
		final ListString list = new ListString();
		this.addTipos(list);
		return list;
	}
	
	public String getSimpleName() {
		return UString.afterLast("." + this.getName(), ".");
	}
	
}
