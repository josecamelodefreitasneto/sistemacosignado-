package br.impl.outros;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import gm.utils.abstrato.IdObject;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UCpf;
import gm.utils.comum.UNomeProprio;
import gm.utils.email.UEmail;
import gm.utils.exception.MessageException;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromObject;
import gm.utils.number.Numeric;
import gm.utils.number.UInteger;
import gm.utils.outros.UTelefone;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

public abstract class ServiceModelo<T extends EntityModelo> {
	
	public static final Map<String, String> CONSTRAINTS_MESSAGES = new HashMap<>();
	
	@Autowired @Getter EntityServiceBox entityServiceBox; 

	public abstract Class<T> getClasse();
	public abstract MapSO toMap(T o, boolean listas);
	public abstract int getIdEntidade();
	
	public T fromMap2(MapSO map) {return fromMap(map);}
	
	protected T fromMap(MapSO map) {return null;}
	protected T setOld(T o) {return o;}
	protected boolean houveMudancas(T o) {return false;}
	public abstract boolean auditar();
	public boolean utilizaObservacoes() {
		return true;
	}
	protected void registrarAuditoriaInsert(T o) {}
	protected void registrarAuditoriaUpdate(T o) {}
	protected void registrarAuditoriaDelete(T o) {}
	protected void registrarAuditoriaUndelete(T o) {}
	protected void registrarAuditoriaBloqueio(T o) {}
	protected void registrarAuditoriaDesbloqueio(T o) {}
	protected void validar(T o) {
		throw new RuntimeException("???");
	}
	protected void validar2(T o) {}
	protected void validar3(T o) {}
	
	public T newO() {
		throw new RuntimeException("???");
	}

	public List<T> findAll() {
		return getEntityServiceBox().findAll(getClasse());
	}
	public T findByKey(String s) {
		throw new RuntimeException("!implementado");
	}
	public T find(int id) {
		final T o = findNotObrig(id);
		if (o == null) {
			throw new RuntimeException("Nao encontrado: " + getClasse().getSimpleName() + " (" + id + ")");
		}
		return setOld(o);
	}
	public T findNotObrig(int id) {
		return getEntityServiceBox().find(getClasse(), id);
	}
	public boolean exists(int id) {
		return findNotObrig(id) != null;
	}
	
	public MapSO toMap(int id, boolean listas) {
		return toMap(find(id), listas);
	}

	public void validar(MapSO map) {
		T o = fromMap(map);
		if (o.getId() == null) {
			validaInsert(o);
		} else {
			validaUpdate(o);
		}
	}

	@Transactional
	public MapSO saveAndMap(MapSO map) {
		return toMap(save(map), true);
	}

	@Transactional
	public T save(MapSO map) {
		T o = fromMap(map);
		o.setIgnorarUniquesAoPersistir(UBoolean.isTrue(map.getBoolean("ignorarUniquesAoPersistir")));
		o = save(o);
		saveListas(o, map);
		if (utilizaObservacoes()) {
			saveObservacoes(o, map);
		}
		return o;
	}
	public ResultadoConsulta consulta(MapSO map) {
		throw new RuntimeException("???");
	}
	protected void saveListas(T o, MapSO map) {}
	protected void saveObservacoes(T o, MapSO map) {}

	public T insert(T o) {
		o = insertSemAuditoria(o);
		if (auditar()) {
			registrarAuditoriaInsert(o);
		}
		return o;
	}
	private void validaInsert(T o) {
		beforeInsert(o);
		validar(o);
		beforeInsertAfterValidate(o);
	}
	protected T insertSemAuditoria(T o) {
		validaInsert(o);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		setBusca(o);
		persistInsert(o);
		setOld(o);
		afterInsert(o);
		return o;
	}
	protected void persistInsert(T o) {
		getEntityServiceBox().persist(o);		
	}
	@Transactional
	public T save(T o) {
		if (o.getId() == null) {
			return insert(o);
		} else {
			return update(o);
		}
	}
	
