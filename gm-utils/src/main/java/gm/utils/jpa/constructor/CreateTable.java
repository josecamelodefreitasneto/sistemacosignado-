package gm.utils.jpa.constructor;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import gm.utils.comum.UConstantes;
import gm.utils.comum.ULog;
import gm.utils.comum.UType;
import gm.utils.exception.MessageException;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBC;
import gm.utils.jpa.USchema;
import gm.utils.jpa.UTable;
import gm.utils.jpa.UTableSchema;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTable {
	
	private ConexaoJdbc con;
	private DriverJDBC driver;
	private String owner;

	public CreateTable(final DriverJDBC driver, final String owner) {
		this.driver = driver;
		this.owner = owner;
	}
	
	public CreateTable(final ConexaoJdbc con) {
		this(con.getDriver(), con.getOwner());
		this.con = con;
	}

	private boolean guardarReferencias = true;
	private boolean aceitarSomenteItensNoPadrao = true;
	private ListString referencias = new ListString();
	private ListString constraints = new ListString();

	private static final Class<byte[]> byteClass = byte[].class;

	public String tratarColuna(final Atributo a){
		return this.tratarColuna(UTableSchema.get(a.getClasse()), a);
	}
	
	public void tratarUnique(final String ts, final Atributo a, Atributos as) {
	
		if (ts.equalsIgnoreCase("public.usuario")) {
			System.out.println(a);
		}
		
		final String campo = a.getColumnName();
		
		String where = "";
		
		String s;
		
		if (a.isUnique()) {
			s = "create unique index "+ts.replace(".", "_")+"_"+campo+" on "+ts+"("+campo+")";
		} else {
			
			String unicoPor = a.uniqueJoin();
			if (unicoPor == null) {
				return;
			}
				
			final ListString palavras = ListString.byDelimiter(unicoPor, ",");
			palavras.trimPlus();
			s = "create unique index "+ts.replace(".", "_")+"_"+campo;
			for (final String string : palavras) {
				s += "_"+string;
			}
			
			s += " on "+ts+"("+campo;
			for (final String string : palavras) {
				s += ","+string;
			}
			s += ")";
			
			for (final String string : palavras) {
				if (!isNotNull(as.getObrig(string))) {
					where += " and "+string+" is not null";		
				}
			}
				
		}
		
		if (!isNotNull(a)) {
			where += " and "+campo+" is not null";
		}
		
		String uniqueWhere = a.getProp("UniqueWhere");
		if (UString.notEmpty(uniqueWhere)) {
			where += " and "+uniqueWhere;
		}		
		
		if (UString.notEmpty(where)) {
			s += " where " + where.substring(5);
		}
		
		if (driver == DriverJDBC.MSSQLServer) {
			s = s.replace("create unique index", "create unique nonclustered index");
		}
		
		s = s.toLowerCase();
		
		constraints.add(s + ";");
		
	}
	
	public String tratarColuna(final String ts, final Atributo a) {

		final Class<?> type = a.getType();
		final String campo = a.getColumnName();

		if (a.getAnnotation(java.beans.Transient.class) != null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " estah com a anotacao 'java.beans.Transient', mas a anotacao correta eh 'javax.persistence.Transient'!");
		}
		if (a.isList()) {
			throw new MessageException ("O campo " + ts + "." + campo + " "+UConstantes.e_agudo+" uma List, isso vai contra a arquitetura adotada!");
		}
		if (a.getAnnotation(OneToOne.class) != null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " estah com a anotacao 'OneToOne', isso vai contra a arquitetura adotada!");
		}
		if (type.equals(CreateTable.byteClass)) {
			final javax.persistence.Lob lob = a.getAnnotation(javax.persistence.Lob.class);
			if (lob == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Lob (javax.persistence.Lob)");
			}
			return campo + " bytea";
		}

//		if (type.equals(Long.class)) {
//			throw new MessageException ("O campo " + ts + "." + campo + " estah com o tipo Long. Utilize o tipo Integer");
//		}
		Column column = a.getAnnotation(Column.class);

		if (type.equals(String.class)) {

			column = a.getAnnotation(Column.class);

			if (column == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Column(length=?)");
			}
			if (column.length() < 1) {
				throw new MessageException (campo + " column.length() < 1");
			}
			return campo + " varchar(" + column.length() + ")";
		} else if (type.equals(Double.class) || type.equals(double.class)) {
			throw new MessageException ("O campo " + ts + "." + campo + " "+UConstantes.e_agudo+" do tipo Double! Use o tipo BigDecimal");
		} else if (type.equals(Float.class) || type.equals(float.class)) {
			throw new MessageException ("O campo " + ts + "." + campo + " "+UConstantes.e_agudo+" do tipo Float! Use o tipo BigDecimal");
		} else if (type.equals(BigDecimal.class)) {

			final Digits digits = a.getAnnotation(Digits.class);

			if (digits == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " precisa ter a anotacao @Digits(integer=15, fraction=2)");
			}
			if (digits.fraction() == 0) {
				throw new MessageException ("O campo " + ts + "." + campo + " estah com a anotacao @Digits fraction = 0");
			}
			if (digits.fraction() >= digits.integer()) {
				throw new MessageException ("O campo " + ts + "." + campo + " digits.fraction() >= digits.integer()");
			}
