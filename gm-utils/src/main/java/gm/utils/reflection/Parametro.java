package gm.utils.reflection;

import java.lang.reflect.Parameter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Parametro {

	private Parameter parameter;
	private String nome;

	public Parametro(Parameter parameter) {
		this.parameter = parameter;
		setNome(parameter.getName());
	}

	public Class<?> getType() {
		return parameter.getType();
	}

	public boolean is(Class<?> classe) {
		return getType().equals(classe);
	}
	
	@Override
	public String toString() {
		return parameter.toString();
	}

}
