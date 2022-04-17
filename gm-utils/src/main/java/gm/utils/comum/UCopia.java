package gm.utils.comum;

import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;

public class UCopia {

	public static void copia(Object de, Object para) {
		try {

			Atributos asDe = ListAtributos.get(de);
			Atributos asPara = ListAtributos.get(para);
			asDe.removeStatics();
			asDe.removeFinais();

			Atributo id = asDe.getId();

			if (id != null) {
				asDe.add(id);
			}

			Atributo aux = asPara.get("aux");
			if (aux != null) {
				aux.set(para, "copiando");
			}
			
			for (Atributo a : asDe) {

				Atributo aPara = asPara.get(a.nome());

				if (aPara == null) {
					continue;
				}

				Object value = a.get(de);

				if (value == null) {
					// TODO colocar assim que precisar as outras primitivas

					if (aPara.is(int.class)) {
						aPara.set(para, 0);
					} else if (aPara.is(boolean.class)) {
						aPara.set(para, false);
					} else {

						try {
							aPara.set(para, null);
						} catch (Exception e) {
							throw UException.runtime(e.getMessage() + " > de: " + a.nome() + " > para: " + aPara.nome(), e);
						}
					}
					continue;
				}

				Class<?> deT = a.getType();
				Class<?> paraT = aPara.getType();

				if (!deT.equals(paraT)) {

					try {
						value = UType.parse(value, paraT);
						aPara.set(para, value);
					} catch (Exception e) {
					}

					continue;
				}
				if (deT.equals(java.util.List.class)) {
					try {
						aPara.set(para, a.get(de));
					} catch (Exception e) {
					}
				} else {
					value = a.get(de);
					try {
						aPara.set(para, value);
					} catch (Exception e) {
						ULog.debug(a);
					}
				}
			}
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> copiaList(List<T> list) {
		try {
			List<T> list2 = UClass.newInstance( list.getClass() );
			for (T t : list) {
				list2.add(copia(t));
			}
			return list2;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public static <T> T copia(T o) {
		try {
			T x = UClass.newInstance( UClass.getClass(o) );
			copia(o, x);
			return x;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	
}
