package gm.utils.javaCreate;

import java.lang.annotation.Annotation;

import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public class JcAnotacao {
	private final JcTipos tipos = new JcTipos();
	private JcTipo tipo;
	private MapSO parametros = new MapSO();
	
	public JcAnotacao(String tipo) {
		setTipo(new JcTipo(tipo));
	}

	public JcAnotacao(String tipo, Object value) {
		this(tipo);
		setValue(value);
	}
	
	public JcAnotacao(JcTipo tipo) {
		setTipo(tipo);
	}
	public JcAnotacao(Class<? extends Annotation> classe) {
		this(new JcTipo(classe));
	}
	public JcAnotacao(Class<? extends Annotation> classe, Object value) {
		this(classe);
		setValue(value);
	}
	
	public JcAnotacao setValue(Object value) {
		if (value == null) {
			throw new RuntimeException("?");
		}
		addParametro("value", value);
		return this;
	}
	
	public void setTipo(final Class<? extends Annotation> classe) {
		this.setTipo(new JcTipo(classe));
	}
	public void setTipo(final JcTipo tipo) {
		if (this.tipo != null) {
			this.tipos.remove(this.tipo);
		}
		this.tipo = tipo;
		this.tipos.add(tipo);
	}
	@Override
	public String toString() {
		String s = "@" + tipo.toString();
		
		if (!parametros.isEmpty()) {
			ListString keys = parametros.getKeys();
			if (parametros.size() == 1 && keys.get(0).contentEquals("value")) {
				Object value = parametros.get("value");
				s += "("+value+")";
			} else {
				
				String x = "";
				
				for (String key : keys) {
					x += ", " + key + " = ";
					Object value = parametros.get(key);
					x += value;
				}
				
				x = x.substring(2);
				s += "("+x+")";
			}
		}
		
		return s;
	}
	
	public JcAnotacao addParametro(final String key, final Object value) {
		parametros.add(key, value);
		return this;
	}
	public JcAnotacao addImport(final Class<?> classe) {
		getTipos().add(classe);
		return this;
	}
}
