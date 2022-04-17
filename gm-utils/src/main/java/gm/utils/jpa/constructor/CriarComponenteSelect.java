package gm.utils.jpa.constructor;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import gm.utils.classes.ListClass;
import gm.utils.config.UConfig;
import gm.utils.javaCreate.JcClasse;
import gm.utils.jpa.UJpa;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.string.UString;

public class CriarComponenteSelect {

	public static void exec(ConstructorBackConfig config) {
		exec(config.getEntidades(), config.statelessClass());
	}

	public static void exec(ListClass entidades, Class<? extends Annotation> statelessClass) {
		String m = UConfig.get().getNomeProjetoGlobal();
		m = m.replace("gm-fw", "fw");
		if (m.contains("-")) {
			m = UString.beforeFirst(m, "-");
		}
		m = UString.primeiraMaiuscula(m);
		JcClasse jc = new JcClasse(m + "Select");
		
		jc.addImport(Criterio.class);
		jc.addImport(UJpa.class);
		
		jc.addAnnotation(statelessClass);
		jc.atributo("em", EntityManager.class).addAnotacao(PersistenceContext.class);
		
		for (Class<?> o : entidades) {
			
			jc.addImport(o);
			
			final String s = o.getSimpleName();
			String sm = UString.primeiraMinuscula( o.getSimpleName() );
			
			String x = s + "Select<"+s+"Select<?>>";
			
			jc.metodo(sm).public_().typeSemValidacao(x)
			.add("return " + sm + "(null);");
			
			jc.metodo(sm).public_().typeSemValidacao(x).addParametro("prefixo", String.class)
			.add("return new " + s + "Select<>(null, new Criterio<>("+s+".class, em), prefixo);");
			
			jc.metodo(sm).public_().type(o).addParametro("id", int.class)
			.add("return " + sm + "().byId(id);");
			
			jc.metodo(sm+"Obrig").public_().type(o).addParametro("id", int.class)
			.add("" + s + " o = " + sm + "(id);")
			.add("UJpa.checaObrig(o, \""+sm+"\", id);")
			.add("return o;");
		}
		
		jc.save();
		
	}
	
}
