package gm.utils.anotacoes;

public enum ExtensaoDeArquivoEnum {
	JPG(1), JPEG(2), PNG(3), GIF(4), PDF(5), DOC(6), DOCX(7), XLS(8), XLSX(9)
	, TXT(10), PPT(11), PPTX(12), RTF(13), ODT(14), ODS(15), RAR(16), ZIP(17), TODAS(0);
	private int id;
	
	ExtensaoDeArquivoEnum(int id){
		this.id = id;
	}
	public int getTipo(){
		return this.id;
	}
}
