package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* uma entidade vinculada eh uma entidade que soh existe em razao de outra.
 * isso quer dizer que um registro pai eh que darah origem a ela.
 * Se, por um acaso o campo do registro pai que aponta para esta for nulado,
 * o registro nesta serah automaticamente excluido.
 * Se for preenchido, o registro nesta serah automaticamente inserido
 *  */
@Retention(RetentionPolicy.RUNTIME)
public @interface EntidadeVinculada {
}
