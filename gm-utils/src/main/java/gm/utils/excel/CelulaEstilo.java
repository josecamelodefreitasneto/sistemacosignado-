package gm.utils.excel;

import lombok.Getter;

@Getter
public class CelulaEstilo {
	
	public static final int TString = 0;
	public static final int TNumber = 1;
	public static final int TDate = 2;
	public static final int TDateTime = 3;
	public static final int TBoolean = 4;
	
	private String field;
	private String title;
	private String format;
	private Integer tipo;
	private int ordem;
	
	private Boolean totalizarSoma;
	private Boolean totalizarMenor;
	private Boolean totalizarMaior;
	private Boolean totalizarMedia;
	private Boolean contarDistintos;
	
	@Getter
	private ConfiguracaoEstilo configuracaoEstilo;

	public CelulaEstilo(String field, String title, int ordem, Integer tipo, ConfiguracaoEstilo configuracaoEstilo) {
		this.field = field;
		this.title = title;
		this.ordem = ordem;
		this.tipo = tipo;
		this.configuracaoEstilo = configuracaoEstilo;
	}
	
	public CelulaEstilo(String field, String title, Integer tipo, ConfiguracaoEstilo configuracaoEstilo) {
		this.field = field;
		this.title = title;
		this.tipo = tipo;
		this.ordem = 0;
		this.configuracaoEstilo = configuracaoEstilo;
	}
	
//	public CelulaEstilo(Coluna coluna, Integer tipo, ConfiguracaoEstilo configuracaoEstilo) {
//		this.field = coluna.getNome();
//		this.title = coluna.getTitulo();
//		this.ordem = coluna.getOrdem();
//		this.totalizarSoma = coluna.getTotalizarSoma();
//		this.totalizarMenor = coluna.getTotalizarMenor();
//		this.totalizarMaior = coluna.getTotalizarMaior();
//		this.totalizarMedia = coluna.getTotalizarMedia();
//		this.contarDistintos = coluna.getContarDistintos();
//		this.tipo = tipo;
//		this.configuracaoEstilo = configuracaoEstilo;
//	}
	
}