	private T update(T o) {
		validaUpdate(o);
		o = merge(o);
		afterUpdate(o);
		if (auditar()) {
			registrarAuditoriaUpdate(o);
		}
		return o;
	}
	
	private void validaUpdate(T o) {
		if (o.getOld() == null) {
			throw new RuntimeException("Uma entidade somente pode ser alterada se tiver passado por ServiceModelo.find");
		}
		if (o.getRegistroBloqueado()) {
			throw new RuntimeException("O registro não pode ser alterado pois está bloqueado: " + getClasse().getSimpleName() + " (" + o.getId() + ")");
		}
		beforeUpdate(o);
		validar(o);
	}
	@Transactional
	public T delete(int id) {
		return delete(find(id));
	}
	public T delete(T o) {
		beforeDelete(o);
		o.setExcluido(true);
		o = merge(o);
		if (auditar()) {
			registrarAuditoriaDelete(o);
		}
		afterDelete(o);
		return o;
	}
	
	public T undelete(T o) {
		o.setExcluido(false);
		o = merge(o);
		if (auditar()) {
			registrarAuditoriaUndelete(o);
		}
		return o;
	}
	
	private T merge(T o) {
		setBusca(o);
		return getEntityServiceBox().merge(o);		
	}
	public final T bloqueiaRegistro(T o) {
		o.setRegistroBloqueado(true);
		o = merge(o);
		if (auditar()) {
			registrarAuditoriaBloqueio(o);
		}
		return o;
	}
	public final T desbloqueiaRegistro(T o) {
		o.setRegistroBloqueado(false);
		o = merge(o);
		if (auditar()) {
			registrarAuditoriaDesbloqueio(o);
		}
		return o;
	}
	
	protected void beforeInsert(T o) {}
	protected void beforeInsertAfterValidate(T o) {}
	protected void beforeUpdate(T o) {}
	protected void beforeDelete(T o) {}
	protected void afterInsert(T o) {}
	protected void afterUpdate(T o) {}
	protected void afterDelete(T o) {}
	protected void setBusca(T o) {}
	public String getText(T o) {return null;}
	
	public String getText(Integer id) {
		if (id == null) {
			return null;
		} else {
			return getText(find(id));
		}
	}

	protected Criterio<T> criterio() {
		return getEntityServiceBox().criterio(getClasse());
	}
	
	protected final BigDecimal validaDecimal(BigDecimal value, int integers, int decimais, boolean nullIfZero) {
		if (value == null) return null;
		if (value.doubleValue() == 0) {
			if (nullIfZero) {
				return null;
			} else {
				return BigDecimal.ZERO;
			}
		}
		final String s = value.toString();
		if (!s.contains(".")) {
			if (UString.length(s) > integers) {
				throw new RuntimeException("??? " + s);
			}
		}
		if (UString.length(UString.beforeFirst(s, ".")) > integers) {
			throw new RuntimeException("??? " + s);
		}
		if (UString.length(UString.afterFirst(s, ".")) > decimais) {
			throw new RuntimeException("??? " + s);
		}
		Numeric<?> numeric = Numeric.toNumeric(value, decimais);
		if (nullIfZero && numeric.isZero()) {
			return null;
		} else {
			return numeric.getValor();
		}
	}

	public List<MapSO> toMapList(Lst<T> list) {
		return list.map(item -> toMap(item, true));
	}

	public List<MapSO> toMapList(String campo, Object value) {
		return criterio().eq(campo, value).list().map(item -> toMap(item, false));
	}
	
