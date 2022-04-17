package gm.utils.javaCreate.annotations;

import java.lang.annotation.Annotation;

import javax.persistence.Column;

import gm.utils.javaCreate.JcAnotacao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JcaColumn implements JcAnotacaoGet {
	
	private Class<? extends Annotation> tipo = Column.class;
	private String name;
	private Integer precision;
	private Integer scale;
	private Integer length;
	private Boolean nullable;
	
	@Override
	public JcAnotacao get() {
		JcAnotacao o = new JcAnotacao(tipo);
		if (name != null) o.addParametro("name", "\"" + name + "\"");
		if (nullable != null) o.addParametro("nullable", nullable);
		if (length != null) o.addParametro("length", length);
		if (precision != null) o.addParametro("precision", precision);
		if (scale != null) o.addParametro("scale", scale);
		return o;
	}
	
}
