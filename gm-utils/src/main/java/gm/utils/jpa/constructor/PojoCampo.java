package gm.utils.jpa.constructor;

import gm.utils.lambda.FTT;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PojoCampo {
	
	private String nome;
	private String alias;
	private FTT<String, Object> function;
	
	@Override
	public String toString() {
		if (!UString.isEmpty(alias) && !nome.equals(alias)) {
			return nome + " - " + alias;
		} else {
			return nome;
		}
	}
	
}
