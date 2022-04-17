package gm.utils.jpa;

import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;

public class CreateView {

	public static ListString exec(Class<?> classe) {
		
		Atributos as = ListAtributos.persist(classe);
		
		ListString list = new ListString();

		list.add("create view map." + classe.getSimpleName() + " as select");
		list.add("  id = " + as.getId().getColumnName());
		for (Atributo a : as) {
			list.add(", " + a.nome() + " = " + a.getColumnName());
		}
		list.add("from " + UTableSchema.get(classe));
		return list;
		
	}
	
}
