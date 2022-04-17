package gm.utils.anotacoes;

import lombok.Getter;

public enum TamanhoDeArquivo {
	MB1(1, "1 MB"), MB2(2, "2 MB"), MB5(5, "5 MB"), MB10(10, "10 MB"), MB20(20, "20 MB"), MB40(40, "40 MB"), MB100(100, "100 MB");
	
	@Getter
	private int bytes;
	
	@Getter
	private String descricao;
	
	private TamanhoDeArquivo(int mbs, String descricao){
		this.bytes = mbs * 1048576;
		this.descricao = descricao;
	}
	
}
