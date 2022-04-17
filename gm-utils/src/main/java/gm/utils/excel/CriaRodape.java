package gm.utils.excel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gm.utils.comum.UConstantes;
import gm.utils.map.MapSO;
import gm.utils.number.UDouble;
import gm.utils.string.UString;
import lombok.Getter;

public class CriaRodape {
	
	private final CriaCelula criaCelula;
	private Map<TipoTotalizador, XSSFRow> mapaLinhasTotalizadores; 
	private Map<String, MapSO> mapaTotaisRodape;
	private List<CelulaEstilo> colunas;
	private Set<String> totalizadoresCriados;
	private final XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private Integer countColunasJuntas = 0;
	
	private CriaRodape(List<CelulaEstilo> colunas, Map<String, MapSO> mapaTotaisRodape, XSSFWorkbook workbook, XSSFSheet sheet) {
		this.colunas = colunas;
		this.mapaTotaisRodape = mapaTotaisRodape;
		this.workbook = workbook;
		this.sheet = sheet;
		this.criaCelula = new CriaCelula(this.workbook);
	}
	
	public static CriaRodape getInstance(List<CelulaEstilo> colunas, Map<String, MapSO> mapaTotaisRodape, XSSFWorkbook workbook, XSSFSheet sheet) {
		return new CriaRodape(colunas, mapaTotaisRodape, workbook, sheet);
	}
	
	public void build(Integer rowCount) {
		ConfiguracaoEstilo configuracaoEstilo = ConfiguracaoEstilo.getInstanceTitulo();
		configuracaoEstilo.setTextoAlinhamento(HorizontalAlignment.LEFT);
		
		if (isMapaTotaisRodapeEmpty()) {
			criarRodapeVazio(rowCount, configuracaoEstilo);
		} else {
			criarRodapeTotalizadores(rowCount, configuracaoEstilo);
		}
	}

	private boolean isMapaTotaisRodapeEmpty() {
		for (String chave : mapaTotaisRodape.keySet()) {
			MapSO mapSO = mapaTotaisRodape.get(chave);
			if (mapSO != null) {
				return false;
			}
		}
		return true;
	}
	
	private void criarRodapeVazio(Integer rowCount, ConfiguracaoEstilo configuracaoEstilo) {
		XSSFRow row = sheet.createRow(rowCount);
		
		configuracaoEstilo.setColspan(colunas.size());
		
		criaCelula.configurarMergeColunas(sheet, row, 0, configuracaoEstilo);
		XSSFCell cell = criaCelula.novaCelula(sheet, row, 0, "");
		criaCelula.configurarEstiloColuna(workbook, configuracaoEstilo, cell);
	}
	
	private void criarRodapeTotalizadores(Integer rowCount, ConfiguracaoEstilo configuracaoEstilo) {
		criarLinhasTotalizadores(rowCount, configuracaoEstilo);
		verificarColunasJuntadas();
		
		for (CelulaEstilo celulaEstilo : colunas) {
			if (celulaEstilo.getTotalizarSoma()) {
				configurarCelula(configuracaoEstilo, celulaEstilo, TipoTotalizador.SOMA);
			}
			
			if (celulaEstilo.getTotalizarMaior()) {
				configurarCelula(configuracaoEstilo, celulaEstilo, TipoTotalizador.MAIOR);
			}
			
			if (celulaEstilo.getTotalizarMenor()) {
				configurarCelula(configuracaoEstilo, celulaEstilo, TipoTotalizador.MENOR);
			}

			if (celulaEstilo.getTotalizarMedia()) {
				configurarCelula(configuracaoEstilo, celulaEstilo, TipoTotalizador.MEDIA);
			}
			
			if (celulaEstilo.getContarDistintos()) {
				configurarCelula(configuracaoEstilo, celulaEstilo, TipoTotalizador.DISTINTOS);
			}
		}
	}
	
	private void verificarColunasJuntadas() {
		for (CelulaEstilo celulaEstilo : colunas) {
			if (celulaEstilo.getConfiguracaoEstilo().isJuntarComCelulaAnterior()) {
				countColunasJuntas ++;
			}
		}
	}

