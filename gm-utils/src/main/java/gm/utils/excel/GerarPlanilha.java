package gm.utils.excel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Setter;

@Setter
public class GerarPlanilha {

	private String titulo;
	
	private List<?> dados;
	private Map<String, MapSO> mapaTotaisRodape = new LinkedHashMap<String, MapSO>();
	private List<CelulaEstilo> colunas = new ArrayList<CelulaEstilo>();

	public GerarPlanilha(String titulo, List<?> dados) {
		this.titulo = titulo;
		this.dados = dados;
	}
	
	public GerarPlanilha(String titulo, List<?> dados, ListString fields) {
		this(titulo, dados);
		add(fields);
	}
	
	public GerarPlanilha(String titulo, List<?> dados, String... fields) {
		this(titulo, dados, new ListString(fields));
	}
	
	public XSSFWorkbook build() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		workbook.setSheetName(0, "Planilha 1");
		
		int rowCount = CriaTitulo.getInstance(colunas, workbook, sheet).build(titulo);

		int rowInicialDadoNum = rowCount;
		
		rowCount = CriaDados.getInstance(colunas, dados, workbook, sheet).build(rowCount);
		
		CriaRodape.getInstance(colunas, mapaTotaisRodape, workbook, sheet).build(rowCount);

		configurarAutoSize(workbook);
		
		criarFiltroAutomatico(sheet, rowInicialDadoNum, workbook);
		
		try {
			return workbook;
		} catch (Exception e) {
			UException.printTrace(e);
		}
		
		return null;
	}
	
	private void configurarAutoSize(XSSFWorkbook workbook) {
		XSSFRow row = workbook.getSheetAt(0).getRow(0);
		for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
			workbook.getSheetAt(0).autoSizeColumn(colNum);
		}
	}

	private void criarFiltroAutomatico(XSSFSheet sheet, int rowInicialDadoNum, XSSFWorkbook workbook) {
		int lastRowNum = sheet.getLastRowNum() - 1;
		
		XSSFRow lastRow = sheet.getRow(lastRowNum);
		
		int lasCellNum = lastRow.getLastCellNum() - 1;
		
		if (lasCellNum < 0) {
			lasCellNum = 0;
		}		
		
		CellRangeAddress region = new CellRangeAddress(rowInicialDadoNum, sheet.getLastRowNum(), 0, lasCellNum);
		
		sheet.setAutoFilter(region);
	}
	
	public void addTotalRodape(String chave, MapSO valoresCampo) {
		this.mapaTotaisRodape.put(chave, valoresCampo);
	}
	
	public GerarPlanilha add(Class<?> c, String field, String title, Integer ordem, ConfiguracaoEstilo estilo) {
		if ( c.equals(String.class) ) {
			add(field, title, ordem, estilo);
		} else if ( UType.isData(c) ) {
			addDate(field, title, ordem, estilo);
		} else if ( c.equals(Boolean.class) || c.equals(boolean.class) ) {
			addBoolean(field, title, ordem, estilo);
		} else if ( c.equals(BigDecimal.class) || c.equals(Integer.class) || c.equals(int.class) || c.equals(Double.class) || c.equals(double.class) ) {
			addNumber(field, title, ordem, estilo);
		} else {
			add(field, title, ordem, estilo);
		}
		return this;
	}
	
	public GerarPlanilha add(ListString fields) {
		for (String field : fields) {
			String title;
			if (field.contains(":")) {
				title = UString.afterFirst(field, ":");
				field = UString.beforeFirst(field, ":");
			} else {
				title = field;
			}
			title = UString.toCamelCaseSepare(title);
			add(field, title, null);
		}
		return this;
	}
	
	
	private GerarPlanilha add(String field, String title, Integer ordem) {
		return add(field, title, ordem, ConfiguracaoEstilo.getInstanceTitulo());
	}
	private GerarPlanilha add(String field, String title, Integer ordem, ConfiguracaoEstilo configuracaoEstilo) {
		colunas.add(new CelulaEstilo(field, title, ordem, CelulaEstilo.TString, configuracaoEstilo));
		return this;
	}
	
	private GerarPlanilha addDate(String field, String title, Integer ordem, ConfiguracaoEstilo configuracaoEstilo) {
		colunas.add(new CelulaEstilo(field, title, ordem, CelulaEstilo.TDate, configuracaoEstilo));
		return this;
	}
	
	private GerarPlanilha addNumber(String field, String title, Integer ordem, ConfiguracaoEstilo configuracaoEstilo) {
		colunas.add(new CelulaEstilo(field, title, ordem, CelulaEstilo.TNumber, configuracaoEstilo));
		return this;
	}
	
	private GerarPlanilha addBoolean(String field, String title, Integer ordem, ConfiguracaoEstilo configuracaoEstilo) {
		colunas.add(new CelulaEstilo(field, title, ordem, CelulaEstilo.TBoolean, configuracaoEstilo));
		return this;
	}

}
