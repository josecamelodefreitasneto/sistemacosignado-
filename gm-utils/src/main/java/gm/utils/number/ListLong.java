package gm.utils.number;

import gm.utils.abstrato.Lista;
import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ListLong extends Lista<Long>{

	private static final long serialVersionUID = 1;
	
	public ListLong(){}
	public ListLong(Long... args){
		for (Long o : args) add(o);
	}

	public static ListLong byDelimiter(String s, String delimiter) {
		
		ListLong o = new ListLong();
		
		ListString list = ListString.byDelimiter(s, delimiter);
		
		for (String string : list) {
			o.add( UInteger.toInt(string).longValue() );
		}
		
		return o;
		
	}

	public static ListLong destrincha(String s) {

		if ( !UInteger.isLongInt(s) ) {
			throw UException.runtime("Deve ser 100% num"+UConstantes.e_agudo+"rico: " + s );
		}
		
		ListLong list = new ListLong();
		
		while ( !UString.isEmpty(s) ) {
			Integer x = UInteger.toInt(s.substring(0,1));
			s = s.substring(1);
			list.add(x.longValue());
		}
		
		return list;
		
	}

//	@Override
	public ListLong removeLast(int quantidade) {
		return (ListLong) super.removeLast_(quantidade);
	}

//	@Override
	public ListLong remove(int index, int quantidade) {
		return (ListLong) super.remove_(index, quantidade);
	}

	public void add(int i) {
		add( ULong.toLong(i) );
	}

	public static ListLong array(Long... ints) {
		ListLong list = new ListLong();
		for (long i : ints) {
			list.add(i);
		}
		return list;
	}
	
}
