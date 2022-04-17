package gm.utils.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CriaCelula {
	
	private XSSFWorkbook workbook;

	public CriaCelula(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public XSSFCell novaCelula(XSSFSheet sheet, XSSFRow row, int colunaInicial, String valor) {
		XSSFCell cell = row.createCell(colunaInicial);
		cell.setCellValue(valor);
		sheet.autoSizeColumn(colunaInicial);
		return cell;
	}

	public int configurarMergeColunas(XSSFSheet sheet, XSSFRow row, int colunaInicial,
			ConfiguracaoEstilo configuracaoEstilo) {
		int firstRow = row.getRowNum();
		int lastRow = firstRow + configuracaoEstilo.getRowspan();
		
		int lastCol = colunaInicial + configuracaoEstilo.getColspan();
		
		if (firstRow != lastRow || colunaInicial != lastCol) {
			sheet.addMergedRegion(new CellRangeAddress(
				firstRow,
				lastRow,
				colunaInicial,
				lastCol
			));
		}
		return lastCol;
	}
	
	public void configurarEstiloColuna(XSSFWorkbook workbook, ConfiguracaoEstilo estilo, XSSFCell cell) {
		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setColor(estilo.getCorDaFonte(workbook));

		configurarCorDeFundo(estilo, style);
		configurarFonteNegrito(estilo, style, font);
		configurarAlinhamento(estilo, style);
		configurarBorda(estilo, style);

		cell.setCellStyle(style);
	}
	
	private void configurarFonteNegrito(ConfiguracaoEstilo configuracaoEstilo, XSSFCellStyle style, XSSFFont font) {
		if (configuracaoEstilo.isFonteNegrito()) {
			font.setFontHeightInPoints((short) 10);
		    font.setBold(Boolean.TRUE);
		    style.setFont(font);
		}
	}

	private void configurarCorDeFundo(ConfiguracaoEstilo configuracaoEstilo, XSSFCellStyle style) {
		if (configuracaoEstilo.getCorDeFundo() != null) {
		    style.setFillForegroundColor(configuracaoEstilo.getCorDeFundo(this.workbook));
		    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
	}

	private void configurarBorda(ConfiguracaoEstilo configuracaoEstilo, XSSFCellStyle style) {
		if (configuracaoEstilo.getCorDaBorda() != null) {
			XSSFColor corDaBorda = configuracaoEstilo.getCorDaBorda(this.workbook);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(corDaBorda);
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(corDaBorda);
			style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(corDaBorda);
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(corDaBorda);
		}
	}

	private void configurarAlinhamento(ConfiguracaoEstilo configuracaoEstilo, XSSFCellStyle style) {
		style.setAlignment(configuracaoEstilo.getTextoAlinhamento());
	}
	
	public Integer getPosicaoInicialColunaNovaLinha(XSSFRow row, ConfiguracaoEstilo configuracaoEstilo) {
		if (configuracaoEstilo.getColunaInicial() == null) {
			int lastCellNum = row.getLastCellNum();
			
			if (lastCellNum < 0) {
				return 0;
			}
			
			return lastCellNum++;
		}
		
		return configuracaoEstilo.getColunaInicial();
	}
}
