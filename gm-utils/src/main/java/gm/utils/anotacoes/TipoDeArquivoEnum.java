package gm.utils.anotacoes;

import java.util.Arrays;

public enum TipoDeArquivoEnum {
	IMAGEM(1) {
		@Override
		public ExtensaoDeArquivoEnum[] extensoes() {
			ExtensaoDeArquivoEnum[] arr = { ExtensaoDeArquivoEnum.JPG, ExtensaoDeArquivoEnum.JPEG, ExtensaoDeArquivoEnum.PNG, ExtensaoDeArquivoEnum.GIF, };
			return arr;
		}
		@Override
		public String accept() {
			return "image/png,image/jpeg,image/gif";
		}

	},
	TEXTO(2) {
		@Override
		public ExtensaoDeArquivoEnum[] extensoes() {
			ExtensaoDeArquivoEnum[] arr = { ExtensaoDeArquivoEnum.PDF, ExtensaoDeArquivoEnum.DOC, ExtensaoDeArquivoEnum.DOCX,
					ExtensaoDeArquivoEnum.ODS, ExtensaoDeArquivoEnum.ODT, ExtensaoDeArquivoEnum.PPT, ExtensaoDeArquivoEnum.PPTX,
					ExtensaoDeArquivoEnum.RTF, ExtensaoDeArquivoEnum.TXT, ExtensaoDeArquivoEnum.XLS,
					ExtensaoDeArquivoEnum.XLSX };
			return arr;
		}
		@Override
		public String accept() {
			return ".pdf,.doc,.docx,.ods,.odt,.ppt,.pptx,.rtf,.xls,.xlsx";
		}
	},
	QUALQUER(0) {
		private ExtensaoDeArquivoEnum[] extensoes = null;
		private ExtensaoDeArquivoEnum[] append(ExtensaoDeArquivoEnum[] arr, ExtensaoDeArquivoEnum element) {
		    final int N = arr.length;
		    arr = Arrays.copyOf(arr, N + 1);
		    arr[N] = element;
		    return arr;
		}
		private void add(ExtensaoDeArquivoEnum[] arr){
			if(arr == null) {
				return;
			}
			for (ExtensaoDeArquivoEnum e : arr) {
				if (e == null) {
					continue;
				}
				extensoes = append(extensoes, e);
			}
		}
		@Override
		public ExtensaoDeArquivoEnum[] extensoes() {
			extensoes = IMAGEM.extensoes();
			add(TEXTO.extensoes());
			return extensoes;
		}
		@Override
		public String accept() {
			return IMAGEM.accept() + "," + TEXTO.accept();
		}
	};

	TipoDeArquivoEnum(int id) {
		this.tipo = id;
	}

	public abstract ExtensaoDeArquivoEnum[] extensoes();
	public abstract String accept();

	private int tipo;
	public int getTipo() {
		return this.tipo;
	}
}
