package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* Aten"+UConstantes.cedilha+""+UConstantes.a_til+"o, um campo anotado como VisivelSe somente ser"+UConstantes.a_agudo+" obrigat"+UConstantes.o_agudo+"rio se estiver visivel */
@Retention(RetentionPolicy.RUNTIME)
public @interface SomenteLeituraSe {
	String value();
}
