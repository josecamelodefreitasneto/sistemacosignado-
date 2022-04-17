package gm.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import gm.utils.anotacoes.Arquivo;
import gm.utils.anotacoes.Imagem;
import gm.utils.anotacoes.Lookup;
import gm.utils.anotacoes.NomeProprio;
import gm.utils.anotacoes.Obrigatorio;
import gm.utils.anotacoes.Ordem;
import gm.utils.anotacoes.SomenteLeitura;
import gm.utils.anotacoes.Status;
import gm.utils.anotacoes.Telefone;
import gm.utils.anotacoes.Titulo;
import gm.utils.anotacoes.Unique;
import gm.utils.anotacoes.UniqueJoin;
import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.UAssert;
import gm.utils.comum.UBoolean;
import gm.utils.comum.UCompare;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UCpf;
import gm.utils.comum.UGenerics;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.email.UEmail;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.SqlNative;
import gm.utils.jpa.UIdObject;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric1;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Atributo {
	
	public Atributo(final Class<?> classe){
		this.classe = classe;
	}
	
	private Field field;
	private Metodo getMethod;
	private Metodo setMethod;
	private Integer scale;
	private String nomeDefault;
	private Integer lengthBanco;
	private Boolean existeNoBanco;
	private Class<?> classe;
	private Object aux;
	private Integer len;
	private List<MapSO> list;
	private Map<String, Object> props;
	public FTT<Object, Object> tryCast;
	
	@SuppressWarnings("unchecked")
	public <T> T getProp(final String key) {
		if (props == null) {
			return null;
		} else {
			return (T) props.get(key);
		}
	}
	public void setProp(final String key, final Object value) {
		if (props == null) {
			if (value == null) {
				return;
			} else {
				props = new HashMap<>();
				props.put(key, value);
			}
		} else if (value == null) {
			props.remove(key);
		} else {
			props.put(key, value);
		}
	}

	public Integer getLength(){
		return this.getLength(null);
	}

	public Integer getLength(final Integer def){
		
		if (len != null) {
			return len;
		}
		
		final Atributo a = getReal();
		
		if (a.is(String.class)) {
			final Column c = a.getAnnotation(Column.class);
			if (c == null) {
				if (def == null) {
					throw new RuntimeException("@Column == null");
				} else {
					len = def;
				}
			} else {
				len = c.length();
			}
		} else if (a.is(Integer.class)) {
			final Max c = a.getAnnotation(Max.class);
			int max;
			if (c == null) {
				max = Integer.MAX_VALUE;
			} else {
				max = UInteger.toInt(c.value(), 0);
			}
			len = UString.toString(max).length();
		} else if (a.is(BigDecimal.class)) {
			len = 10;//999.999,99
		} else if (a.is(Calendar.class)) {
			len = 10;//00/00/0000
		} else if (a.isPrimitivo()) {
			len = 100;
		} else {
			len = ListAtributos.get(a.getType()).getNomeAny().getLength();
		}
		
		return len;
	}
	
	private Atributo real;
	
	public Atributo getReal() {
		if (real == null) {
			if (isLookup()) {
				final Lookup lookup = this.getAnnotation(Lookup.class);
				try {
					Atributo a = ListAtributos.get(getClasse()).get(lookup.vinculo()).getReal();
					ListString campos = ListString.split(lookup.resultado(), ".");
					for (String campo : campos) {
						a = ListAtributos.get(a.getType()).getObrig(campo).getReal();
					}
					real = a;
				} catch (Exception e) {
					throw new RuntimeException("Erro ao tentar ler o atributo real de " + this, e);
				}
			} else {
				real = this;
			}
		}
		return real;
	}
	
	public boolean isLookup() {
		return this.hasAnnotation(Lookup.class);
	}
	public Integer getInt(final Object o, final int def){
		return UInteger.toInt(this.get(o), def);
	}
	public Boolean getBoolean(Object o){
		o = this.get(o);
		if (o == null) return null;
		return UBoolean.isTrue(o);
	}
	public Integer getInt(final Object o){
		return UInteger.toInt( this.get(o) );
	}
	public String getString(final Object o){
		return UString.toString( this.get(o) );
	}

	@SuppressWarnings("unchecked")
	public <T> T get(final Object o){

		if (o == null) {
			throw UException.runtime("o == null");
		}
		
		if (getMethod == null) {
			try {
				final Object value = field.get(o);
				return (T) value;
			} catch (final Throwable e) {
				throw UException.runtime(e);
			}
		}
		
		try {
			return getMethod.invoke(o);
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
		
	}
	public void set(final Object o, Object value){
		
		if (o == null) {
			throw UException.runtime("o == null");
		}
		
		if (UObject.isEmpty(value)) {
			value = null;
		}
		
		if (value != null && tryCast != null) {
			value = tryCast.call(value);
		}
		
		if (value != null) {
			
			final Object x = UType.tryCast(value, getType());
			if (x != null) {
				value = x;
				
				if ( getType().equals(BigDecimal.class) ) {
					final BigDecimal v = (BigDecimal) value;
					final Digits digits = this.getAnnotation(Digits.class);
					
					if (digits != null) {
						final int fraction = digits.fraction();
						if (fraction == 1) {
							value = new Numeric1(v).getValor();
						}
					}
					
				}
			} else if (value instanceof Map) {
				final DO<?> novo = DO.novo(UClass.newInstance(getType()));
				novo.read(value);
				value = novo.object();
			} else {
				if ( UClass.isAbstract( getType() ) ) {
					value = null;
				} else if ( isTransient() || UConfig.get() == null || !UConfig.get().onLine() ) {
					final Object novo = UClass.newInstance(getType());
					final Atributo id = ListAtributos.getId(getType());
					if (id == null) {
						throw UException.runtime("id == null " + getType().getSimpleName());
					}
					id.set(novo, value);
					value = novo;
				} else if ( !isTransient() ) {
					value = Criterio.resolveValue(this, value);	
				}
			}
			
			if (value != null) {
				if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
					value = UInteger.toInt(value);
				} else if (field.getType().equals(Long.class)) {
					value = ULong.toLong(value);
				}
			}
			
		} else {
			if (isPrimitivo()) {
				if (getType().equals(int.class) || getType().equals(long.class)) {
					value = 0;
				}
			}
		}
		
		try {
			
			if (setMethod == null) {
				field.set(o, value);
			} else {
				setMethod.invoke(o, value);
			}
			
		} catch (final Throwable e) {
			String s = "";
			if (value != null) {
				s = value.getClass().getSimpleName();
			}
			s = this + " <set> " + s + " " + value + "\n";
			ULog.debug(s);
			throw UException.runtime(e);
		}
	}
	
	private String toStringResolved;
	
	
	@Override
	public String toString() {
		if (toStringResolved != null) {
			return toStringResolved;
		}
		toStringResolved = getType().getSimpleName() + " ";
		toStringResolved += classe.getSimpleName() + ".";
		toStringResolved += field.getName() + " >> ";
		toStringResolved += getColumnName();
		return toStringResolved;
	}
	
	public boolean isId(){
		
		if ( isTransient() ) return false;
		if ( isStatic() ) return false;
		if ( this.hasAnnotation(Id.class) ) return true;
		
		final Field field = getField();
		
		if (field != null) {
			
			if ( field.getName().equals("id") ) {
				return true;
			}
			if ( field.getName().equals("id" + field.getClass().getSimpleName()) ) {
				return true;
			}
			
		}
		
		return false;
//		return haveAnnotation(Id.class) || haveAnnotation(PrimaryKeyJoinColumn.class);
	}
	
	private Boolean isNome;
	
	public boolean isNome(){
		
		if (isNome != null) return isNome;
		
		if ( isStatic() ) {
			isNome = false;
			return false;
		}
		if ( this.hasAnnotation(NomeProprio.class) ) {
			isNome = true;
			return true;
		}
		
		final Field field = getField();
		
		if (field != null) {
			String name = field.getName().toLowerCase();
			if (name.startsWith("ds")) {
				name = name.substring(2);
			}
			if ( name.equalsIgnoreCase("nome") ) {
				isNome = true;
				return true;
			}
			if ( name.equalsIgnoreCase("nome" + field.getClass().getSimpleName()) ) {
				isNome = true;
				return true;
			}
			if ( name.equalsIgnoreCase("text") ) {
				isNome = true;
				return true;
			}
		}
		
		isNome = false;
		return false;
		
	}
	
	public String getColumnName(){

		String s = null;
		
		final Column column = getColumn();
		if (column != null && !UString.isEmpty( column.name() )) {
			s = column.name();
		} else {
			final JoinColumn join = this.getAnnotation(JoinColumn.class);
			if (join != null && !UString.isEmpty( join.name() ) ) {
				s = join.name();
			} else {
				final PrimaryKeyJoinColumn keyJoin = this.getAnnotation(PrimaryKeyJoinColumn.class);
				if (keyJoin!= null && !UString.isEmpty( keyJoin.referencedColumnName() )) {
					s = keyJoin.referencedColumnName();
				} else {
					return field.getName();			
				}
			}
			
		}
		
		if (s.equalsIgnoreCase( field.getName() )) {
			return field.getName();	
		} else {
			return s;
		}
		
	}
	public Column getColumn(){
		return this.getAnnotation(Column.class);
	}
	public boolean hasAnnotation(final String nome) {
//		if (nome.equals("Setter")) {
//			return lombokSetter();
//		} else if (nome.equals("Getter")) {
//			return lombokGetter();
//		} else {
		return this.getAnnotation(nome) != null;
//		}
	}
	public boolean hasAnnotation(final Class<? extends Annotation> annotation) {
		return hasAnnotation(annotation, true);
	}
	public boolean hasAnnotation(final Class<? extends Annotation> annotation, boolean buscarNaClasse) {
//		if (annotation == Setter.class) {
//			return this.lombokSetter();
//		} else if (annotation == Getter.class) {
//			return this.lombokGetter();
//		} else {
		return this.getAnnotation(annotation, buscarNaClasse) != null;
//		}
	}
	public boolean hasAnnotation(final Class<? extends Annotation> a, final Class<? extends Annotation> b) {
		return this.hasAnnotation(a) || this.hasAnnotation(b);
	}
	public boolean hasAnnotation(final Class<? extends Annotation> a, final Class<? extends Annotation> b, final Class<? extends Annotation> c) {
		return this.hasAnnotation(a, b) || this.hasAnnotation(c);
	}	
	public boolean hasAnnotation(final Class<? extends Annotation> a, final Class<? extends Annotation> b, final Class<? extends Annotation> c, final Class<? extends Annotation> d) {
		return this.hasAnnotation(a, b, c) || this.hasAnnotation(d);
	}	
	
//	nao usar pois dah warning
//	@SuppressWarnings("unchecked")
//	public <T extends Annotation> boolean hasAnyAnnotation(Class<T>... annotations) {
//		for (Class<T> annotation : annotations) {
//			if (getAnnotation(annotation) != null) {
//				return true;
//			}
//		}
//		return false;
//	}
	public <T extends Annotation> T getAnnotationObrig(final Class<T> annotation) {
		final T o = this.getAnnotation(annotation);
		if (o == null) {
			throw UException.runtime("N"+UConstantes.a_til+"o foi encontrata a annotation '" + annotation + "' no atributo '" + this + "'");
		}
		return o;
	}
	public <T extends Annotation> T getAnnotation(final Class<T> annotation) {
		return this.getAnnotation(annotation, false);
	}
	
	public FTT<Annotation, String> getAnnotation;
	public FTT<Annotation, Class<? extends Annotation>> getAnnotationByClass;
	
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(final String nome) {
		final List<Annotation> annotations = getAnnotations();
		for (final Annotation annotation : annotations) {
			if (annotation.getClass().getSimpleName().equalsIgnoreCase(nome)) {
				return (T) annotation;
			}
			if (annotation.annotationType().getSimpleName().equals(nome)) {
				return (T) annotation;
			}
		}
		if (getAnnotation != null) {
			return (T) getAnnotation.call(nome);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(final Class<T> annotation, final boolean buscarNaClasse) {
		
		if (field == null) {
			return null;
		}
		T a = field.getAnnotation(annotation);
		if (a != null) {
			return a;
		}
		if ( getMethod != null ) {
			a = getMethod.getAnnotation(annotation);
			if (a != null) {
				return a;
			}
		}
		if ( setMethod != null ) {
			a = setMethod.getAnnotation(annotation);
			if (a != null) {
				return a;
			}
		}
		
		if (getAnnotationByClass != null) {
			Annotation an = getAnnotationByClass.call(annotation);
			if (an != null) {
				return (T) an;
			}
		}
		
		if (buscarNaClasse) {
			a = getClasse().getAnnotation(annotation);
		}
		
		return a;
	}
	
	public String uniqueJoin() {
		
		if (this.hasAnnotation(UniqueJoin.class)) {
			this.getAnnotation(UniqueJoin.class).value();
		}
		
		final Annotation annotation = this.getAnnotation("UniqueJoin");
		
		if (annotation != null) {
			final Metodo metodo = ListMetodos.get(annotation.getClass()).get("value");
			return metodo.invoke(annotation);
		}
		return UString.toString(getProp("UniqueJoin"));
		
	}
	
	public boolean isUnique() {
		return this.hasAnnotation(Unique.class) || this.hasAnnotation("Unique") || UBoolean.isTrue(getProp("Unique"));
	}	
	
	public List<Annotation> getAnnotations() {
		if (field == null) {
			return null;
		}
		final Annotation[] annotations = field.getAnnotations();
		return Arrays.asList(annotations);
	}
	
	public boolean isTransient() {
		return this.hasAnnotation(Transient.class);
	}
	public boolean isTransientModifier(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isTransient(field.getModifiers());
	}
	public boolean isStatic(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isStatic(field.getModifiers());
	}
	public boolean isFinal(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isFinal(field.getModifiers());
	}
	public boolean isPrivate(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isPrivate(field.getModifiers());
	}
	public boolean isProtected(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isProtected(field.getModifiers());
	}
	public boolean isPublic(){
		if (field == null) return false;
		return java.lang.reflect.Modifier.isPublic(field.getModifiers());
	}
	public boolean isDefault(){
		if (field == null) return false;
		return !isPrivate() && !isPublic() && !isProtected();
	}
	public final String getModificadorDeAcesso() {
		if (isPublic()) {
			return "public";
		} else if (isPrivate()) {
			return "private";
		} else if (isProtected()) {
			return "protected";
		} else {
			return "";
		}
	}
	public boolean isBoolean(){
		return is(boolean.class) || is(Boolean.class);
	}
	public boolean eq(final String name){
		return field.getName().equalsIgnoreCase(name);
	}
	
	public boolean isAtivo(){
		return this.eq("ativo") || this.eq("isAtivo") || this.eq("is_ativo") || this.eq("ativa") || this.eq("isAtiva") || this.eq("is_ativa") || this.eq("idAtivo");
	}
	private Boolean primitivo;
	public boolean isPrimitivo() {
		if (primitivo == null) {
			primitivo = UType.isPrimitiva( getType() );
		}
		return primitivo;
	}
	public boolean isEnum() {
		return getType().isEnum();
	}
	public boolean isWrapper() {
		if (!isPrimitivo()) {
			return false;
		}
		final Class<?> type = getType();
		if (type.equals(boolean.class)) return false;
		if (type.equals(int.class)) return false;
		if (type.equals(double.class)) return false;
		if (type.equals(float.class)) return false;
		if (type.equals(short.class)) return false;
		return true;
	}
	public boolean isLong() {
		return getType().equals(Long.class);
	}
	public boolean isString() {
		return getType().equals(String.class);
	}
	public void setAleatorio(final Object o) {
		
		if (isDate()) {
			final Data data = Aleatorio.getData();
			data.setAno(2016);
			if ( getType().equals(Calendar.class) ) {
				set(o, data.getCalendar() );
			} else if ( getType().equals(Date.class) ) {
				set(o, data.toDate() );
			}
			return;
		}
		
		Object value = null;
		final String n = getColumnName();
		
		Integer length = this.getLength();
		
		if (isString()) {
			
			String s;
			
			if (length == null || length > 100) {
				length = 100;
			}
			
			if ( n.startsWith("cpj") || n.contains("cnpj") ) {
				s = Aleatorio.getIntString(length);
			} else if ( n.startsWith("nu_") || n.contains("inscricao") ) {
				s = Aleatorio.getIntString(length);
			} else if ( n.startsWith("tx_") ) {
				s = Aleatorio.getTexto(length);
			} else if ( n.contains("email") ) {
				s = UEmail.aleatorio();
			} else if ( n.contains("url") ) {
				s = Aleatorio.getUrl();
			} else if ( n.contains("cpf") ) {
				s = UCpf.aleatorio();
			} else if ( nomeDefault != null ) {
				s = nomeDefault;
			} else {
				s = field.getDeclaringClass().getSimpleName() + " " + Aleatorio.getString(10);
			}
			
			while (s.length() > length) {
				s = s.substring(1);
			}
			
			value = s;
			
		} else if ( getType().equals(Integer.class) ) {

			if ( n.startsWith("qt_") || n.contains("porcent") ) {
				value = Aleatorio.get(0, 100);
			} else {
				value = Aleatorio.get(0, 100000);	
			}
			
		} else 
//			if ( type().equals(Integer.class) ) {
//			
//			if ( n.startsWith("qt_") || n.contains("porcent") ) {
//				value = Aleatorio.get(0, 100);
//			} else {
//				value = Aleatorio.get(0, 1000);	
//			}
//			
//		} else 
		if ( getType().equals(Double.class) ) {
			
			if (length == null) {
				value = Aleatorio.getBigDecimal().doubleValue();	
			} else {
				value = Aleatorio.getBigDecimal(length, scale).doubleValue();	
			}
			
		} else {
			value = Aleatorio.get(field.getType());	
		}
		
		set(o, value);
		
	}
	
	public boolean isDate() {
		return UType.isData(getType());
	}
	public Table table() {
		return field.getType().getAnnotation(Table.class);
	}
	public String toSql(final Object o) {

		Object value = this.get(o);
		
		if (UObject.isEmpty(value)) {
			return "null";
		}
		if (isString()) {
			return "'" + value.toString() + "'";
		}
		if (isPrimitivo()) {
			return value.toString();
		}
		if (isDate()) {
			return Data.to(value).format_sql(true);
		}

		value = ListAtributos.get(value.getClass()).getId().get(value);
		
		UAssert.notEmpty(value, "value == null");

		return value.toString();
		
	}
	public boolean typeIn(final ListClass types){
		for (final Class<?> type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean typeIn(final Class<?>... types){
		for (final Class<?> type : types) {
			if (is(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean is(final Class<?> type) {
		return getType().equals(type) || UClass.a_herda_b(getType(), type);
	}

	@Setter//pode ser sobrescrito em alguns mehtodos
	private Class<?> type;
	
	public Class<?> getType() {
		if (type == null) {
			type = field.getType();
		}
		return type;
	}
	
	private String typeDeclaration;
	
	public String getTypeDeclaration() {
		
		if (typeDeclaration != null) {
			return typeDeclaration;
		}
		
		final String s = UGenerics.getClassComGenerics(field).toString();
		
//		Removido em 2019-05-18 - ainda nao sei se hah impacto negativo
//		String s = getType().getSimpleName();
//		ListClass classes = UGenerics.getGenericClasses(field);
//		if (classes != null) {
//			String x = "";
//			for (Class<?> classe : classes) {
//				x += ", " + classe.getSimpleName();
//			}
//			x = x.substring(2);
//			s += "<" + x + ">";
//		}
		
		typeDeclaration = s;
		return s;
		
	}
	
	public boolean isListOf(final Class<?> type) {
		if (!isList()) return false;
		return getTypeOfList().equals(type);
		
	}
	public boolean isList() {
		return UType.isList(getType());
	}
	public boolean isMap() {
		return UType.isMap(getType());
	}
	private Class<?> typeOfList;
	public Class<?> getTypeOfList() {
		if (typeOfList == null) {
			typeOfList = UGenerics.getGenericClass(field);
		}
		return typeOfList;
	}
	public Field getField() {
		return field;
	}
	
	private Object value;
	public Boolean aceitaNulos;
	
	@SuppressWarnings("deprecation")
	public void setValue(final Object o) {
		
		value = this.get(o);
		
		if (value == null) {
			return;
		}
		
		if (value instanceof Date) {
			
			final java.util.Date d = (java.util.Date) value;
			
			Data data;
			
			final Temporal a = this.getAnnotation( Temporal.class );
			if (a != null && a.value().equals(TemporalType.TIME)) {
				data = new Data(1900, 1, 1, d.getHours(), d.getMinutes(), d.getSeconds());
			} else {
				data = new Data(d);
			}
			
			value = data.format_sql(false);
			return;
		}	
		if (value instanceof Calendar) {
			final Calendar d = (Calendar) value;
			final Data data = new Data(d);
			value = data.format_sql(false);
			return;
		}	

		if (value instanceof java.sql.Date) {
			final java.sql.Date d = (java.sql.Date) value;
			final Data data = new Data(d);
			value = data.format_sql(false);
			return;
		}
		
		if (!UType.isPrimitiva(value)) {
			value = UIdObject.getId(value);
			return;
		}
		
	}
	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}
	public boolean isFk() {
		if (isPrimitivo()) {
			return false;
		}
		if (isList()) {
			return false;
		}
		return true;
	}
	
	
	private String nomeComPrimeiraMaiuscula;
	public String upperNome() {
		if (nomeComPrimeiraMaiuscula == null) {
			nomeComPrimeiraMaiuscula = UString.primeiraMaiuscula(nome()); 
		}
		return nomeComPrimeiraMaiuscula;
	}
	
	public String nome() {
		return getField().getName();
	}

	private List<ConexaoJdbc> bancosEmQueExiste = new ArrayList<>();

	public boolean existeNoBanco(final ConexaoJdbc con) {
		if ( bancosEmQueExiste.contains(con) ) {
			return true;
		}
		if ( con.existsColumn(getClasse(), getColumnName()) ) {
			bancosEmQueExiste.add(con);
			return true;
		} else {
			return false;
		}
	}
	public void setExisteNoBanco() {
		this.setExisteNoBanco(UConfig.con());
	}
	public void setExisteNoBanco(final ConexaoJdbc con) {
		bancosEmQueExiste.add(con);
	}
	
	public boolean existeNoBanco() {
		return this.existeNoBanco(UConfig.con());
	}
	public Class<?> getClasse() {
		return classe;
	}
	public boolean temporalDate() {
		if (!isDate()) {
			return false;
		}
		final Temporal temporal = this.getAnnotation(Temporal.class);
		if (temporal == null) {
			throw UException.runtime("O atributo " + getClasse().getSimpleName() + "." + this + " estah sem a anotacao @Temporal");
		}
		return temporal.value().equals(TemporalType.DATE);
	}
	public boolean temporalDateTime() {
		if (!isDate()) {
			return false;
		}
		final Temporal temporal = this.getAnnotation(Temporal.class);
		if (temporal == null) {
			throw UException.runtime("O atributo " + getClasse().getSimpleName() + "." + this + " estah sem a anotacao @Temporal");
		}
		return temporal.value().equals(TemporalType.TIMESTAMP);
	}

	private Boolean calculado;
	public boolean isCalculado() {
		if (calculado == null) {
			calculado = this.hasAnnotation(SomenteLeitura.class) || isId();
		}
		return calculado;
	}
	
	public int getOrdem() {
		final Ordem ordem = this.getAnnotation(Ordem.class);
		if (ordem == null) {
			return 999;
		} else {
			return ordem.value();
		}
	}
	
	private static boolean isThis(final Atributo a) {
		return a instanceof Atributo;
	}
	private static Atributo toThis(final Atributo a) {
		return a;
	}
	
	public boolean eq(final Atributo a) {
		if (super.equals(a)) {
			return true;
		}
		if (Atributo.isThis(a)) {
			if (Atributo.toThis(a).getField().equals(getField())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isNumeric() {
		return getType().equals(BigDecimal.class);
	}
	
	public boolean isNumeric1() {
		return digitsFraction() == 1;
	}
	public boolean isNumeric2() {
		return digitsFraction() == 2;
	}
	public int digitsFraction() {
		if ( !getType().equals(BigDecimal.class) ) {
			return 0;
		}
		final Digits digits = this.getAnnotation(Digits.class);
		UAssert.notEmpty(digits, "Falta a annotation @Digits");
		return digits.fraction();
	}

	public String nomeElegante() {
		
		if (isId()) {
			return "id";
		}
		
		final String s = nome();
		
		if ( s.startsWith("ds") || s.startsWith("is") || s.startsWith("tx") ) {
			final String x = s.substring(2);
			final String y = x.substring(0,1);
			if (!y.equals(y.toLowerCase())) {
				return UString.primeiraMinuscula(x);
			}
		}
		
		return s;
		
	}

	public boolean equals(final Object a, final Object b) {
		return UCompare.compare(a, b, this) == 0;
	}
	public boolean ne(final Object o, final Object old) {
		return !this.equals(o, old);
	}

	private String sqlCount;
	
	public int getCount(final Integer id) {
		if (sqlCount == null) {
			sqlCount = "select count(*) from " + getClasse().getSimpleName() + " where " + nome() + " = ";
		}
		return SqlNative.getInt(sqlCount + id);
	}
	
	private Boolean obrigatorio;
	
	public boolean isObrigatorio() {
		
		if (obrigatorio == null) {
			if (this.hasAnnotation(NotNull.class) || this.hasAnnotation(Obrigatorio.class)) {
				obrigatorio = true;
			} else if (this.hasAnnotation(Column.class)) {
				final Column column = this.getAnnotation(Column.class);
				obrigatorio = !column.nullable();
			} else if (this.hasAnnotation(JoinColumn.class)) {
				final JoinColumn column = this.getAnnotation(JoinColumn.class);
				obrigatorio = !column.nullable();
			} else {
				obrigatorio = false;
			}
		}
		
		return obrigatorio;
		
	}

	public int getMaxInt() {
		final Max max = this.getAnnotation(Max.class);
		if (max == null) {
			return 99999;
		} else {
			return UInteger.toInt(max.value(), 99999);
		}
	}
	public int getMinInt() {
		final Min min = this.getAnnotation(Min.class);
		if (min == null) {
			return 0;
		} else {
			return UInteger.toInt(min.value(), 0);
		}
	}

	public boolean isPersistent() {
		return false;
	}

	public boolean isArray() {
		return getType().getSimpleName().endsWith("[]");
	}

	public Atributos getAtributos() {
		return ListAtributos.get(getType());
	}
	public Atributos getAtributosPersistentes() {
		return ListAtributos.persist(getType(), false);
	}
	
	private String nomeValue;
	private Atributos nomeValueCadeia;
	
	public String getNomeValue() {
		if (nomeValue != null) {
			return nomeValue;
		}
		if (isLookup()) {
			final Lookup lookup = this.getAnnotation(Lookup.class);
			nomeValue = lookup.vinculo() + "." + getReal().getNomeValue();
		} else {
			nomeValue = nome();
		}
		return nomeValue;
	}
	public Atributos getNomeValueCadeia() {
		if (nomeValueCadeia == null) {
			getNomeValue();
		}
		return nomeValueCadeia.copy();
	}
	public boolean isStatus() {
		return getType().getAnnotation(Status.class) != null;		
	}
	public boolean isEmail() {
		return this.hasAnnotation(Email.class) || (isString() && nome().startsWith("email"));		
	}
	public boolean isTelefone() {
		return this.hasAnnotation(Telefone.class) || (isString() && nome().startsWith("telefone"));		
	}
	public boolean isImagem() {
		return this.hasAnnotation(Imagem.class);
	}
	public boolean isArquivo() {
		return this.hasAnnotation(Arquivo.class) || isImagem();
	}
	
	private String stringGet;
	public String stringGet() {
		if (stringGet == null) {
			stringGet = "get" + upperNome() + "()";
		}
		return stringGet;
	}
	
	public String stringSet(final String value) {
		return "set" + upperNome() + "("+value+");";
	}
	
	private String titulo;
	
	public String getTitulo() {
		if (titulo == null) {
			final Titulo o = this.getAnnotation(Titulo.class, false);
			if (o == null) {
				String s = nome();
				s = UString.toCamelCaseSepare(s);
				s = s.replace("Percentual", "%");
				s = s.replace("Porcentagem", "%");
				titulo = s;
			} else {
				titulo = o.value();
			}
		}
		return titulo;
	}
	public String getDeclaracao() {
		String s = getModificadorDeAcesso() + " ";
		if (isStatic()) {
			s += "static ";
		}
		if (isFinal()) {
			s += "final ";
		}
		s += getTypeDeclaration() + " ";
		s += nome();
		return s;
	}
	
	private Boolean lombokSetter;
	
	public boolean lombokSetter() {
		if (lombokSetter == null) {
			
			final ClassBox box = ClassBox.get(getClasse());
			
			if (getMethod == null) {
				getMethod = box.getMetodos().get("set" + upperNome());
				if (getMethod == null) {
					lombokSetter = false;
					return false;
				}
			}
			
			if (!box.getImports().contains(Setter.class)) {
				lombokSetter = false;
				return false;
			}
			
			String s = box.getTripa();
			s = s.replace(";", " ;");
			
			final String busca = "@Setter " + getModificadorDeAcesso() + " " + getType().getSimpleName() + " " + nome() + " ";
			lombokSetter = s.contains(busca);
			
		}
		return lombokSetter;
	}

	private Boolean lombokGetter;
	
	public boolean lombokGetter() {
		if (lombokGetter == null) {
			
			final ClassBox box = ClassBox.get(getClasse());
			
			if (getMethod == null) {
				getMethod = box.getMetodos().get("get" + upperNome());
				if (getMethod == null) {
					lombokGetter = false;
					return false;
				}
			}
			
			if (!box.getImports().contains(Getter.class)) {
				lombokGetter = false;
				return false;
			}
			
			String s = box.getTripa();
			s = s.replace(";", " ;");
			
			final String busca = "@Getter " + getModificadorDeAcesso() + " " + getType().getSimpleName() + " " + nome() + " ";
			lombokGetter = s.contains(busca);
			
		}
		return lombokGetter;
	}
	
		
}
