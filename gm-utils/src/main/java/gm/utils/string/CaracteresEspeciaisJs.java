package gm.utils.string;

import gm.utils.comum.UConstantes;

public class CaracteresEspeciaisJs {

	public static void main(String[] args) {
		ListString list = ListString.clipboard();
		exec(list);
		list.print();
		list.toClipboard();
	}
	
	public static String exec(String s) {
		ListString list = ListString.array(s);
		exec(list);
		return list.toString("");
	}
	
	public static void exec(ListString list) {
		list.replaceTexto(UConstantes.a_agudo,"\\u00e1");
		list.replaceTexto(UConstantes.A_agudo,"\\u00c1");
		list.replaceTexto(UConstantes.a_crase,"\\u00e0");
		list.replaceTexto(UConstantes.A_crase,"\\u00c0");
		list.replaceTexto(UConstantes.a_circunflexo,"\\u00e2");
		list.replaceTexto(UConstantes.A_circunflexo,"\\u00c2");
		list.replaceTexto(UConstantes.a_til,"\\u00e3");
		list.replaceTexto(UConstantes.A_til,"\\u00c3");
		list.replaceTexto(UConstantes.a_trema,"\\u00e4");
		list.replaceTexto(UConstantes.A_trema,"\\u00c4");
		list.replaceTexto(UConstantes.e_agudo,"\\u00e9");
		list.replaceTexto(UConstantes.E_agudo,"\\u00c9");
		list.replaceTexto(UConstantes.e_circunflexo,"\\u00ea");
		list.replaceTexto(UConstantes.E_circunflexo,"\\u00ca");
		list.replaceTexto(UConstantes.e_crase,"\\u00e8");
		list.replaceTexto(UConstantes.E_crase,"\\u00c8");
		list.replaceTexto(UConstantes.E_trema,"\\u00cb");
		list.replaceTexto(UConstantes.i_agudo,"\\u00ed");
		list.replaceTexto(UConstantes.I_agudo,"\\u00cd");
		list.replaceTexto(UConstantes.i_crase,"\\u00ec");
		list.replaceTexto(UConstantes.I_crase,"\\u00cc");
		list.replaceTexto(UConstantes.i_circunflexo,"\\u00ee");
		list.replaceTexto(UConstantes.I_circunflexo,"\\u00ce");
		list.replaceTexto(UConstantes.i_trema,"\\u00ef");
		list.replaceTexto(UConstantes.I_trema,"\\u00cf");
		list.replaceTexto(UConstantes.o_agudo,"\\u00f3");
		list.replaceTexto(UConstantes.O_agudo,"\\u00d3");
		list.replaceTexto(UConstantes.o_crase,"\\u00f2");
		list.replaceTexto(UConstantes.O_crase,"\\u00d2");
		list.replaceTexto(UConstantes.o_circunflexo,"\\u00f4");
		list.replaceTexto(UConstantes.O_circunflexo,"\\u00d4");
		list.replaceTexto(UConstantes.o_til,"\\u00f5");
		list.replaceTexto(UConstantes.O_til,"\\u00d5");
		list.replaceTexto(UConstantes.o_trema,"\\u00f6");
		list.replaceTexto(UConstantes.O_trema,"\\u00d6");
		list.replaceTexto(UConstantes.u_agudo,"\\u00fa");
		list.replaceTexto(UConstantes.U_agudo,"\\u00da");
		list.replaceTexto(UConstantes.u_crase,"\\u00f9");
		list.replaceTexto(UConstantes.U_crase,"\\u00d9");
		list.replaceTexto(UConstantes.u_circunflexo,"\\u00fb");
		list.replaceTexto(UConstantes.U_circunflexo,"\\u00db");
		list.replaceTexto(UConstantes.u_trema,"\\u00fc");
		list.replaceTexto(UConstantes.cedilha,"\\u00e7");
		list.replaceTexto(UConstantes.CEDILHA,"\\u00c7");
		list.replaceTexto(UConstantes.n_til,"\\u00f1");
		list.replaceTexto(UConstantes.N_til,"\\u00d1");
	}	
}
