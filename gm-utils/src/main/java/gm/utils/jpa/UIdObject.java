package gm.utils.jpa;

import java.util.List;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.UAssert;
import gm.utils.comum.UCompare;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;

public class UIdObject {

//	public static Integer getId(IEntity<?> o) {
//		if (o == null) return null;
//		return o.getId();
//	}
	
	public static Integer getId(IdObject o) {
		if (o == null) {
			return null;
		} else {
			return o.getId();
		}
	}
//	public static String getText(IdObject o) {
//		if (o == null) {
//			return null;
//		} else {
//			return o.getText();
//		}
//	}
	
	public static Integer getId(Object o) {
		
		UAssert.notEmpty(o, "o == null");

		if (UType.isPrimitiva(o)) {
			return null;
		}

		if (o instanceof List) {
			return null;
		}

		// Method method = Utils.getMethod("getId", o.getClass());
		//
		// if ( method == null ) {
		// method = Utils.getMethod("getId" + o.getClass().getSimpleName(),
		// o.getClass());
		// }
		//
		// if ( method != null ) {
		// return Utils.getValue(method, o);
		// }

		Atributos atributos = ListAtributos.get(UClass.getClass(o));
		Atributo atributoId = atributos.getId();
		if (atributoId == null)
			return null;
		return atributoId.getInt(o);
	}
	
	public static void checaObrig(Object o, String nome, int id) {
		if (o == null) {
			throw UException.runtime( String.format("N"+UConstantes.a_til+"o encontrado: %s -> %d", nome, id));
		}
	}
	
	public static boolean eq(IdObject a, IdObject b) {
		Integer x = UCompare.basicCompare(a, b);
		if (x == null) return a.getId().equals(b.getId());
		return x == 0;
	}
	public static boolean ne(IdObject a, IdObject b) {
		return !eq(a, b);
	}
	
}
