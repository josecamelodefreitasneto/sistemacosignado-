package gm.utils.jpa;

import javax.persistence.EntityManager;

import gm.utils.jpa.nativeQuery.UQuery;
import gm.utils.string.ListString;

public class UTest {
	public static ListString teste(EntityManager em, String sql) {
		ListString list = new ListString();
		try {
			new UQuery(em.createNativeQuery("select xxxx = 0, " + sql)).forEach(o -> {
				String s = "";
				for (int i = 0; i < o.length(); i++) {
					s += ";" + o.getString(i);
				}
				s = s.substring(1);
				list.add(s);
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
}