//			if (digits.integer() > 15) {
//				throw new MessageException ("O campo " + ts + "." + campo + " digits.integer() > 15");
//			}
			return campo + " numeric(" + digits.integer() + "," + digits.fraction() + ")";

		} else if (type.getName().equals("int")) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " tem o tipo int. A utilizacao deste tipo irah causar varios tipos de problemas na infraestrutura adotada. Utilize Integer!");
		} else if (type.getName().equals("boolean")) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " tem o tipo boolean. A utilizacao deste tipo irah causar varios tipos de problemas na infraestrutura adotada. Utilize Boolean!");
		} else if ( UType.isList(type) ) {
			throw new MessageException ("O campo " + ts + "." + campo + " deve ser anotado com @Transient");
		} else if (type.equals(Integer.class)) {
			return campo + " int";
		} else if (type.equals(Long.class)) {
			return campo + " bigint";
		}
		if (type.equals(Boolean.class)) {
			if (driver == DriverJDBC.PostgreSQL) {
				String s = campo + " boolean";
				if (a.eq("excluido") || a.eq("registroBloqueado")) {
					s += " default false";
				}
				return s;
			} else if (driver == DriverJDBC.MSSQLServer) {
				String s = campo + " bit";
				if (a.eq("excluido") || a.eq("registroBloqueado")) {
					s += " default 0";
				}
				return s;
			} else {
				throw UException.runtime("driver nao tratado: " + driver);
			}
			
		}
		if (type.equals(java.util.Calendar.class)) {

			final Temporal temporal = a.getAnnotation(Temporal.class);

			if (temporal == null) {
				throw new MessageException ("O campo " + ts + "." + campo + " deve ser anotado com @Temporal(TemporalType.DATE) ou semelhante");
			}
			if (temporal.value().equals(TemporalType.DATE)) {
				return campo + " date";
			}
			if (temporal.value().equals(TemporalType.TIMESTAMP)) {
				if (driver == DriverJDBC.PostgreSQL) {
					return campo + " timestamp";
				} else if (driver == DriverJDBC.MSSQLServer) {
					return campo + " datetime";
				} else {
					throw UException.runtime("driver nao tratado: " + driver);
				}
			}
			throw new MessageException (", " + campo + " anotacao deve ser DATE ou TIMESTAMP");
		}
		
		if (UType.isData(type) && type != Calendar.class) {
			throw new MessageException ("Tipo do campo " + campo + " invalido! Mude para java.util.Calendar");
		}
		
		if (a.getAnnotation(ManyToOne.class) == null || a.getAnnotation(JoinColumn.class) == null) {
			throw new MessageException ("O campo " + ts + "." + campo
					+ " deve ser anotado com @ManyToOne(fetch = FetchType.LAZY ) @JoinColumn(name = \""
					+ a.nome() + "\")");
		}
		String s = a.getAnnotation(JoinColumn.class).name();
		if (UString.isEmpty(s)) {
			throw new MessageException ("A anotacao @JoinColumn do campo " + campo + " deve possuir o atributo 'name'");
		}
		if (!s.equalsIgnoreCase(a.nome())) {
			if (aceitarSomenteItensNoPadrao) {
				throw new MessageException ("no campo " +  ts + "." + campo + " a anotacao @JoinColumn.name eh diferente do nome do campo");
			}
		}
		if (!a.isPrivate()) {
			throw new MessageException ("O atributo " + campo + " deve ser private");
		}
		
		final Table table = a.getType().getAnnotation(Table.class);

		if (table == null) {
			throw new MessageException ("no campo " + campo + " aponta para um objeto que n"+UConstantes.a_til+"o tem a anotacao @Table - " + ts + " - " + a);
		}
		
		String name = table.name();
		if (UString.isEmpty(name)) {
			name = a.getType().getSimpleName();
		}
		
		String schema = UString.isEmpty(table.schema()) ? UString.beforeFirst(ts, ".") : table.schema();
		name = schema + "." + name;
		
		
		String referencesOptions;
		if (driver == DriverJDBC.PostgreSQL) {
			referencesOptions = " on update cascade on delete restrict";
		} else if (driver == DriverJDBC.MSSQLServer) {
			/* a porcaria do SQLServer nao permite que hajam duas foreign keys para a mesma table com update cascade
			 * entao, se houverem na mesma table duas fks para outra mesma table ele ira reclamar
			 * */
//			if (name.equals(ts)) {
				referencesOptions = "";
//			} else {
//				
//				referencesOptions = " on update cascade on delete no action";
//			}
		} else {
			throw UException.runtime("driver nao tratado: " + driver);
		}

		if (guardarReferencias) {
			
			if (driver == DriverJDBC.PostgreSQL) {
				s = "alter table " + ts + " add foreign key (" + campo + ") references " + name + referencesOptions + ";";
			} else if (driver == DriverJDBC.MSSQLServer) {
				s = "alter table " + ts + " add constraint \""+ts+"."+campo+" > "+name+"\" foreign key (" + campo + ") references " + name + referencesOptions + ";";
			} else {
				throw UException.runtime("driver nao tratado: " + driver);
			}
			
			referencias.addIfNotContains(s);
			return campo + " int";
		} else {
			s = campo + " int references " + name + referencesOptions;
			return s;
		}
		
	}
	public ListString valida(final Class<?> classe) {
		final Table table = classe.getAnnotation(Table.class);
		return valida(table.schema(), classe);

	}
	public ListString valida(String schema, Class<?> c) {

		final ListString erros = new ListString();

		String ts = schema + "." + c.getSimpleName();

		final Atributos atributos = ListAtributos.get(c);
		atributos.removeTransients();
		atributos.removeStatics();

		final ListString script = new ListString();

		script.add("create table " + ts + " (");
		
		final GeneratedValue generatedValue = atributos.getId().getAnnotation(GeneratedValue.class);
		final boolean sequencial = generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;   
		
		if (!sequencial) {
			script.add("  id int not null primary key");
		} else if (driver == DriverJDBC.PostgreSQL) {
			script.add("  id serial not null primary key");
		} else if (driver == DriverJDBC.MSSQLServer) {
			script.add("  id int identity(1,1) not null primary key");
		} else {
			throw UException.runtime("???");
		}

		for (final Atributo a : atributos) {
			try {
				final String t = this.tratarColuna(ts, a);
				if (t.endsWith(" ")) {
					this.tratarColuna(ts, a);
				}
				script.add( ", " + t + (isNotNull(a) ? " not null" : "") );
				tratarUnique(ts, a, atributos);
			} catch (final Exception e) {
				e.printStackTrace();
				erros.add(e.getMessage());
			}
		}

		script.add("); ");
		
		if (driver == DriverJDBC.PostgreSQL) {
			script.add("alter table " + ts + " owner to " + owner + ";");
		} else if (driver == DriverJDBC.MSSQLServer) {
			script.add("grant select, insert, update on " + ts + " to " + owner + ";");
		} else {
			script.add("--implementar grant");
		}
		
		script.add(referencias);
		script.add(constraints);
		
		if (!erros.isEmpty()) {
			erros.print();
			String s = "N"+UConstantes.a_til+"o foi possivel validar a entidade: " + c.getName();
			s += "\n" + erros.toString("\n");
			throw UException.runtime(s);
		}
		
		return script;
	}

	private boolean isNotNull(final Atributo a) {
		return a.isObrigatorio() && !a.hasAnnotation("VisivelSe");
	}
	public String exec(Class<?> classe) {
		final Table table = classe.getAnnotation(Table.class);
		return exec(table.schema(), classe);
	}
	public String exec(String schema, Class<?> classe) {
		final ListString valida = valida(schema, classe);
		return valida.toString("\n");
	}
	public void reCreate(final Class<?> classe) {
		final UTable table = con.table(classe);
		table.drop();
		this.create(table);
	}
	public void create(String schema, Class<?> classe) {
		final String sql = exec(schema, classe);
		new USchema(con).create(schema);
		con.exec(sql);
	}
	public void create(final Class<?> classe) {
		final Table table = classe.getAnnotation(Table.class);
		create(table.schema(), classe);
	}

	public void print(final Class<?> classe) {
		ULog.debug(exec(classe));
	}
	
	public void create(final UTable table) {
		if (table.exists()) {
			throw UException.runtime(table.getTs() + " ja existe!");
		}
		final Class<?> classe = table.getClasse();
		this.create(classe);
		table.setExists(true);
		final Atributos as = ListAtributos.persist(classe, false);
		for (final Atributo a : as) {
			a.setExisteNoBanco(con);
		}
	}
	public boolean createIfNotExists(final UTable table) {
		if (!table.exists()) {
			this.create(table.getClasse());
			table.setExists(true);
			return true;
		} else {
			return false;
		}
	}

	public void createIfNotExists(final Class<?> classe) {
		this.createIfNotExists(con.table(classe));
	}

}