	/* statics */

//	public static String getText(EntityModelo o) {
//		return o == null ? null : o.getText();
//	}
	public MapSO toIdText(int id) {
		return toIdText(find(id));
	}
	public MapSO toIdText(T o) {
		return new MapSO().add("id", o.getId()).add("text", getText(o));
	}
	protected static int intId(EntityModelo o) {
		return o == null || o.getId() == null ? 0: o.getId();
	}
	protected static boolean isId(EntityModelo o, Integer value) {
		return o == null || o.getId() == null ? value == null : UInteger.equals(o.getId(), value);
	}
	protected static <TT extends EntityModelo> Integer findId(ServiceModelo<TT> manager, MapSO map, String key) {
		TT o = find(manager, map, key);
		return o == null ? null : o.getId();
	}
	protected static <TT extends EntityModelo> TT find(ServiceModelo<TT> manager, MapSO map, String key) {
		
		Object o = map.get(key);
		if (o == null) return null;
		
		Integer id;
		
		if (UInteger.isInt(o)) {
			id = UInteger.toInt(o);
		} else if (o instanceof IdObject) {
			IdObject ido = (IdObject) o;
			id = ido.getId();
		} else {
			
			if (o instanceof String) {
				String s = (String) o;
				if (UString.isEmpty(s)) {
					return null;
				}
				s = s.trim();
				if (!s.startsWith("{")) {
					return manager.findByKey(s);
				}
				o = s;
			}
			
			final MapSO sub = MapSoFromObject.get(o);
			if (sub == null) {
				return null;
			} else {
				id = sub.getInt("id");
				if (id == null) {
					return manager.buscaUnicoObrig(sub);
				}
			}
		}

		if (id == null || id < 1) {
			return null;
		} else {
			return manager.find(id);
		}
		
	}
	
	protected static BigDecimal round(BigDecimal o, int casas, boolean nullIfZero) {
		if (o == null) {
			return null;
		} else {
			Numeric<?> numeric = Numeric.toNumeric(o, casas);
			if (nullIfZero && numeric.isZero()) {
				return null;
			} else {
				return numeric.getValor();
			}
		}
	}
	
	public ResultadoConsulta consultaSelect(MapSO params) {
		return consultaBase(params, o -> {
			final MapSO map = new MapSO();
			map.put("id", o.getId());
			map.put("text", getText(o));
			return map;
		});
	}
	
	protected static String tratarCpf(String s) {
		if (UString.isEmpty(s)) {
			return null;
		} else if (UCpf.isValid(s)) {
			return UCpf.format(s);
		} else {
			throw new MessageException("CPF inválido: " + s);
		}
	}
	protected static String tratarEmail(String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UString.trimPlus(s);
		if (UEmail.isValid(s)) {
			return s;
		} else {
			throw new MessageException("E-mail inválido: " + s);
		}
	}
	protected static String tratarNomeProprio(String s) {
		s = UString.trimPlus(s);
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UNomeProprio.formatParcial(s);
		if (UString.isEmpty(s)) {
			return null;
		} else if (UNomeProprio.isValido(s)) {
			return s;
		} else {
			throw new MessageException("Nome Próprio inválido: " + s);
		}
	}
	protected static String tratarTelefone(String s) {
		s = UString.trimPlus(s);
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UTelefone.formatParcial(s);
		if (UString.isEmpty(s)) {
			return null;
		} else if (UTelefone.isValid(s)) {
			return s;
		} else {
			throw new MessageException("Telefone inválido: " + s);
		}
	}
	protected static String tratarString(String s) {
		s = UString.trimPlus(s);
		if (UString.isEmpty(s)) {
			return null;
		} else {
			return s;
		}
	}
	protected static Integer getId(MapSO map, String key) {
		Object o = map.get(key);
		if (o == null) {
			return null;
		} else if (o instanceof Integer) {
			return (Integer) o;
		} else if (o instanceof MapSO) {
			map = (MapSO) o;
			return map.getInt("id");
		} else if (UInteger.isInt(o)) {
			return UInteger.toInt(o);
		} else {
			throw new RuntimeException("???");
		}
	}
	
	protected abstract T buscaUnicoObrig(MapSO params);
	
	protected ResultadoConsulta consultaBase(MapSO params, FTT<MapSO, T> func) {
		throw new RuntimeException("???");
	}
	
	public ListString getTemplateImportacao() {
		throw new RuntimeException("???");
	}
	
}