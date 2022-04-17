package gm.utils.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* Atencao, um campo anotado como VisivelSe somente serah obrigatohrio se estiver visivel */
@Retention(RetentionPolicy.RUNTIME)
public @interface VisivelSe {
	String value();
}
