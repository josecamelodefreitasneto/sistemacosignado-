package gm.utils.jpa.nativeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.persistence.Query;

import gm.utils.comum.UList;
import gm.utils.lambda.FTT;
import gm.utils.lambda.FVoidT;

public class UQuery {

	private Query query;
	private List<UQueryRow> list;

	public UQuery(Query query) {
		this.query = query;
	}
	public UQuery param(String key, Object value) {
		query.setParameter(key, value);
		return this;
	}
	public UQuery param(int position, Object value) {
		query.setParameter(position, value);
		return this;
	}
	@SuppressWarnings("unchecked")
	public List<UQueryRow> list() {
		if (list == null) {
			list = new ArrayList<>();
			List<Object[]> lst = query.getResultList();
			lst.forEach(o -> list.add(new UQueryRow(o)));
		}
		return list;
	}
	public void forEach(FVoidT<UQueryRow> action) {
		list().forEach(o -> action.call(o));
	}
	public <T> List<T> map(FTT<T, UQueryRow> action) {
		List<T> list = new ArrayList<>();
		forEach(o -> list.add(action.call(o)));
		return list;
	}
	public UQueryRow unique() {
		return UList.getUnique(list());
	}
	public UQuery filter(Predicate<UQueryRow> predicate) {
		UQuery o = new UQuery(null);
		o.list = UList.filter(list(), predicate);
		return o;
	}
	
}
