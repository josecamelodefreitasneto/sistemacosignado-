package gm.utils.jpa.criterions;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
//import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.InExpression;
import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.NotExpression;
import org.hibernate.criterion.NotNullExpression;
import org.hibernate.criterion.NullExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertySubqueryExpression;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.internal.CriteriaImpl.OrderEntry;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.Lst;
import gm.utils.comum.UCompare;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Cronometro;
import gm.utils.date.Data;
import gm.utils.date.Periodo;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.UTable;
import gm.utils.number.ListInteger;
import gm.utils.number.ListLong;
import gm.utils.number.Numeric2;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class Criterio<T> {
	
	private static enum TipoBusca {
		nativa, criteria, criteriaQuery
	}
	
	private TipoBusca tipoBusca = TipoBusca.criteriaQuery;
	private Class<T> classe;
	private Atributo id;
	protected EntityManager em;
	
	public Atributo getId() {
		if (this.as == null) {
			this.as();
		}
		return this.id;
	}

	private Atributos as;

	private Atributos as() {
		if (this.as == null) {
			this.as = ListAtributos.get(this.classe);
			this.as.removeTransients();
			this.as.removeStatics();
			this.id = this.as.getId();
		}
		return this.as;
	}

	private boolean ignoreObserver = false;

	public Criterio(final Class<T> classe) {
		this.classe = classe;
	}
	public Criterio(final Class<T> classe, final EntityManager em) {
		this(classe);
		this.em = em; 
	}
	
	public Criterio<T> skip(final int x) {
		this.skip = x;
		return this;
	}
	
	private boolean beforeListChecked = false;
	protected void beforeList() {
		
		if (beforeListChecked) {
			return;
		}
		
		while (this.fechaParenteses());
		if (!this.ignoreObserver && !this.observerExecutado) {
			if (this.em == null) {
				UConfig.jpa().beforeSelect(this, this.classe);
			}
			this.observerExecutado = true;
		}
		
		beforeListChecked = true;
		
	}
	
	private static Atributos atributosCriteria;
	
	@Deprecated//verificar como utilizar na nova versao do jpa
	private Criteria newCriteria() {
		
		this.beforeList();
		
		Criteria criteria;
		
		if (this.em == null) {
			criteria = UConfig.jpa().createCriteria(this.classe);
		} else {
			final Session session = this.em.unwrap(Session.class);
			criteria = session.createCriteria(this.classe);
		}
		
		if (Criterio.atributosCriteria == null) {
			Criterio.atributosCriteria = ListAtributos.get( UClass.getClass(criteria) );
		}
		
		for (final QueryOperator criterion : this.adds) {
			criteria.add(criterion.getCriterion());
		}
		this.setAlias(criteria);
		if (this.limit > 0) {
			criteria.setMaxResults(this.limit);
			if (this.page == 0) {
				this.page = 1;
			}
			final int firstResult = this.skip + (this.page-1) * this.limit;
			if (firstResult < 0) {
				throw UException.runtime("firstResult < 0");
			}
			criteria.setFirstResult(firstResult);
		} else if (this.limit != -1) {
			criteria.setFirstResult(this.skip);
			criteria.setMaxResults(500);
		}
		return criteria;
	}
	
	public int count() {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return this.countCriteriaQuery();	
		} else if (tipoBusca == TipoBusca.criteria) {
			return this.countCriteria();
		} else if (tipoBusca == TipoBusca.nativa) {
			return countNativa();
		} else {
			throw new RuntimeException("???");
		}
	}
	
	private int countNativa() {
		throw new RuntimeException("N"+UConstantes.a_til+"o implementado");
	}
	
	private int countCriteriaQuery() {
		return new CriterioQuery<Long>(this, Long.class).count();
	}
	
	private int countCriteria() {
		
		final List<Order> orderByOriginal = this.orderBy;
		this.orderBy = null;
		
		try {
			final Criteria c = this.newCriteria();
			Criterio.atributosCriteria.get("maxResults").set(c, 1);
			final List<?> orderEntries = Criterio.atributosCriteria.get("orderEntries").get(c);
			if (orderEntries.size() > 0) {
				orderEntries.clear();
			}
//			MonitorSqlBo.add(this, "count(*)", "x");
			final Number number = (Number) c.setProjection(Projections.rowCount()).uniqueResult();
			if (number == null) {
				return 0;
			}
			return number.intValue();
		} catch (final Exception e) {
			throw UException.runtime(e);
		} finally {
			this.observerExecutado = false;
			this.orderBy = orderByOriginal;
		}
	}
	private int limit = 0;
	private int page = 0;
	private int skip = 0;
	public Criterio<T> setLimit(final int limit) {
		if (limit < -1) {
			this.limit = 0;
		} else {
			this.limit = limit;
		}
		return this;
	}
	public Criterio<T> limit(final int limit) {
		return this.setLimit(limit);
	}
	public Criterio<T> order(final Atributo atributo) {
		return this.order(atributo.getReal().nome());
	}
	public Criterio<T> order(final String s) {
		return this.addOrderAsc(s);
	}
	public Criterio<T> desc(final String s) {
		return this.addOrderDesc(s);
	}
	public Criterio<T> page(final int page) {
		this.page = page;
		if ( this.limit == 0 ) {
			this.limit(20);
		}
		return this;
	}
	public Lst<T> list(final EntityManager em) {
		this.em = em;
		return this.list();
	}
	public Lst<T> list() {
		return busca().list;
	}
	private CriterioResult<T> busca() {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return this.buscaComCriteriaQuery();	
		} else if (tipoBusca == TipoBusca.criteria) {
			return this.buscaComCriteria();
		} else if (tipoBusca == TipoBusca.nativa) {
			return buscaComSql();
		} else {
			throw new RuntimeException("???");
		}
	}
	
	public List<T> list(final ConexaoJdbc con) {
		final MontarQueryNativa qn = this.getQueryNativa();
		qn.addSelect("id");
		final UTable table = con.table( this.getClasse() );
		final List<T> list = table.selectAs("id in ("+qn.getSql()+")");
		return list;
	}
	public Criterio<T> ignoreObserver() {
		this.ignoreObserver = true;
		return this;
	}
	private boolean observerExecutado = false;
	
	private CriterioResult<T> buscaComSql() {
		String sql = this.getSql();
		sql = sql.replace("\n", " ");
		sql = UString.trimPlus(sql);
		sql = sql.replace("a.*", "a");
		sql = sql.replace(" distinct ", " ");
		sql = sql.replace(" as ", " ");
		sql = sql.replace("prospect.", "");
		
		TypedQuery<T> createQuery;
		
		if (this.em == null) {
			createQuery = UConfig.jpa().createQuery(sql, this.classe);
		} else {
			createQuery = this.em.createQuery(sql, this.classe);		
		}
		
		final List<T> resultList = createQuery.getResultList();
		final CriterioResult<T> result = new CriterioResult<>();
		result.s = sql;
		result.list = new Lst<>(resultList);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private CriterioResult<T> buscaComCriteria() {
		
		Criteria c;
		
		try {
			c = this.newCriteria();
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
		
		try {

			this.setOrderBy(c);
			
			final Cronometro cronometro = new Cronometro();
			final List<T> list = c.list();
			if (cronometro.tempo() > 5000) {
				UException.runtime("Consulta demorou: " + cronometro.tempo() + " >> " + c.toString());
			}

			final CriterioResult<T> result = new CriterioResult<>();
			result.s = c.toString();
			result.list = new Lst<>(list);
//			MonitorSqlBo.add(this, "*", "*");
			return result;
		} catch (final Exception e) {
			ULog.error( UString.toString(c) );
			throw UException.runtime(e);
		}
	}
	
	private CriterioResult<T> buscaComCriteriaQuery() {
		return new CriterioQuery<T>(this, classe).result();
	}	

	private void setOrderBy(final Criteria c) {
		if (!UList.isEmpty(this.orderBy)) {
			for (final Order order : this.orderBy) {
				c.addOrder(order);
			}
		}
	}
	public T listUnique() {
		if (this.limit == 0) {
			this.limit(2);
		}
		return this.findUnique(this.busca());
	}
	public T unique(final ConexaoJdbc con) {
		final Integer id = this.uniqueId(con);
		if (id == null) {
			return null;
		} else {
			return con.table(this.getClasse()).byId(id);
		}
	}
	public Integer uniqueId(final ConexaoJdbc con) {
		final MontarQueryNativa qn = this.getQueryNativa();
		qn.addSelect("id");
		final String sql = qn.getSql();
		final Integer id = con.selectInt(sql);
		return id;
	}
	public T uniqueObrig() {
		return this.uniqueObrig("o == null");
	}
	public T uniqueObrig(final String message) {
		final T o = this.unique();
		if (o == null) {
			throw new RuntimeException(message);
		}
		return o;
	}
	public T unique() {
		return this.listUnique();
	}
	private T findUnique(final CriterioResult<T> result) {
		if (result.list.isEmpty()) {
			return null;
		}
		if (result.list.size() > 1) {
			final String message = "A lista retornou + de 1 resultado: " + this.classe.getName() + " - " + result.s;
			throw UException.runtime(message);
		}
		return result.list.get(0);
	}
	public Criterio<T> in(final String field, final List<?> list) {
		this.add(this.op_in(field, list));
		return this;
	}
	public void not_in(final Collection<?> list) {
		this.add(this.restriction_not_in(list));
	}
	public void in_subSelect(final Class<?> classe, final String campo) {
		this.add( new QueryOperator_InSubSelect(classe, campo) );
	}
	private Collection<?> trataIds(Collection<?> ids) {
		if ( this.getId().isLong()) {
			final List<Long> longs = new ArrayList<>();
			for (final Object o : ids) {
				longs.add(ULong.toLong(o));
			}
			ids = longs;
		}
		return ids;
	}
	public Criterio<T> idIn(final Collection<?> ids) {
		return this.add(this.restriction_in(this.getId(), this.trataIds(ids)));
	}
	public Criterio<T> idNotIn(final Collection<?> ids) {
		return this.add(this.restriction_not_in(this.getId(), this.trataIds(ids)));
	}
	public void in(final Collection<?> list) {
		final QueryOperator_In op = this.restriction_in(list);
		this.add(op);
	}
	public Criterio<T> notInIgnoreEmpty(final String field, final Object... values) {
		if (values == null || values.length == 0) {
			return this.backIfOr();
		}
		return this.not_in(field, values);
	}
	public Criterio<T> not_in(final String field, final Object... values) {
		return this.add(this.restriction_not_in(field, values));
	}
	private void removeNulls(Collection<?> list) {
		
		list = UList.removeEmptys(list);
		
		if (list.isEmpty()) {
			return;
		}
		final Object[] array = list.toArray();
		final List<Object> itensToRemove = new ArrayList<>();
		for (final Object o : array) {
			if ( o instanceof IdObject ) {
				final IdObject x = (IdObject) o;
				final Integer id = x.getId();
				if (id == null) {
					itensToRemove.add(o);
				}
			}
		}
		list.removeAll(itensToRemove);
	}
	public Criterio<T> not_in(final String field, final Collection<?> list) {
		this.removeNulls(list);
		if (!list.isEmpty()) {
			this.add(this.restriction_not_in(field, list));
		}
		return this;
	}
	public Criterio<T> in(final String field, final Object... values) {
		this.add(this.restriction_in(field, values));
		return this;
	}
	public Criterio<T> in(final String field, final Collection<?> list) {
		this.add(this.op_in(field, list));
		return this;
	}
	public Criterio<T> maiorOuIgual(final String field, final Data data) {
		return this.maiorOuIgual(field, data.getCalendar().getTime());
	}
	public Criterio<T> menorOuIgual(final String field, final Data data) {
		return this.menorOuIgual(field, data.getCalendar().getTime());
	}
	public Criterio<T> menorOuIgual(final Atributo a, final Data data) {
		return this.menorOuIgual(a.nome(), data);
	}
	public Criterio<T> menorOuIgual(final Atributo a, final Object value) {
		if (value instanceof Data) {
			final Data data = (Data) value;
			return this.menorOuIgual(a, data);
		}
		return this.menorOuIgual(a.nome(), value);
	}
	public Criterio<T> maiorOuIgual(final Atributo a, Object value) {
		if (value instanceof Data) {
			final Data data = (Data) value;
			value = data.getCalendar();
		}
//		if (value instanceof Integer) {
//			Integer data = (Integer) value;
//			return maiorOuIgual(a, data);
//		}
		return this.maiorOuIgual(a.nome(), value);
	}
	public Criterio<T> menor(final Atributo a, final Data data) {
		return this.menor(a.nome(), data);
	}
	public Criterio<T> menor(final String field, final Data data) {
		return this.menor(field, data.getCalendar().getTime());
	}
	public Criterio<T> maior(final String field, final Data data) {
		return this.maior(field, data.getCalendar().getTime());
	}
	public Criterio<T> maior(final Atributo a, final Numeric2 value) {
		return this.maior(a, value.getValor());
	}
	public Criterio<T> maior(final Atributo a, final BigDecimal value) {
		return this.maior(a.nome(), value);
	}
	public Criterio<T> menor(final Atributo a, final Numeric2 value) {
		return this.menor(a, value.getValor());
	}
	public Criterio<T> menor(final Atributo a, final BigDecimal value) {
		return this.menor(a.nome(), value);
	}
	public Criterio<T> maior(final Atributo a, final Data data) {
		return this.maior(a.nome(), data);
	}
	public Criterio<T> maior(final Atributo a, final Integer value) {
		return this.maior(a.nome(), value);
	}
	public Criterio<T> menor(final Atributo a, final Integer value) {
		return this.menor(a.nome(), value);
	}
	public Criterio<T> maiorOuIgual(final String field, final Object value) {
		this.add(this.restriction_maiorOuIgual(field, value));
		return this;
	}
	public Criterio<T> menorOuIgual(final String field, final Object value) {
		this.add(this.restriction_menorOuIgual(field, value));
		return this;
	}
	public Criterio<T> entre(final String field, final Periodo periodo) {
		final Atributo f = this.as().get(field);
		Object v1;
		Object v2;
		if (f.getType().equals(Date.class)) {
			v1 = periodo.getInicio().toDate();
			v2 = periodo.getFim().toDate();
		} else if (f.getType().equals(Calendar.class)) {
			v1 = periodo.getInicio().getCalendar();
			v2 = periodo.getFim().getCalendar();
		} else {
			throw UException.runtime("Tipo desconhecido de data: " + f.getType());
		}
		this.entre(field, v1, v2);
		return this;
	}
	public Criterio<T> entre(final Atributo a, final Object value1, final Object value2) {
		return this.entre(a.nome(), value1, value2);
	}
	public Criterio<T> entre(final String field, final Object value1, final Object value2) {
		if (value1 == null) throw UException.runtime("value1 == null");
		if (value2 == null) throw UException.runtime("value2 == null");
		if (value1.equals(value2)) {
			this.eq(field, value1);
		} else {
			this.maiorOuIgual(field, value1);
			this.menorOuIgual(field, value2);
		}
		return this;
	}
	public Criterio<T> naoEntre(final Atributo a, final Object value1, final Object value2) {
		return this.naoEntre(a.nome(), value1, value2);
	}
	public Criterio<T> naoEntre(final String field, final Object value1, final Object value2) {
		this.menor(field, value1);
		this.maior(field, value2);
		return this;
	}
	public Criterio<T> menor(final String field, final Object value) {
		this.add(this.restriction_menor(field, value));
		return this;
	}
	public Criterio<T> maior(final String field, final Object value) {
		this.add(this.restriction_maior(field, value));
		return this;
	}
	public Criterio<T> isNull(final String field) {
		this.add(this.restriction_isNull(field));
		return this;
	}
	public Criterio<T> isNull(final Atributo a) {
		return this.isNull(a.getNomeValue());
	}
	public Criterio<T> isNotNull(final Atributo a) {
		return this.isNotNull(a.getNomeValue());
	}
	public Criterio<T> isNotNull(final String field) {
		this.add(this.restriction_isNotNull(field));
		return this;
	}
	public Criterio<T> eqResolve(final String field, Object value, final boolean considerarNulo) {
		value = this.resolve(value.getClass(), value);
		if (value == null && !considerarNulo) {
			return this.backIfOr();
		}
		this.eq(field, value);
		return this;
	}
	public Object resolve(final Class<?> classe, final Object o){
		if (o == null) {
			return null;
		}
		Object id = o;
		if ( UClass.isInstanceOf(o, classe) ) {
			final Atributo atributoId = ListAtributos.getId(classe);
			id = atributoId.get(o);
			if (id == null) {
				return null;
			}
		}
		try {
			if (this.em == null) {
				return UConfig.jpa().findById(classe, id);
			} else {
				return this.em.find(classe, id);
			}
		} catch (final Exception e) {}
		
		throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel resolver: " + classe + " >> " + o);
	}	
	public Criterio<T> eqNn(final String field, final Object value) {
		if (value == null) {
			return this.backIfOr();
		}
		if (!UType.isPrimitiva(value)) {
			final IdObject o = (IdObject) value;
			if (o.getId() == null) {
				return this.backIfOr();
			}
		}
		return this.eq(field, value);
		
	}
	public Criterio<T> eqNn(final Object value) {
		if (value == null) {
			return this.backIfOr();
		}
		if (!UType.isPrimitiva(value)) {
			final IdObject o = (IdObject) value;
			if (o.getId() == null) {
				return this.backIfOr();
			}
		}
		return this.eq(value);
	}
	public Criterio<T> eq(final Object... values) {
		for (final Object value : values) {
			this.eq(value);
		}
		return this;
	}
	public Criterio<T> eq(final Object value) {
		if (value == null) {
			throw UException.runtime("value == null");
		}
		final Class<?> type = UClass.getClass(value);
		
		final Atributos atributos = this.as().getWhereType(type);

		if (atributos.isEmpty()) {
			final Atributos as2 = this.as();
			for (final Atributo field : as2) {
				if (UClass.a_herda_b(type, field.getType())) {
					atributos.add(field);
				}
			}
			if (atributos.isEmpty()) {
				throw UException.runtime("Nenhum field com o type = " + type.getName());
			}
		}
		if (atributos.size() > 1) {
			throw UException.runtime("Varios fields com o type = " + type.getName());
		}

		return this.eq(atributos.get(0), value);
	}
	public Criterio<T> eq(final Atributo a, final Object value) {
		return this.eq(a.getNomeValue(), value);
	}
	public Criterio<T> eq(final String field, final Object value) {
		this.add(this.restriction_eq(field, value));
		return this;
	}
	private QueryOperator _eqProperty(final String a, final String b) {
		return new QueryOperator_eqProperty(a, b);
	}
	
	public Criterio<T> eqProperty(final String a, final String b) {
		this.add(this._eqProperty(a,b));
		return this;
	}
	public Criterio<T> neProperty(final String a, final String b) {
		this.add(this._eqProperty(a,b).not());
		return this;
	}
	public Criterio<T> ne(final int id) {
		return this.idNotIn(new ListInteger(id));
	}
	public Criterio<T> ne(final Long id) {
		if (id == null) {
			return this.backIfOr();
		}
		return this.idNotIn(new ListLong(id));
	}
	public Criterio<T> ne(final Atributo a, final Object value) {
		return this.ne(a.nome(), value);
	}
	public Criterio<T> ne(final String field, final Object value) {
		this.add(this.restriction_ne(field, value));
		return this;
	}
	private Criterio<T> in(final Atributo a, final String nativeSubquery, final boolean not) {
		final QueryOperator_InNativeSubQuery op = new QueryOperator_InNativeSubQuery(a.getColumnName(), nativeSubquery);
		op.setNot(not);
		this.add(op);
		return this;
	}
	public Criterio<T> in(final Atributo a, final String nativeSubquery) {
		return this.in(a, nativeSubquery, false);
	}
	public Criterio<T> notIn(final Atributo a, final String nativeSubquery) {
		return this.in(a, nativeSubquery, true);
	}
	public Criterio<T> in(final String nativeSubquery) {
		return this.in(this.as().getId(), nativeSubquery);
	}
	public Criterio<T> notIn(final String nativeSubquery) {
		return this.notIn(this.as().getId(), nativeSubquery);
	}
	public Criterio<T> in(String field, final String nativeSubquery) {
		if (field.endsWith(".id")) {
			field = UString.beforeLast(field, ".");
		}
		return this.in(this.as().get(field), nativeSubquery);
	}
	public Criterio<T> notIn(final String field, final String nativeSubquery) {
		return this.notIn(this.as().get(field), nativeSubquery);
	}
	public Criterio<T> like(final Atributo atributo, final String value) {
		return this.like(atributo.nome(), value);
	}
	public Criterio<T> like(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringContains(field, value));
		return this;
	}
	public Criterio<T> notLike(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringContains(field, value).not());
		return this;
	}
	public Criterio<T> startsWith(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringStartsWith(field, value));
		return this;
	}
	public Criterio<T> notStartsWith(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringStartsWith(field, value).not());
		return this;
	}
	public Criterio<T> endsWith(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringEndsWith(field, value));
		return this;
	}
	public Criterio<T> notEndsWith(final String field, final String value) {
		if (UString.isEmpty(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringEndsWith(field, value).not());
		return this;
	}
	public Criterio<T> ativos() {
		return this.ativos(true);
	}
	public Criterio<T> inativos() {
		return this.ativos(false);
	}
	public Criterio<T> ativos(final boolean val) {
		final Atributo ativo = this.as().getAtivo();
		if (ativo != null) {
			this.eq(ativo, val);
		}
		return this;
	}
	public Integer maxId() {
		final Object o = this.max(this.getId());
		return UInteger.toInt(o);
	}
	public <X> X max(final Atributo a) {
		return this.max(a.getField());
	}
	public <X> X max(final Field field) {
		return this.max(field.getName());
	}
	public <X> X max(final String field) {
		return maxComCriteria(field);
	}
	@SuppressWarnings("unchecked")
	private <X> X maxComCriteria(final String field) {
		final Criteria c = this.newCriteria();
		return (X) c.setProjection(Projections.max(field)).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public <X> X min(final String field) {
		final Criteria c = this.newCriteria();
//		MonitorSqlBo.add(this, "min("+field+")", "x");
		return (X) c.setProjection(Projections.min(field)).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public <X> X sum(final String field) {
		final Criteria c = this.newCriteria();
		final Object o = c.setProjection(Projections.sum(field)).uniqueResult();
//		MonitorSqlBo.add(this, "sum("+field+")", "x");
		return (X) o;
	}
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> sum(final String groupField, final String sumField, final boolean sumFieldOrder, final boolean ascOrder) {
		final ProjectionList p = Projections.projectionList();
		p.add(Projections.groupProperty(groupField));
		p.add(Projections.sum(sumField));
		final Criteria c = this.newCriteria();
		c.setProjection(p);
		final List<Object[]> l = c.list();
		final int index = sumFieldOrder ? 1 : 0;
		final Comparator<Object[]> comparator = (a, b) -> {
			final Comparable<Object> va = (Comparable<Object>) a[index];
			final Comparable<Object> vb = (Comparable<Object>) b[index];
			int i = va.compareTo(vb);
			if (!ascOrder) {
				i = i * -1;
			}
			return i;
		};
		l.sort(comparator);
		final LinkedHashMap<K, V> map = new LinkedHashMap<>();
		for (final Object[] o : l) {
			final K key = (K) o[0];
			final V value = (V) o[1];
			map.put(key, value);
		}
		return map;
	}
	public <X> Lst<X> distinct(String campo) {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return distinctCriteriaQuery(campo);	
		} else if (tipoBusca == TipoBusca.criteria) {
			return distinctCriteria(campo);
		} else if (tipoBusca == TipoBusca.nativa) {
			return distinctNativa(campo);
		} else {
			throw new RuntimeException("???");
		}
	}
	private <X> Lst<X> distinctCriteriaQuery(String campo) {
		return new CriterioQuery<Object>(this, Object.class).distinct(campo);
	}
	private <X> Lst<X> distinctNativa(String campo) {
		throw new RuntimeException("???");
	}
	@SuppressWarnings("unchecked")
	private <X> Lst<X> distinctCriteria(String campo) {
		final Criteria c = this.newCriteria();
		campo = this.addAlias(campo, c);
		final List<?> list = c.setProjection(Projections.distinct(Projections.property(campo))).list();
		final List<?> x = UList.removeEmptys(list);
		final Lst<X> list2 = new Lst<>();
		for (final Object o : x) {
			list2.add((X) o);
		}
		list2.sort(UCompare.instance);
		return list2;
	}
	private String addAlias(final String campo, final Criteria c) {
		return CriterioSetAlias.addAlias(this.aliases, campo, c);
	}
	private QueryOperator_In restriction_in(final Atributo atributo, final Collection<?> list) {
		return this.op_in(atributo.nome(), list);
	}
	private QueryOperator_In op_in(final String string, Collection<?> list) {

		list = UList.removeEmptys(list);
		
		if (UList.isEmpty(list)) {
			throw UException.runtime("A lista esta vazia");
		}
		final List<Object> l = new ArrayList<>();
		for (Object o : list) {
			o = this.resolveValue(string, o);
			l.add(o);
		}

		return new QueryOperator_In(string, l);

	}
	private QueryOperator_In restriction_not_in(final Collection<?> list) {
		final QueryOperator_In op = this.restriction_in(list);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_in(final Collection<?> list) {
		final Atributo atributoId = UList.getId(list);
		final ListInteger ids = new ListInteger();
		for (final Object o : list) {
			final Integer id = atributoId.get(o);
			if (!ids.contains(id)) {
				ids.add(id);
			}
		}
		
		final QueryOperator_In op = this.op_in("id", ids);
		return op;
	}
	private QueryOperator_In restriction_not_in(final Atributo atributo, final Collection<?> list) {
		return this.restriction_not_in(atributo.nome(), list);
	}
	private QueryOperator_In restriction_not_in(final String field, final Object... values) {
		final QueryOperator_In op = this.restriction_in(field, values);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_not_in(final String field, final Collection<?> list) {
		final QueryOperator_In op = this.op_in(field, list);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_in(final String field, final Object... values) {
		final List<Object> list = Arrays.asList(values);
		final QueryOperator_In op = this.op_in(field, list);
		return op;
	}
	private QueryOperator_MaiorOuIgual restriction_maiorOuIgual(final String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_MaiorOuIgual(field, value);
	}
	private QueryOperator_MenorOuIgual restriction_menorOuIgual(final String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_MenorOuIgual(field, value);
	}
	public static Object resolveValue(final Atributo atributo, final Object value) {
		
		final Class<?> type = atributo.getType();
		
//		if (type.equals(IEntity.class)) {
//			throw UException.runtime("type == IEntity - Imposs"+UConstantes.i_agudo+"vel resolver o valor de um tipo abstrato >> " + field);
//		}
		if (UType.isPrimitiva(type)) {
			return UType.tryCast(value, type);
		}
		try {

			if (UType.isPrimitiva(value)) {
				final Object o = UConfig.jpa().findById(type, value);
				if (o != null)
					return o;
			} else {

				final Atributos a = ListAtributos.get(type.getClass());

				if (a.getId() != null) {
					final Object id = a.getId().get(value);

					if (id == null) {
						throw UException.runtime("id == null");
					}

					final Object o = UConfig.jpa().findById(type, id);
					if (o != null) {
						return o;
					}
				}
			}

		} catch (final Exception e) {
			if ("id == null".equals(e.getMessage())) {
				throw UException.runtime(e);
			}
		}
		
		try {
			if (UClass.instanceOf(value.getClass(), type)) {
				return value;
			}
		} catch (final Exception e) {}
		
		try {
			return UType.cast(value, type);
		} catch (final Exception e) {}
		
		try {
			final Atributos atributos = ListAtributos.get(type);
			final Atributo idAtributo = atributos.getId();
			final Object id = idAtributo.get(value);
			final Object o = UConfig.jpa().findById(type, id);
			if (o != null)
				return o;
		} catch (final Exception e) {}
		
		try {
			final Object o = UConfig.jpa().findById(type, UInteger.toInt(value) );
			if (o != null)
				return o;
		} catch (final Exception e) {}
		
		try {
			final Object o = UConfig.jpa().findById(type, value);
			if (o != null)
				return o;
		} catch (final Exception e) {}

		throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel resolver o valor: " + type.getSimpleName() + "." + atributo.nome() + " >> " + value);
	}
	private Object resolveValue(final String fieldName, final Object value) {
		if (!fieldName.contains(".")) {
			final Atributo atributo = this.as().getObrig(fieldName);
			return Criterio.resolveValue(atributo, value);
		}
		final ListString list = ListString.byDelimiter(fieldName, ".");
		list.removeEmptys();
		
		Atributos atributos = this.as();
		
		Atributo field = null;
		while (!list.isEmpty()) {
			field = atributos.get(list.remove(0));
			if (field == null) {
				throw UException.runtime("field == null >> " + fieldName + " >> " + value);
			}
			if (UType.isPrimitiva(field.getType())) {
				if (!list.isEmpty()) {
					throw new RuntimeException("???");
				}
			} else {
				atributos = ListAtributos.get(field.getType());
			}
		}
		return Criterio.resolveValue(field, value);
	}
	private QueryOperator_Menor restriction_menor(final String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_Menor(field, value);
	}
	private QueryOperator_Maior restriction_maior(final String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_Maior(field, value);
	}
//	private QueryOperator restriction_isNull(Atributo atributo) {
//		return restriction_isNull(atributo.nome());
//	}
	private QueryOperator restriction_isNull(final String field) {
		return new QueryOperator_isNull(field);
	}
	private QueryOperator restriction_isNotNull(final String field) {
		return new QueryOperator_isNull(field).not();
	}
	private QueryOperator restriction_eq(final String field, Object value) {
		if (UObject.isEmpty(value)) {
			return this.restriction_isNull(field);
		}
		value = this.resolveValue(field, value);
		if (value instanceof String) {
			return new QueryOperator_StringEquals(field, (String) value);
		}
		return new QueryOperator_eq(field, value);
	}
//	private QueryOperator restriction_eq(Atributo atributo, Object source) {
//		try {
//			if (!UClass.isInstanceOf(source, atributo.getType())) {
//				source = atributo.get(source);
//			}
//			if (source == null) {
//				return restriction_isNull(atributo);
//			}
//			source = resolveValue(atributo, source);
//			return restriction_eq(atributo.nome(), source);
//		} catch (Exception e) {
//			throw UException.runtime(e);
//		}
//	}
	private QueryOperator restriction_ne(final String field, final Object value) {
		if (UObject.isEmpty(value)) {
			return this.restriction_isNotNull(field);
		}
		return this.restriction_eq(field, value).not();
	}
	protected static void add(final List<Criterion> expressions, final Object criterion) {
		if (criterion instanceof SimpleExpression) {
			expressions.add((SimpleExpression) criterion);
			return;
		}
		if (criterion instanceof Disjunction) {
			final Disjunction disjunction = (Disjunction) criterion;
			final Iterable<Criterion> conditions = disjunction.conditions();
			for (final Criterion c2 : conditions) {
				Criterio.add(expressions, c2);
			}
			return;
		}
		if (criterion instanceof NullExpression) {
			expressions.add((NullExpression) criterion);
			return;
		}
		if (criterion instanceof NotNullExpression) {
			expressions.add((NotNullExpression) criterion);
			return;
		}
		if (criterion instanceof InExpression) {
			expressions.add((InExpression) criterion);
			return;
		}
		if (criterion instanceof LikeExpression) {
			expressions.add((LikeExpression) criterion);
			return;
		}
		if (criterion instanceof org.hibernate.criterion.PropertyExpression) {
			expressions.add((org.hibernate.criterion.PropertyExpression) criterion);
			return;
		}
		if (criterion instanceof Conjunction) {
			final Conjunction conjunction = (Conjunction) criterion;
			final Iterable<Criterion> conditions = conjunction.conditions();
			for (final Criterion c2 : conditions) {
				Criterio.add(expressions, c2);
			}
			return;
		}
		if (criterion instanceof PropertySubqueryExpression) {
			throw UException.runtime("PropertySubqueryExpression N"+UConstantes.a_til+"o implementado");
		}
		if (criterion instanceof NotExpression) {
			expressions.add((NotExpression) criterion);
			return;
//			throw UException.runtime("NotExpression N"+UConstantes.a_til+"o implementado");
		}
		if (criterion instanceof org.hibernate.criterion.SQLCriterion) {
			return;
//			throw UException.runtime("SQLCriterion N"+UConstantes.a_til+"o implementado");
		}
		String erro = "ERRO: Criterio.add(List<Criterion> expressions, Object criterion):";
		erro += " Nao sei tratar este tipo de expressao: " + criterion.getClass();
		ULog.debug(erro);
		throw UException.runtime(erro);
	}
	public List<Criterion> getExpressions() {
		return CriterioSetAlias.getExpressions(this.newCriteria());
	}

	private ListString aliases;

	private void setAlias(final Criteria c) {
		this.aliases = new ListString();
		CriterioSetAlias.set(c, this.aliases, this.orderBy);
	}

	private List<Order> orderBy;

//	private void addOrder(Order order) {
//		if (orderBy == null) {
//			orderBy = new ArrayList<>();
//		}
//		orderBy.add(order);
//	}

	private Criterio<T> addOrder(String campo, final boolean asc) {
		
//		campo = CriterioSetAlias.putAliasInEL(campo);
		
		/* evita que a mesma coluna seja posta no orderby + de 1 vez.
		 * quando isso ocorre dah um erro no sqlserver
		 *  */
		if (this.orderBy != null) {
			for (final Order orders : this.orderBy) {
				if (orders.getPropertyName().equals(campo)) {
					return this;
				}
			}
		}
		
		Order order;
		
//		TODO FIXME ver como resolver o problema de NullPrecedence no sqlServer 
		
		if (asc) {
			order = Order.asc(campo);
//			order.nulls(NullPrecedence.FIRST);
		} else {
			order = Order.desc(campo);
//			order.nulls(NullPrecedence.LAST);
		}
		
		if (this.orderBy == null) {
			this.orderBy = new ArrayList<>();
		}
		this.orderBy.add(order);
		
		return this;
	}
	public void addOrderAsc(final ListString list) {
		for (final String s : list) {
			this.addOrder(s, true);
		}
	}
	public Criterio<T> addOrderAsc(final String... campos) {
		final ListString list = ListString.newFromArray(campos);
		this.addOrderAsc(list);
		return this;
	}
	public Criterio<T> desc(final String... campos) {
		return this.addOrderDesc(campos);
	}
	public Criterio<T> addOrderDesc(final String... campos) {
		for (final String campo : campos) {
			this.addOrder(campo, false);
		}
		return this;
	}
	public static List<Order> getOrders(final Criteria c) {
		@SuppressWarnings("unchecked")
		final
		List<OrderEntry> list = (List<OrderEntry>) Criterio.atributosCriteria.get("orderEntries").get(c);
		final List<Order> expressions = new ArrayList<>();
		for (final OrderEntry entry : list) {
			final Order o = entry.getOrder();
			expressions.add(o);
		}
		return expressions;
	}
	public Criterio<T> id(final Object o) {
		this.eq(this.getId(), o);
		return this;
	}
	public T porId(final Object id) {
		if (id == null) {
			return null;
		}
		return this.id(id).listUnique();
	}
	
	List<List<QueryOperator>> addsBack = new ArrayList<>();
	
	List<QueryOperator> adds = new ArrayList<>();
	public List<QueryOperator> getAdds() {
		return this.adds;
	}
	private Criterio<T> backIfOr() {
		final QueryOperator last = UList.getLast(this.adds);
		if (last instanceof QueryOperator_or) {
			final QueryOperator_or o = (QueryOperator_or) last;
			if (o.getRigth() == null) {
				this.adds.remove(last);
				final QueryOperator left = o.getLeft();
				this.add(left);
			}
		}
		return this;
	}
	public Criterio<T> or(){
		
		if (this.adds.isEmpty()) {
			throw UException.runtime("Nada para comparar");
		}
		
		final QueryOperator last = UList.getLast(this.adds);
		
		if (last instanceof QueryOperator_or) {
			final QueryOperator_or o = (QueryOperator_or) last;
			if (o.getRigth() == null) {
				throw UException.runtime("Or j"+UConstantes.a_agudo+" aberto");
			}
		}
		
		final QueryOperator_or or = new QueryOperator_or();
		or.setLeft(last);
		this.adds.remove(last);
		this.add(or);
		return this;
	}

	private Criterio<T> add(final QueryOperator criterion) {
		if (criterion == null) {
			return this;
		}
		final QueryOperator last = UList.getLast(this.adds);
		
		if (last != null && last instanceof QueryOperator_or) {
			final QueryOperator_or or = (QueryOperator_or) last;
			if (or.getRigth() != null) {
				this.adds.add(criterion);
				return this;
			} else {
				or.setRigth(criterion);
			}
		} else {
			this.adds.add(criterion);
		}
		
		return this;
	}
	
	public void abreParenteses() {
		this.addsBack.add(this.adds);
		this.adds = new ArrayList<>();
	}
	public boolean fechaParenteses() {
		if ( this.addsBack.isEmpty() ) {
			return false;
		}
		QueryOperator_and and = null;
		if (!this.adds.isEmpty()) {
			and = new QueryOperator_and();
			for (final QueryOperator o : this.adds) {
				and.add(o);
			}
		}
		this.adds = UList.removeLast( this.addsBack );
		if (and != null) {
			this.add(and);
		}
		return true;
	}
	
	public boolean exists() {
		return this.count() > 0;
	}
	public T last() {
		return this.limit(1).desc(this.getId().nome()).unique();
	}
	public T first() {
		return this.limit(1).unique();
	}
	public T aleatorio() {
		// TODO implementar o count
		return this.page(Aleatorio.get(1, 100)).limit(1).unique();
	}
	public Criterio<T> eqAnoMes(final String campo, final Calendar data) {
		return this.eqAnoMes(campo, new Data(data));
	}
	public Criterio<T> eqAnoMes(final String campo, Data data) {
		data = data.copy();
		data.setDia(1);
		data.zeraTime();
		this.maiorOuIgual(campo, data.getCalendar());
		data.addMes();
		this.menor(campo, data.getCalendar());
		return this;
	}
	public Criterio<T> eqAnoMesDia(final String campo, Data data){
		data.zeraTime();
		data = data.copy();
		data.zeraTime();
		maiorOuIgual(campo, data.getCalendar());
		data = data.copy();
		data.add();
		menor(campo, data.getCalendar());
		return this;
	}
	public Criterio<T> notEqAnoMesDia(final String campo, Data data){
		data.zeraTime();
		data = data.copy();
		data.zeraTime();
		menor(campo, data.getCalendar());
		data = data.copy();
		data.add();
		or();
		maiorOuIgual(campo, data.getCalendar());
		return this;
	}
	public Criterio<T> isHoje(final String campo) {
		return eqAnoMesDia(campo, Data.now());
	}
	public Criterio<T> isNotHoje(final String campo) {
		return notEqAnoMesDia(campo, Data.now());
	}
	public Criterio<T> between(final Calendar d, final String campo1, final String campo2) {
		this.menorOuIgual(campo1, d);
		this.maiorOuIgual(campo2, d);
		return this;
	}
	public void delete() {
		UConfig.jpa().delete(this.list());
	}
	public Criterio<T> noLimit() {
		return this.limit(-1);
	}
	public Criterio<T> busca(String text) {
		text = UString.toCampoBusca(text);
		return this.like("busca", text);
	}

//	private boolean considerarCompartilhamento = true;

//	public Criterio<T> considerarCompartilhamento() {
//		considerarCompartilhamento = true;
//		return this;
//	}
	@Override
	public String toString() {
		return this.newCriteria().toString();
	}
	
	public String getSql(){
		return this.getQueryNativa().getSql();
	}
	public MontarQueryNativa getQueryNativa() {
		final MontarQueryNativa m = new MontarQueryNativa(this.getClasse());
		m.setLimit( this.getLimit() );
		m.setOffSet( this.offSet() );
		m.setOps( this.getAdds() );
		m.setOrders( this.getOrderBy() );
		return m;
	}
	
//	public String getSql2(){
//		
//		Criteria c0 = newCriteria();
//		setOrderBy(c0);
//		
//		CriteriaImpl criteriaImpl = (CriteriaImpl) c0;
//		SessionImplementor session = criteriaImpl.getSession();
//		SessionFactoryImplementor factory = session.getFactory();
//		CriteriaQueryTranslator translator=new CriteriaQueryTranslator(factory,criteriaImpl,criteriaImpl.getEntityOrClassName(),CriteriaQueryTranslator.ROOT_SQL_ALIAS);
//		String[] implementors = factory.getImplementors( criteriaImpl.getEntityOrClassName() );
//
//		CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable)factory.getEntityPersister(implementors[0]), 
//		                        translator,
//		                        factory, 
//		                        criteriaImpl, 
//		                        criteriaImpl.getEntityOrClassName(), 
//		                        session.getLoadQueryInfluencers()   );
//
//		String sql=walker.getSQLString();		
//		return sql;
//		
//	}
	
	public int offSet() {
		
		if (this.skip > 0) {
			return this.skip;
		}
		
		final int p = this.getPage();
		if (p < 2) {
			return 0;
		}
		return (this.page-1)*30;
	}	
	public void addNative(final String sql) {
		final QueryOperator_NativeSql nativeSql = new QueryOperator_NativeSql(sql);
		this.add(nativeSql);
	}

}
