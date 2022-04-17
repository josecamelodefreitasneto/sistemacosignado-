package gm.utils.jpa;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.jpa.criterions.QueryOperator;
import gm.utils.reflection.Atributo;
import gm.utils.string.ListString;
public class NativeUpdate {
	public static int exec(Class<?> classe, Map<Atributo, Object> map, List<QueryOperator> adds) {
		return exec(classe, map, adds, 0, 0);
	}
	public static int exec(Class<?> classe, Map<Atributo, Object> map, List<QueryOperator> adds, int offSet, int limit) {
		Map<String, Object> map2 = new HashMap<>();
		for (Atributo a : map.keySet()) {
			map2.put(a.nome(), map.get(a));
		}
		return execKeyset(classe, map2, adds, offSet, limit);
	}
	public static int execKeyset(Class<?> classe, Map<String, Object> map, List<QueryOperator> adds, int offSet, int limit) {
		Map<String, String> mp = new HashMap<>();
		mp.put("id", "id");
		String s = "";
		Set<String> keys = map.keySet();
		if (keys.isEmpty()) return 0;
		for (String a : keys) {
			s += ", " + a + " = " + SqlNativeValue.get(map.get(a));
		}
		s = s.substring(1);
		s = "update " + UTableSchema.get(classe) + "\nset" + s;
		s += "\nwhere id in (";
		MontarQueryNativa qn = new MontarQueryNativa(classe);
		qn.setSemOrderBy(true);
		qn.setSelect(mp);
		qn.setOps(adds);
		if (offSet > 0) {
			qn.setOffSet(offSet);
		}
		if (limit > 0) {
			qn.setLimit(limit);
		}
		ListString result = qn.getResult();
		s += result.toString("\n");
		s += "\n)";
		return SqlNative.execSQL(s);
	}
	public static int exec(Class<?> classe, Map<Atributo, Object> map, Criterio<?> c) {
		return exec(classe, map, c.getAdds(), c.offSet(), c.getLimit());
	}
}
