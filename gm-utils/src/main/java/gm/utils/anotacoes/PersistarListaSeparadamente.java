package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * Anotar em campos do tipo List
 * "+UConstantes.E_agudo+" como o UpdateCascade
 * Caso verdadeiro a tela ir"+UConstantes.a_agudo+" salvar cada item da lista assim que "+UConstantes.e_agudo+" editado
 * e s"+UConstantes.o_agudo+" permitir"+UConstantes.a_agudo+" sua edi"+UConstantes.cedilha+""+UConstantes.a_til+"o se o registro pais estiver salvo
 * Caso verdadeiro quando se persistir o registro tamb"+UConstantes.e_agudo+"m ser"+UConstantes.a_til+"o persistidas
 * as listas filhas 
 * */
@Retention(RetentionPolicy.RUNTIME)
public @interface PersistarListaSeparadamente {
	boolean value();
}
