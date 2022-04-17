package gm.utils.jpa.constructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.br.CPF;

import gm.utils.abstrato.IdObject;
import gm.utils.classes.UClass;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UCpf;
import gm.utils.comum.UExpression;
import gm.utils.comum.UObject;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.jpa.NativeSelectMap;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.jpa.select.SelectBase;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Setter;

//@Getter @Setter
public abstract class Pojo {
	
	private List<?> itens;
	private SelectBase<?,?,?> selectBase;
	String prefixo;
	List<PojoCampo> campos;
	
	@Setter	
	private boolean formatarValores;

	@Setter	
	private boolean formatarPercentuais;
	
	@SuppressWarnings("unused")
	private Pojo pai;
//	private boolean booleanFormatado; 

	public Pojo(Pojo pai, String prefixo){
		this.pai = pai;
		this.itens = pai.itens;
		this.campos = pai.campos;
		this.prefixo = pai.prefixo + prefixo + ".";
	}
	
	public Pojo(List<?> itens) {
		this.itens = itens;
		this.prefixo = "";
		this.pai = this;
		this.campos = new ArrayList<>();
	}
	
	public Pojo(SelectBase<?,?,?> selectBase) {
		this.selectBase = selectBase;
		this.prefixo = "";
		this.pai = this;
		this.campos = new ArrayList<>();
	}
	
	public Pojo(Object o) {
		this.prefixo = "";
		this.pai = this;
		this.campos = new ArrayList<>();
		List<Object> list = new ArrayList<>();
		list.add(o);
		this.itens = list;
	}

//	public Pojo sequencial(){
//		campos.add("sequencial");
//		return this;
//	}
//	public Pojo aux(String alias){
//		campos.add("aux");
//		getAliases().put("aux", alias);
//		return this;
//	}
	
	public Pojo add(String campo) {
		return add(campo, null, null);
	}
	public Pojo add(Atributo a, FTT<String, Object> function) {
		return add(a.nome(), function);
	}
	public Pojo add(String campo, FTT<String, Object> function) {
		return add(campo, null, function);
	}
	public Pojo add(String campo, String alias) {
		return add(campo, alias, null);
	}
	public void add(Atributo a) {
		add(a.nome());
	}
//	public Pojo asStatus(String alias) {
//		return add(campo, alias, null);
//	}
	
	public void remove(String campo) {
		Predicate<PojoCampo> filter = new Predicate<PojoCampo>(){
			@Override
			public boolean test(PojoCampo o) {
				return o.getNome().equalsIgnoreCase(campo);
			}
		};
		campos.removeIf(filter);
	}
	
	public Pojo add(String campo, String alias, FTT<String, Object> function) {
		PojoCampo o = new PojoCampo();
		o.setNome(prefixo+campo);;
		campos.removeIf(new PojoRemoveCampo(o.getNome()));
		o.setFunction(function);
		
		if (UString.isEmpty(alias)) {
			
			if (o.getNome().contains(".")) {

				ListString split = ListString.split(o.getNome(), ".");
				
				split.primeirasMaiusculas();
				
				alias = "";
				
				for (String s : split) {
					
					if (s.endsWith("()")) {
						
						s = UString.beforeLast(s, "()");

						String before;
						
						if (s.contains(".")) {
							before = UString.beforeLast(s, ".") + ".";
							s = UString.afterLast(s, ".");
						} else {
							before = "";
						}
						
						if (s.startsWith("Get")) {
							s = UString.afterFirst(s, "Get");
						}
						
						s = before + s;
						
					}
					
//					s = S.primeiraMaiuscula(s);
					alias += s;
					
				}
				
				alias = UString.primeiraMinuscula(alias);				
				
			} else {
				alias = o.getNome();
			}
			
		}
		
		o.setAlias(alias);
		
		campos.add(o);
		return this;
	}
	
	private static Atributo getAtributo(String campo, Class<?> classe){
		Atributos as = ListAtributos.get(classe);
		if (!campo.contains(".")) {
			return as.get(campo);
		}
		String before = UString.beforeFirst(campo, ".");
		Atributo atributo = as.get(before);
		campo = UString.afterFirst(campo, ".");
		return getAtributo(campo, atributo.getType());
	}
	
