package gm.utils.excel;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gm.utils.string.UString;

public class CriaTitulo {
	
	private List<CelulaEstilo> colunas;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private CriaTitulo(List<CelulaEstilo> colunas, XSSFWorkbook workbook, XSSFSheet sheet) {
		this.colunas = colunas;
		this.workbook = workbook;
		this.sheet = sheet;
	}
	
	public static CriaTitulo getInstance(List<CelulaEstilo> colunas, XSSFWorkbook workbook, XSSFSheet sheet) {
		return new CriaTitulo(colunas, workbook, sheet);
	}
	
	public Integer build(String titulo) {

		Integer quantidadeColunaDados = getQuantidadeColunaDados();
		ConfiguracaoEstilo configuracaoEstilo = ConfiguracaoEstilo.getInstanceTitulo();
		configuracaoEstilo.setColspan(quantidadeColunaDados);
		configuracaoEstilo.setNovaLinha(true);
		CelulaEstilo celulaEstilo = new CelulaEstilo(null, titulo, CelulaEstilo.TString, configuracaoEstilo);
		
		colunas.add(0, celulaEstilo);
		
		return criarTituloColuna();
	}

	private Integer getQuantidadeColunaDados() {
		int count = 0;
		
		for (CelulaEstilo coluna : colunas) {
			if (!UString.isEmpty(coluna.getField()) && !coluna.getConfiguracaoEstilo().isJuntarComCelulaAnterior()) {
				count++;
			}
		}
		
		return count;
	}
	
	private int criarTituloColuna() {
		int rowCount = 0;
		
		CelulaEstilo celulaEstilo = (CelulaEstilo) colunas.get(1);
		ConfiguracaoEstilo configuracaoEstilo = celulaEstilo.getConfiguracaoEstilo();
		
		configuracaoEstilo.setNovaLinha(true); 
		
		TituloColunaEstilo tituloColunaEstilo = new TituloColunaEstilo();
		
		rowCount = tituloColunaEstilo.gerarTitulosColunas(workbook, sheet, colunas);
			
		return rowCount;
	}
}