	private void configurarCelula(ConfiguracaoEstilo configuracaoEstilo, CelulaEstilo celulaEstilo, TipoTotalizador tipoTotalizador) {
		XSSFRow row = mapaLinhasTotalizadores.get(tipoTotalizador);

		criarColunaLegendaTotalizador(row, configuracaoEstilo, celulaEstilo, tipoTotalizador);
		criarColunaValorTotalizador(row, configuracaoEstilo, celulaEstilo, tipoTotalizador);
	}
	
	private void criarLinhasTotalizadores(Integer rowCount, ConfiguracaoEstilo configuracaoEstilo) {
		mapaLinhasTotalizadores = new HashMap<TipoTotalizador, XSSFRow>();
		totalizadoresCriados = new HashSet<String>();
		
		for (String chave : mapaTotaisRodape.keySet()) {
			TipoTotalizador tipoTotalizador = TipoTotalizador.getTipoTotalizador(chave);
			XSSFRow row = sheet.createRow(rowCount++);
			
			mapaLinhasTotalizadores.put(tipoTotalizador, row);
		}
	}
	
	private void criarColunaLegendaTotalizador(XSSFRow row, ConfiguracaoEstilo configuracaoEstilo, CelulaEstilo celulaEstilo, TipoTotalizador tipoTotalizador) {
		if (!totalizadoresCriados.contains(tipoTotalizador.getChave())) {
			int posicao = celulaEstilo.getOrdem() - countColunasJuntas - 1;
			
			configuracaoEstilo.setTextoAlinhamento(HorizontalAlignment.RIGHT);
			configuracaoEstilo.setColspan(posicao);
			
			criaCelula.configurarMergeColunas(sheet, row, 0, configuracaoEstilo);
			XSSFCell cell = criaCelula.novaCelula(sheet, row, 0, tipoTotalizador.getLegenda());
			criaCelula.configurarEstiloColuna(workbook, configuracaoEstilo, cell);

			totalizadoresCriados.add(tipoTotalizador.getChave());
		}
	}

	private void criarColunaValorTotalizador(XSSFRow row, ConfiguracaoEstilo configuracaoEstilo, CelulaEstilo celulaEstilo, TipoTotalizador tipoTotalizador) {
		String valor = getValor(celulaEstilo, tipoTotalizador.chave);
		XSSFCell cell = null;
		
		int posicao = celulaEstilo.getOrdem() - countColunasJuntas - 1;
		
		if (UString.isEmpty(valor)) {
			cell = criaCelula.novaCelula(sheet, row, posicao, "-");
		} else {
			cell = criaCelula.novaCelula(sheet, row, posicao, valor);
		}

		criaCelula.configurarEstiloColuna(workbook, configuracaoEstilo, cell);
	}

	private String getValor(CelulaEstilo coluna, String totalizador) {
		MapSO mapSO = mapaTotaisRodape.get(totalizador);
		Object valor = mapSO.get(coluna.getField());
		if (valor == null) {
			return "";
		}
		return UDouble.format(UDouble.toDouble(valor.toString().replace(".", "").replace(",", ".")), 2);
	}
	
	private enum TipoTotalizador {
		SOMA("soma", "Soma"),
		MEDIA("media", "M"+UConstantes.e_agudo+"dia"),
		MENOR("menor", "Menor"),
		MAIOR("maior", "Maior"),
		DISTINTOS("distintos", "Distintos");
		
		@Getter
		private String chave;
		
		@Getter
		private String legenda;
		
		private TipoTotalizador(String chave, String legenda) {
			this.chave = chave;
			this.legenda = legenda;
		}
		
		public static TipoTotalizador getTipoTotalizador(String chaveTotalizador) {
			for (TipoTotalizador totalizador : TipoTotalizador.values()) {
				if (totalizador.getChave().equals(chaveTotalizador)) {
					return totalizador;
				}
			}
			
			return null;
		}
	}
}
