package gm.utils.jpa.select;

import java.util.List;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.comum.UList;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.PersisterMaster;
import gm.utils.jpa.SqlNative;
import gm.utils.jpa.UTableSchema;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.lambda.FT;
import gm.utils.lambda.FVoidT;
import gm.utils.number.ListInteger;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public abstract class SelectBase<ORIGEM, T extends IdObject, TS extends SelectBase<ORIGEM, T, TS>> {

	protected Criterio<?> c;
	private String prefixo;
	private Class<T> classe;
	private ORIGEM origem;
	private String campo;
	
	private static PersisterMaster persisterMaster;
	public static void setPersisterMaster(PersisterMaster persisterMaster) {
		SelectBase.persisterMaster = persisterMaster;
	}
	
	public void setC(Criterio<?> c) {
		this.c = c;
	}

	@SuppressWarnings("unchecked")
	public SelectBase(ORIGEM origem, Criterio<?> criterio, String prefixo, Class<T> classe) {
		if (origem == null) {
			this.origem = (ORIGEM) this;
		} else {
			this.origem = origem;
		}
		this.c = criterio;
		this.classe = classe;

		if (UString.isEmpty(prefixo)) {
			prefixo = "";
			campo = "id";
		} else {
			while (prefixo.startsWith(".")) {
				prefixo = prefixo.substring(1);
			}
			campo = prefixo + ".id";
		}
		this.prefixo = prefixo;
	}

	public void delete() {
		List<?> list = list();
		if (list.isEmpty()) {
			return;
		}
		Class<? extends IdObject> classe = UClass.getClass(list.get(0));
		persisterMaster.get(classe).deleteCast(list);
	}
	public void save() {
		List<?> list = list();
		if (list.isEmpty()) {
			return;
		}
		Class<?> classe = UClass.getClass(list.get(0));
		persisterMaster.get(classe).saveCast(list);
	}
	public int deleteNative() {
		MontarQueryNativa qn = getC().getQueryNativa();
		qn.setSelect(null);
		qn.addSelect("id");
		String sql = qn.getSql();
		sql = "delete from " + UTableSchema.get(classe) + " where id in (" + sql + ")";
		return SqlNative.execSQL(sql);
	}

	@Override
	public String toString() {
		return c.toString();
	}

	@SuppressWarnings("unchecked")
	public T byId(int id) {
		if (con == null) {
			if (getC().getEm() == null) {
				return (T) persisterMaster.get(classe).get(id);
			} else {
				return getC().getEm().find(classe, id);
			}
		} else {
			getC().id(id);
			return unique(con);
		}
	}

	@SuppressWarnings("unchecked")
	private TS THIS() {
		return (TS) this;
	}

	public TS limit(int x) {
		getC().limit(x);
		return THIS();
	}

	public TS abreParenteses() {
		getC().abreParenteses();
		return THIS();
	}
	public TS fechaParenteses() {
		getC().fechaParenteses();
		return THIS();
	}
	
	public TS or() {
		getC().or();
		return THIS();
	}

	public TS page(int x) {
		getC().page(x);
		return THIS();
	}

	public TS skip(int x) {
		getC().skip(x);
		return THIS();
	}

	public static ConexaoJdbc con;
	
	@SuppressWarnings("unchecked")
	public <XX extends IdObject> XX unique() {
		if (con == null) {
			return (XX) getC().unique();
		} else {
			return unique(con);
		}
	}

	@SuppressWarnings("unchecked")
	public <XX> XX uniqueJoinIf() {
		List<XX> list = (List<XX>) getC().list();
		if (list.isEmpty()) {
			return null;
		}
		XX result = list.remove(0);
		while (!list.isEmpty()) {
			XX x = list.remove(0);
			delete(x);
		}
		return result;
	}
	
	private <XXX> void delete(XXX o) {
		Class<XXX> classe = UClass.getClass(o);
		persisterMaster.get(classe).deleteCast(o);
	}	
	
	public <XX extends IdObject> XX uniqueObrig(FT<RuntimeException> exception) {
		XX o = unique();
		if (o == null) {
			throw exception.call();
		}
		return o;
	}
	
	public <XX extends IdObject> XX uniqueObrig() {
		XX o = unique();
		UAssert.notEmpty(o, "o == null");
		return o;
	}
	
	public <XX extends IdObject> XX unique(Class<XX> classe) {
		return unique();
	}

	@SuppressWarnings("unchecked")
	public <XX> XX unique(ConexaoJdbc con) {
		return (XX) getC().unique(con);
	}
	public <XX> XX uniqueObrig(ConexaoJdbc con) {
		XX o = unique(con);
		if (o == null) {
			throw UException.runtime("o == null");
		}
		return o;
	}
	
	public int count() {
		return getC().count();
	}
	public int paginas() {
		int count = count();
		int limit = getC().getLimit();
		int paginas = count / limit;
		if (paginas * limit < count) {
			paginas++;
		}
		return paginas;
	}
	@SuppressWarnings("unchecked")
	public <X> X first() {
		return (X) getC().first();
	}

	public boolean exists() {
		return getC().exists();
	}

	public TS ignoreObserver() {
		getC().ignoreObserver();
		return THIS();
	}
	
	public Integer getId() {
		IdObject o = unique();
		return o == null ? null : o.getId();
	}

	public ListInteger ids() {
		List<T> list = list();
		ListInteger ids = new ListInteger();
		for (T o : list) {
			ids.add(o.getId());
		}
		ids.sort();
		return ids;
	}

	public TS notInIf(List<T> itens) {
		if (itens.isEmpty()) {
			return THIS();	
		} else {
			return notIn(itens);
		}
	}
	@SuppressWarnings("unchecked")
	public TS notIn(T... itens) {
		List<T> asList = UList.asList(itens);
		return notIn(asList);
	}
	public TS notIn(List<T> itens) {
		if (itens.isEmpty())
			return THIS();
		ListInteger ids = new ListInteger();
		for (T o : itens) {
			ids.add(o.getId());
		}
		String s = getPrefixo();
		if (UString.isEmpty(s)) {
			s = "id";
		} else {
			s += ".id";
		}
		getC().not_in(s, ids);
		return THIS();
	}
	
	public <X> void exec(Class<X> classe, FVoidT<X> func) {
		Lst<X> list = list();
		for (X x : list) {
			func.call(x);
		}
	}

	@SuppressWarnings("unchecked")
	public <X> Lst<X> list() {
		if (con == null) {
			beforeSelect();
			return (Lst<X>) getC().list();
		} else {
			return list(con);
		}
	}

	@SuppressWarnings("unchecked")
	public <X> Lst<X> list(ConexaoJdbc con) {
		beforeSelect();
		return (Lst<X>) getC().list(con);
	}
	
	protected void beforeSelect() {}

	public Lst<T> distinct() {
		String s = campo;
		if (s.endsWith(".id")) {
			s = UString.beforeLast(s, ".");
		}
		return c.distinct(s);
	}
	public ORIGEM eq(T value) {
		if (value == null) {
			c.isNull(campo);
		} else {
			c.eq(campo, value);
		}
		return origem;
	}
	public ORIGEM ne(T value) {
		if (value == null || value.getId() == null) {
			c.isNotNull(campo);
		} else {
			c.ne(campo, value);
		}
		return origem;
	}
	public ORIGEM eqProperty(String s) {
		c.eqProperty(campo, s);
		return origem;
	}
	public ORIGEM neProperty(String s) {
		c.neProperty(campo, s + ".id");
		return origem;
	}
	public ORIGEM notIn(TS select) {
		throw UException.runtime("implemnetar");
//		return origem;
	}
	public ORIGEM inIf(List<T> list) {
		if (!list.isEmpty()) {
			return in(list);
		} else {
			return origem;
		}
	}
	public ORIGEM in(List<T> list) {
		c.in(campo, list);
		return origem;
	}
	@SuppressWarnings("unchecked")
	public ORIGEM in(T... list) {
		List<T> list2 = UList.asList(list);
		return in(list2);
	}
	public ORIGEM isNull() {
		c.isNull(campo);
		return origem;
	}
	public ORIGEM isNotNull() {
		c.isNotNull(campo);
		return origem;
	}
	public ORIGEM inSubQuery(String sql) {
		ListInteger ids = SqlNative.getInts(sql);
		c.in("id", ids);
		return origem;
	}
	public ORIGEM notIn(String sql) {
		ListInteger ids = SqlNative.getInts(sql);
		c.idNotIn(ids);
		return origem;
	}
	public String getSql() {
		return getC().getSql();
	}
	public abstract SelectInteger<?> id();
}
