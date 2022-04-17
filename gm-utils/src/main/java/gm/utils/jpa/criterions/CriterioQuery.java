package gm.utils.jpa.criterions;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Order;

import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.date.Cronometro;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import lombok.Getter;

@Getter
public class CriterioQuery<T> {
	
	private final Criterio<?> co;
	private final Root<?> from;
	private final CriteriaBuilder cb;
	private final CriteriaQuery<T> cq;
	private From<?, ?> path;
//	private Class<T> classe;

	public CriterioQuery(Criterio<?> criterio, Class<T> classe) {
		this.co = criterio;
//		this.classe = classe;
		this.cb = criterio.em.getCriteriaBuilder();
		this.cq = cb.createQuery(classe);
		from = cq.from(criterio.getClasse());
		addWheres();
	}
	
	private void addWheres() {
		co.beforeList();
		Predicate[] predicates = new Predicate[co.adds.size()];
		int i = 0;
		for (QueryOperator o : co.adds) {
			predicates[i++] = o.getPredicate(this);
		}		
		cq.where(predicates);
	}
	
	@SuppressWarnings("unchecked")
	public <X> Lst<X> distinct(String campo) {
		campo = trataCampo(campo);
		cq.select(path.get(campo));
		cq.distinct(true);
		List<X> resultList = (List<X>) co.em.createQuery(cq).getResultList();
		return new Lst<>(resultList);
	}

	public String trataCampo(String campo) {
		this.path = from;
		if (campo.contains(".")) {
			ListString campos = ListString.byDelimiter(campo, ".");
			campo = campos.removeLast();
			for (String s : campos) {
				path = path.join(s, JoinType.LEFT);	
			}
		}
		return campo;
	}

	@SuppressWarnings("unchecked")
	public int count() {
		Expression<T> count = (Expression<T>) cb.count(from);
		cq.select(count);
		Long o = (Long) co.em.createQuery(cq).getSingleResult();
		return o.intValue();
	}

	public CriterioResult<T> result() {
		
		if (!UList.isEmpty(co.getOrderBy())) {
			
			for (final Order order : co.getOrderBy()) {
				String campo = order.getPropertyName();
				From<?, ?> path = from;
				if (campo.contains(".")) {
					ListString campos = ListString.byDelimiter(campo, ".");
					campo = campos.removeLast();
					for (String s : campos) {
						path = path.join(s, JoinType.LEFT);	
					}
				}
				if (order.isAscending()) {
					cq.orderBy(cb.asc(path.get(campo)));
				} else {
					cq.orderBy(cb.desc(path.get(campo)));
				}
			}
		}
		
		TypedQuery<T> query = co.em.createQuery(cq);
		if (co.getLimit() > 0) {
			query.setMaxResults(co.getLimit());
			if (co.getPage() == 0) {
				co.setPage(1);
			}
			final int firstResult = co.getSkip() + (co.getPage() - 1) * co.getLimit();
			if (firstResult < 0) {
				throw UException.runtime("firstResult < 0");
			}
			query.setFirstResult(firstResult);
		} else if (co.getLimit() != -1) {
			query.setFirstResult(co.getSkip());
			query.setMaxResults(500);
		}
		
		final Cronometro cronometro = new Cronometro();
		final List<T> list = query.getResultList();
		if (cronometro.tempo() > 5000) {
			UException.runtime("Consulta demorou: " + cronometro.tempo() + " >> " + cq.toString());
		}

		final CriterioResult<T> result = new CriterioResult<>();
		result.s = cq.toString();
		result.list = new Lst<>(list);
		return result;
	}
	
}
