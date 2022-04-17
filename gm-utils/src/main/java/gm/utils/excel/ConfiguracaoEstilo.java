package gm.utils.excel;

import java.awt.Color;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfiguracaoEstilo {
	
	private int colspan = 1;
	private int rowspan = 1;
	private boolean novaLinha;
	private boolean fonteNegrito = false;
	private boolean juntarComCelulaAnterior = false;
	
	private HorizontalAlignment textoAlinhamento = HorizontalAlignment.LEFT;
	private Color corDeFundo = Color.WHITE;
	private Color corDaFonte = Color.BLACK;
	private Color corDaBorda = Color.BLACK;
	private Integer colunaInicial;
	
	private ConfiguracaoEstilo() {
		
	}
	
	private static XSSFColor getCor(XSSFWorkbook workbook, Color color) {
		return new XSSFColor(color, workbook.getStylesSource().getIndexedColors());
	}
	
	public XSSFColor getCorDeFundo(XSSFWorkbook workbook) {
		return getCor(workbook, getCorDeFundo());
	}
	public XSSFColor getCorDaFonte(XSSFWorkbook workbook) {
		return getCor(workbook, getCorDaFonte());
	}
	public XSSFColor getCorDaBorda(XSSFWorkbook workbook) {
		return getCor(workbook, getCorDaBorda());
	}
	
	public static ConfiguracaoEstilo getInstanceTitulo() {
		ConfiguracaoEstilo o = new ConfiguracaoEstilo();
		o.setTextoAlinhamento(HorizontalAlignment.CENTER);
		o.setFonteNegrito(true);
		o.setCorDaFonte(Color.WHITE);
		return o;
	}
	
	public static ConfiguracaoEstilo getInstancePadrao() {
		return new ConfiguracaoEstilo();
	}
	
}
