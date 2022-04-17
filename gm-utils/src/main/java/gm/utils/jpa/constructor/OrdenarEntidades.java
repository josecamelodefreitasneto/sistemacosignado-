package gm.utils.jpa.constructor;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.ListClass;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.jpa.IEntityHierarquica;
import gm.utils.lambda.FTT;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
public class OrdenarEntidades {
	
	private boolean fw;
	private Class<?> classeBusca;
	private FTT<Boolean, Atributo> funcaoChecaIsFwEntity;
	
	public OrdenarEntidades(ListClass list, boolean fw, Class<?> classeBusca, FTT<Boolean, Atributo> funcaoChecaIsFwEntity) {

		this.fw = fw;
		this.classeBusca = classeBusca;
		this.funcaoChecaIsFwEntity = funcaoChecaIsFwEntity;
		
		ListString print = new ListString();
		ListClass ordenadas = new ListClass();
		//separar as que nao apontam para ninguem
		for (Class<?> classe : list) {
			Atributos as = atributos(classe);
			as.removeByType(classe);
			if (as.isEmpty()) {
				ordenadas.add(classe);
			}
		}
		
		list.removeAll(ordenadas);
		for (Class<?> classe : ordenadas) {
			print(classe);
		}
		print.add();
		for (int i = 0; i < 20; i++) {
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
			if (classeBusca != null) {
				if (ordenadas.remove(classeBusca)) {
					ordenadas.add(0, classeBusca);
				}
			}
			list.addAll(ordenadas);
			return;
		}
		ULog.debug("Ordenadas:");
		ordenadas.print();
		ULog.debug();
		ULog.debug();
		ULog.debug("N\u00e3o Ordenadas:");
		ULog.debug(list.size());
		for (Class<?> classe : list) {
			print.add("------------------------------");
			print.add("*******" + classe);
			Atributos as = atributos(classe);
			as.removeByType(classe);
			for (Class<?> type : ordenadas) {
				as.removeByType(type);
			}
			as.print();
		}
		throw UException.runtime("Existem mapeamentos que n\u00e3o foi poss\u00edvel ordena-los");
	}
	private Atributos atributos(Class<?> classe) {
		Atributos as = ListAtributos.persist(classe, false);
		as.removePrimitivas();
		if (classeBusca != null) {
			as.removeByType(classeBusca);
		}
		as.removeByType(IEntityHierarquica.class);
		
		if (fw) {
			return as;	
		}
		
		if (funcaoChecaIsFwEntity != null) {
			Atributos as2 = as.copy();
			for (Atributo a : as2) {
				if (funcaoChecaIsFwEntity.call(a)) {
					as.remove(a);
				}
			}
		}
		
		
		return as;
	}
	private static void print(Class<?> classe) {
	}
}
