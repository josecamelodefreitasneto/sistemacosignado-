package gm.utils.javaCreate;

import java.util.ArrayList;
import java.util.List;

import gm.utils.comum.UList;
import lombok.Getter;

@Getter
public class JcTipos {
	private final List<JcTipo> list = new ArrayList<>();
	public JcTipos add(final Class<?> classe) {
		return this.add(new JcTipo(classe));
	}
	public JcTipos add(final String classe) {
		return this.add(new JcTipo(classe));
	}
	public JcTipos add(final JcClasse classe) {
		return this.add(new JcTipo(classe.getNome()));
	}
	public JcTipos add(final JcTipo tipo) {
		if (!this.has(tipo)) {
			this.list.add(tipo);
		}
		return this;
	}
	public JcTipos add(final JcTipos tipos) {
		for (final JcTipo tipo : tipos.list) {
			this.add(tipo);
		}
		return this;
	}
	public boolean has(final JcTipo tipo) {
		return this.has(tipo.getName());
	}
	public boolean has(final String tipo) {
		return UList.exists(this.list, o -> o.getName().contentEquals(tipo));
	}
	public void sort() {
		this.list.sort((a,b) -> a.getName().compareTo(b.getName()) );
	}
	public boolean remove(JcTipo tipo) {
		return this.list.removeIf(o -> o.getName().contentEquals(tipo.getName()));
	}
	public boolean remove(Class<?> classe) {
		return this.list.removeIf(o -> o.getName().contentEquals(classe.getName()));
	}
	public void replace(Class<?> a, Class<?> b) {
		if (remove(a)) {
			add(b);
		}
	}
	
}