	public MapSO unique() {
		List<MapSO> list = list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	protected static final String NAO_ENCONTRADO = "([!>NAO_ENCONTRADO<!])";
	
	public List<MapSO> list() {
		
		List<MapSO> result = new ArrayList<>();
		
		if (itens == null) {
			return montaItens();
		}
		
		if (itens.isEmpty()) {
			return result;
		}

		Class<?> classe = UClass.getClass(itens.get(0));
		
		int sequencial = 0;
		for (Object o : itens) {
			MapSO map = new MapSO();
			for (PojoCampo campo : campos) {
				
				String nome = campo.getNome();
				
				if (campo.getFunction() != null) {
					String value = campo.getFunction().call(o);
					map.put(campo.getAlias(), value);
					continue;
				}
				
				
				String gerado = getGerado(o, nome);
				
				if (!NAO_ENCONTRADO.equals(gerado)) {
					map.put(campo.getAlias(), gerado);
					continue;
				}
				
				if (nome.equals("sequencial")) {
					map.put("sequencial", ++sequencial);
					continue;
				}

				Object value;
				
				if (nome.contains("!avatar")) {
					nome = UString.beforeFirst(nome, "!");
					value = UExpression.getValue(nome, o);
					if (value != null) {
//							Entidade<?> entity = (Entidade<?>) value;
						throw UException.runtime("TODO");
//							value = FwBaseConfig.get().getUrlAvatar(entity, null);
					}
				} else {
					
					value = UExpression.getValue(nome, o);
					
					if ( nome.endsWith("()") ) {
//						TODO desenvolver
						throw UException.runtime("implementar: " + nome);
					} else {
						Atributo a = getAtributo(nome, classe);
						if (a != null) {
							value = format(a, value);
						}
					}
						
				}					
				
				if (!UObject.isEmpty(value)) {
					map.put(campo.getAlias(), value);
				}
				
			}
			result.add(map);
		}

		return result;

	}
	
	protected abstract String getGerado(Object o, String nome);

	private List<MapSO> montaItens() {
		
		ListString colNames = new ListString();
		
		Map<String, String> select = new HashMap<>();
		for (PojoCampo campo : campos) {
			select.put(campo.getNome(), campo.getAlias());
			colNames.add(campo.getAlias());
		}
		Criterio<?> c = selectBase.getC();
		MontarQueryNativa qn = c.getQueryNativa();
		qn.setSelect(select);
		qn.getResult().saveTemp();
		String sql = qn.getResult().join("\n");
		NativeSelectMap selectMap = new NativeSelectMap(sql);
		return selectMap.map();
		
	}

	private Object format(Atributo a, Object x) {

		if ( UObject.isEmpty(x) ) {
			return null;
		}
		
		if (a.nome().equals("aux")) {
			if ( x.getClass() == boolean.class || x.getClass() == Boolean.class ) {
				return UBoolean.isTrue(x);
			}
		}

		if ( a.isBoolean() ) {
//			if (booleanFormatado) {
//				if (U.isTrue(x)) {
//					return "Sim";
//				} else {
//					return "N"+UConstantes.a_til+"o";
//				}
//			} else {
			return UBoolean.isTrue(x);
//			}
		}

		if (a.isDate()) {
			
			Data data = Data.to(x);
			
			Class<?> fwDia = UClass.getClass("gm.fw.model.Dia");
			
			if (fwDia != null && a.is(fwDia)) {
				return data.format("[ddd] [dd]/[mm]/[yyyy]");
			}
			
			Temporal temporal = a.getAnnotation(Temporal.class);
			if (temporal.value().equals(TemporalType.TIMESTAMP)) {
				return data.format("[ddd] [dd]/[mm]/[yyyy] [hh]:[nn]");
			} else {
				return data.format("[ddd] [dd]/[mm]/[yyyy]");
			}
			
		}
		
		if ( a.isString() ) {
			CPF cpf = a.getAnnotation(CPF.class);
			if (cpf != null) {
				return UCpf.format(UString.toString(x));
			}
			return UString.toString(x);
		}
		
		if (a.isFk()) {
			if (x instanceof Integer) {
				return x;
			}
			IdObject io = (IdObject) x;
			return io.getId();
		}

		Class<?> type;
		
		if (a.nome().equals("aux")) {
			type = x.getClass();
		} else {
			type = a.getType();
		}
		
		if ( type.equals(Integer.class) ) {
			return x;
//			return U.toString(x);	
		}
		
		if ( type.equals(BigDecimal.class) ) {
			BigDecimal b = (BigDecimal) x;
			int fraction = a.getAnnotation(Digits.class).fraction();
			
			Numeric<?> n;
			
			if (fraction == 1) {
				n = new Numeric1(b);
			} else if (fraction == 2) {
				n = new Numeric2(b);
			} else {
				n = new Numeric<>(b, fraction);
			}
			
			if (formatarValores) {
				if (n.isZero()) {
					return "";
				} else {
					return "R$ " + n.toString();
				}
			} else {
				return new Numeric<>(b, fraction).toDouble();
			}
			
		}
		
		return UString.toString(x);
		
	}

	public static Pojo get(Class<?> classe, List<?> list) {
		String s = "auto."+classe.getSimpleName() + "Pojo";
		Class<? extends Pojo> classePojo = UClass.getClass(s);
		if (classePojo == null) {
			throw UException.runtime("classePojo == null: " + s);
		}
		return UClass.newInstance(classePojo, list);
	}
	
	public abstract Pojo all();

	public Pojo subPojo(Atributo a){
		throw UException.runtime("Nenhuma FK na classe");
	}

}
