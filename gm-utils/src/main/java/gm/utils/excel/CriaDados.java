package gm.utils.excel;

import java.util.List;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gm.utils.comum.UConstantes;
import gm.utils.string.UString;

public class CriaDados {
	
	private List<?> dados;
	private List<CelulaEstilo> colunas;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private CriaDados(List<CelulaEstilo> colunas, List<?> dados, XSSFWorkbook workbook, XSSFSheet sheet) {
		this.dados = dados;
		this.colunas = colunas;
		this.workbook = workbook;
		this.sheet = sheet;
	}
	
	public static CriaDados getInstance(List<CelulaEstilo> colunas, List<?> dados, XSSFWorkbook workbook, XSSFSheet sheet) {
		return new CriaDados(colunas, dados, workbook, sheet);
	}

	public Integer build(Integer rowCount) {
		ConfiguracaoEstilo configuracaoEstiloPadrao = ConfiguracaoEstilo.getInstancePadrao();
		
		XSSFRow row = sheet.createRow(++rowCount);
		
		colunas.sort((CelulaEstilo o1, CelulaEstilo o2) -> o1.getOrdem() - o2.getOrdem());
		
		CriaCelula criaCelula = new CriaCelula(workbook);

		for (Object dado : dados) {
			int cellCount = 0;

			for (CelulaEstilo coluna : colunas) {
				Object value = null;
				
				XSSFCell cell = null;
				
				if (!UString.isEmpty(coluna.getField())) {
					value = UExcel.getValue(dado, coluna);
				}

				if (coluna.getConfiguracaoEstilo().isJuntarComCelulaAnterior()) {
					XSSFCell cellBefore = row.getCell(cellCount - 1);
					if (value != null) {
						String valueCellBefore = cellBefore.getStringCellValue();
						valueCellBefore = valueCellBefore + "\n" + value;
						cellBefore.setCellValue(valueCellBefore);
					}
				} else {
					cell = criaCelula.novaCelula(sheet, row, cellCount, coluna.getTitle());
					criaCelula.configurarEstiloColuna(workbook, configuracaoEstiloPadrao, cell);

					if (value == null) {
						cell.setCellValue("");
						cellCount++;
						continue;
					}

					configurarValorNaColuna(workbook, dado, coluna, cell, cellCount);
				}
				
				if (!coluna.getConfiguracaoEstilo().isJuntarComCelulaAnterior()) {
					cellCount++;
				}
			}

			row = sheet.createRow(++rowCount);
		}
		
		return rowCount;
	}
	
	private void configurarValorNaColuna(XSSFWorkbook workbook, Object o, CelulaEstilo col, XSSFCell cell, int cellPosition) {
		CreationHelper createHelper = workbook.getCreationHelper();

		if (col.getTipo() == CelulaEstilo.TString) {
			configurarValorString(o, col, cell);
		} else if (col.getTipo() == CelulaEstilo.TDate) {
			configurarCellStyleData(workbook, cell, createHelper, "dd/mm/yyyy");
			configurarValorData(o, col, cell);
		} else if (col.getTipo() == CelulaEstilo.TDateTime) {
			configurarCellStyleData(workbook, cell, createHelper, "dd/mm/yyyy hh:mm");
			configurarValorData(o, col, cell);
		} else if (col.getTipo() == CelulaEstilo.TNumber) {
			configurarValorDouble(o, col, cell, cellPosition);
		} else if (col.getTipo() == CelulaEstilo.TBoolean) {
			configurarValorBoolean(o, col, cell);
		} else {
			configurarValorData(o, col, cell);
		}
	}
	
	private void configurarCellStyleData(XSSFWorkbook workbook, XSSFCell cell, CreationHelper createHelper, String formato) {
		XSSFCellStyle cellStyle = cell.getCellStyle();
		if (cellStyle != null) {
			cellStyle = workbook.createCellStyle();
			cell.setCellStyle(cellStyle);
		}
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(formato));
	}

	private void configurarValorBoolean(Object o, CelulaEstilo coluna, XSSFCell cell) {
		cell.setCellValue(UExcel.getSimNao(o, coluna));
	}

	private void configurarValorDouble(Object o, CelulaEstilo coluna, XSSFCell cell, int cellPosition) {
		cell.setCellValue(UExcel.getDouble(o, coluna));
	}
	
	private void configurarValorData(Object o, CelulaEstilo coluna, XSSFCell cell) {
		cell.setCellValue(UExcel.getCalendar(o, coluna));
	}

	private void configurarValorString(Object o, CelulaEstilo coluna, XSSFCell cell) {
		String valorString = UExcel.getString(o, coluna);
		String before = UString.beforeFirst(valorString, " ");
		if (UConstantes.abreviacoesSemana.contains(before)) {
			valorString = UString.afterFirst(valorString, " ");
			if (valorString.contains(":")) {
				valorString = UString.beforeFirst(valorString, " ");
			}
		}
		cell.setCellValue(valorString);
	}
	
}
