package gm.utils.jpa;

import java.util.HashMap;
import java.util.Map;

import gm.utils.config.UConfig;
import gm.utils.number.ListInteger;
import gm.utils.number.UInteger;

public class FreeId {
	
	private static final int range = 500;
	
	private Class<?> classe;
	private String ts;
	private int atual = 0;
	private int max;
	private void setMax(int max) {
		this.max = max;
//		
//		int maior = U.toInt(SqlNative.getInt("select max(id) from " + ts),0);
//		
//		if (max < maior) {
//			Console.debug("algo errado");
//		}
//		if (classe.equals(Tabela.class) && max < 8) {
//			Console.debug("algo errado");
//		}
//		
	}
	private int getMax() {
		return max;
	}
	private int incMax() {
		max++;
		setMax(max);
		return max;
	}
	private String sql;
	private ListInteger recents;
	private boolean acabou = false;
	
	private FreeId(Class<?> classe) {
		this.classe = classe;
		ts = UTableSchema.get(classe);
		String s = "select max(id) from " + ts;
		setMax(selectInt(s,0));
		montaSql();
	}
	
	private void montaSql() {
		sql = "select min(id) from "+ts;
		sql += " a where id > " + atual + " and id <= " + (atual+range);
		if (recents != null && !recents.isEmpty()) {
			sql += " and (a.id+1 not in ("+recents.toString(",") + "))";	
		}
		sql += " and not exists(select * from "+ts;
		sql += " b where b.id = a.id+1)";
	}

	private static Map<Class<?>,FreeId> maps = new HashMap<>();
	
//	private int getMax() {
//		if (!acabou) {
//			max = U.toInt(DB.selectInt(sql + max),0);
//		}
//		max++;
//		return max;
//	}
	
	private static Integer selectInt(String sql, Integer def) {
		ConexaoJdbc con = UConfig.con();
		return UInteger.toInt(con.selectInt(sql), def);
	}
	
	private int getInterno() {

		if (acabou) {
			return incMax();
		}
		
		ConexaoJdbc con = UConfig.con();
		
		if (!con.exists(classe, 1)) {
			return 1;
		}
		
		if (recents != null) {
			while (recents.size() > 400) {
				recents.remove(0);
			}
			montaSql();
		}
		
		Integer id = null;
		while (id == null) {
			id = selectInt(sql, 0);
			if (id == null) {
				atual += range;
				acabou = atual > getMax();
				if (acabou) {
					sql = "select max(id) from " + ts + " where id > ";
					setMax( selectInt(sql + getMax(),getMax()) );
					recents = null;
					return incMax();
				} else {
					montaSql();
				}
			}
		}
		return id + 1;
	}	
	
	private synchronized int get() {
		int id = getInterno();
		if (acabou) {
			return id;
		}
		if (recents == null) {
			recents = new ListInteger();
		}
		recents.add(id);
		return id;
	}

	public static int get(Class<?> classe) {
//		throw new MessageException("n"+UConstantes.a_til+"o deve ser usado");
		FreeId o = maps.get(classe);
		if (o == null) {
			o = new FreeId(classe);
			maps.put(classe, o);
		}
		return o.get();
	}

	public static void drop(Class<?> classe) {
		maps.remove(classe);
	}
	
	public static void main(String[] args) {
		
//		Console.debug( get(ParcelaPagamentoCurso.class) );
		
		/*
		ListClass list = new ListClass();
		list.add( RelacaoDeEntidades.list().get(0) );
		list.add( RelacaoDeEntidades.list().get(1) );
//		ListClass list = RelacaoDeEntidades.list();
		
		for (Class<?> c : list) {
			new Thread() {
				@Override
				public void run() {
					tools.utils.Console.debug( get(c) );
				};
			}.start();
		}
		*/
	}

	public static void delete(Class<?> classe, Integer id) {
		FreeId o = maps.get(classe);
		if (o == null) {
			return;
		}
		ListInteger recents = o.recents;
		if (recents == null) {
			return;
		}
		recents.remove(id);
	}
	
}
