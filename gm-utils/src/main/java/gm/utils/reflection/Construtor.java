package gm.utils.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class Construtor extends IMetodo {
	private Constructor<?> constructor;
	public Construtor(Constructor<?> constructor) {
		this.constructor = constructor;
	}
	@Override
	protected Parameter[] getParameters() {
		return constructor.getParameters();
	}
	@Override
	protected int getModifiers() {
		return constructor.getModifiers();
	}
	@Override
	public Class<?> getClasseImpl() {
		return constructor.getDeclaringClass();
	}
	@Override
	public String getAssinaturaSemParametrosImpl() {
		return getModificadorDeAcesso() + " " + getClasse().getName();
	}
	@Override
	protected Class<?> getDeclaringClasseImpl() {
		return constructor.getDeclaringClass();
	}
}
