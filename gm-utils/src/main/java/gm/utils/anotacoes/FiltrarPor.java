package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FiltrarPor {
	String campoDaEntidadeAtual();
	String campoDaEntidadeDoTipoDoCampo();
	String campoDaEntidadeAtual2() default "";
	String campoDaEntidadeDoTipoDoCampo2() default "";
	String campoDaEntidadeAtual3() default "";
	String campoDaEntidadeDoTipoDoCampo3() default "";
}
