package gm.utils.map;

import gm.utils.comum.UType;
import gm.utils.string.ListString;

public class MapSoToTest {
	
	private ListString list = new ListString();

	private MapSoToTest(MapSO map) {
		list.add("new MapSO()");
		exec(map);
		list.add(";");
	}
	private void exec(MapSO map) {
		for(String key : map.keySet()) {
			Object value = map.get(key);
			if (value == null) {
				list.add(".add(\"" + key + "\", null)");
			} else if (value instanceof String) {
				list.add(".add(\"" + key + "\", \""+value+"\")");
			} else if (UType.isPrimitiva(value)) {
				list.add(".add(\"" + key + "\", "+value+")");
			} else if (UType.isArray(value)) {
				list.add(".add(\"" + key + "\", [");
				list.add("]");
			} else {
				list.add(".sub(\"" + key + "\")");
				list.margemInc();
				exec((MapSO) value);
				list.add(".out()");
				list.margemDec();
			}
		}		
	}
	
	public static ListString get(MapSO map) {
		return new MapSoToTest(map).list;
	}
	
	public static void main(String[] args) {
		MapSO map = MapSoFromJson.get("{\"idProduto\":7,\"quantidadeDeParcelas\":72,\"valorSolicitado\":54167.5,\"beneficioAniversario\":true,\"dataParaVencimento\":null,\"dataParaLiberacao\":\"20191007\"}");
		get(map).print();
	}
		
}
