package gm.utils.classes;

import java.util.ArrayList;
import java.util.List;

import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public class UOrdenarClassesPorHierarquiaDeAtributos {
	
	private ListClass list;
	private ListString print;

	public UOrdenarClassesPorHierarquiaDeAtributos(ListClass list) {
		this.list = list.copy();
		sort();
	}

	private Atributos atributos(Class<?> classe) {
		Atributos as = ListAtributos.persist(classe, false);
		as.removePrimitivas();
		return as;
	}

	public void print(Class<?> classe) {
		String s = classe.getName();
		s = "<class>" + s + "</class>";
		print.add(s);
	}


	private void sort() {

		print = new ListString();

		ListClass ordenadas = new ListClass();

		// separar as que nao apontam para ninguem

		for (Class<?> classe : list) {
			Atributos as = atributos(classe);
			as.removeByType(classe);
			if (as.isEmpty()) {
				ordenadas.add(classe);
			} else {
				for (Atributo a : as) {
					if (!list.contains(a.getType())) {
						throw UException.runtime("A classe " + a.getType() + " n"+UConstantes.a_til+"o foi encontrada na rela"+UConstantes.cao+" de entidades -> " + a);
					}
				}
			}
		}

		list.removeAll(ordenadas);

		for (Class<?> classe : ordenadas) {
			print(classe);
		}
		print.add();

		for (int i = 0; i < 30; i++) {

			List<Class<?>> ordenadas2 = new ArrayList<>();

			for (Class<?> classe : list) {
				Atributos as = atributos(classe);
				as.removeByType(classe);
				for (Class<?> type : ordenadas) {
					as.removeByType(type);
				}
				if (as.isEmpty()) {
					ordenadas2.add(classe);
				}
			}

			for (Class<?> classe : ordenadas2) {
				print(classe);
			}
			print.add();

			ordenadas.addAll(ordenadas2);
			list.removeAll(ordenadas2);

		}

		if (list.isEmpty()) {
			list = ordenadas;
			return;
		}

		ULog.debug("Ordenadas:");
		print.print();
		ULog.debug();
		ULog.debug();
		ULog.debug("N"+UConstantes.a_til+"o Ordenadas:");

		ULog.debug(list.size());

		for (Class<?> classe : list) {
			print.add("------------------------------");
			print.add("*******" + classe);
			Atributos as = atributos(classe);
			as.removeByType(classe);
			for (Class<?> type : ordenadas) {
				as.removeByType(type);
			}
			ULog.debug("*******" + classe.getSimpleName());
			as.print();
		}

		throw UException.runtime("Existem mapeamentos que n"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel ordena-los");

	}
	
}
