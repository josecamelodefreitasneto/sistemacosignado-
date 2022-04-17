package gm.utils.excel;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gm.utils.string.UString;

public class TituloColunaEstilo {

	@SuppressWarnings("rawtypes")
	public Integer gerarTitulosColunas(XSSFWorkbook workbook, XSSFSheet sheet, List<CelulaEstilo> colunas) {
		XSSFRow row = sheet.createRow(sheet.getLastRowNum());
		int colunaInicial = 0;
		CriaCelula criaCelula = new CriaCelula(workbook);
		
		for (Iterator iterator = colunas.iterator(); iterator.hasNext();) {
			CelulaEstilo celulaEstilo = (CelulaEstilo) iterator.next();
			
			ConfiguracaoEstilo configuracaoEstilo = celulaEstilo.getConfiguracaoEstilo();
			
			if (configuracaoEstilo.isNovaLinha()) {
				int lastRowNum = sheet.getLastRowNum();
				row = sheet.createRow(++lastRowNum);
				colunaInicial = criaCelula.getPosicaoInicialColunaNovaLinha(row, configuracaoEstilo);
			}
			
			if (configuracaoEstilo.isJuntarComCelulaAnterior()) {
				continue;
			} 
			
			XSSFCell cell = criaCelula.novaCelula(sheet, row, colunaInicial, celulaEstilo.getTitle());
			
			int lastCol = criaCelula.configurarMergeColunas(sheet, row, colunaInicial, configuracaoEstilo);
			
			criaCelula.configurarEstiloColuna(workbook, configuracaoEstilo, cell);
			
			colunaInicial = lastCol + 1;
			
			removeColunasSemField(iterator, celulaEstilo);
		}
		
		return sheet.getLastRowNum();
	}

	@SuppressWarnings("rawtypes")
	private void removeColunasSemField(Iterator iterator, CelulaEstilo coluna) {
		if (UString.isEmpty(coluna.getField())) {
			iterator.remove();
		}
	}

}
