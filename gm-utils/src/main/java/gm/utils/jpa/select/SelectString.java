package gm.utils.jpa.select;
public class SelectString<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, String> {
	public SelectString(TS x, String campo) {
		super(x, campo);
	}
	public TS like(StringBuilder s){
		return like(s.toString());
	}
	public TS like(String s){
		c().like(getCampo(), s);
		return ts;
	}
	public TS notLike(String s){
		c().notLike(getCampo(), s);
		return ts;
	}
	public TS likePlus(String s){
		s = s.replace(" ", "");
		String x = "%";
		while (!s.isEmpty()) {
			x += s.substring(0, 1) + "%";
			s = s.substring(1);
		}		
		return like(x);
	}
	public TS startsWith(String s){
		c().startsWith(getCampo(), s);
		return ts;
	}
	public TS notStartsWith(String nome) {
		c().notStartsWith(getCampo(), nome);
		return ts;
	}
	public TS endsWith(String s){
		c().endsWith(getCampo(), s);
		return ts;
	}
	public TS notEndsWith(String s){
		c().notEndsWith(getCampo(), s);
		return ts;
	}

//	public SelectStringLength<TS> len(){
//		new SelectStringLength<>(ts, campo, c)
//		return null;
//	}
}
