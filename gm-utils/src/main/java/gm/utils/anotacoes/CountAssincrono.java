package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CountAssincrono {
	/*nome da classe que ser"+UConstantes.a_agudo+" contada*/
	String classe();
	
	/*
	 * caso a tabela filha possua mais de um campo
	 * apontando para a tabela pai, ser"+UConstantes.a_agudo+" necess"+UConstantes.a_agudo+"rio
	 * informar o campo
	 * */
	String referencia() default "";

	/*
	 * caso a contagem seja condicional
	 * */
	String condicao() default "";
	
}
