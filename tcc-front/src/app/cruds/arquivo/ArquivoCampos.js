/* front-constructor */
import ArquivoCamposAbstract from '../../auto/arquivo/ArquivoCamposAbstract';
import ResourcesFc from '../../../resources/ResourcesFc';
import Sessao from '../../../projeto/Sessao';
import UString from '../../misc/utils/UString';

export default class ArquivoCampos extends ArquivoCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ArquivoCampos", () => new ArquivoCampos(), o => o.init());
	}

	isImage() {
		return this.extensaoIn(ArquivoCampos.EXTENSOES_IMAGEM);
	}

	isPdf() {
		return this.extensaoIn(ArquivoCampos.EXTENSOES_PDF);
	}
	isXls() {
		return this.extensaoIn(ArquivoCampos.EXTENSOES_XLS);
	}
	extensaoIn(itens) {
		return itens.contains(this.getExtensao());
	}

	getExtensao() {
		if (this.uri.isEmpty()) {
			return null;
		}
		let value = UString.afterLast(this.nome.get(), ".");
		if (UString.isEmpty(value)) {
			return null;
		}
		return value.toLowerCase().trim();
	}

	getIcone() {
		if (this.isPdf()) return ResourcesFc.pdf;
		if (this.isXls()) return ResourcesFc.xls;
		return ResourcesFc.download;
	}

}
ArquivoCampos.EXTENSOES_IMAGEM = ["png","jpg","bmp","jpeg"];
ArquivoCampos.EXTENSOES_PDF = ["pdf"];
ArquivoCampos.EXTENSOES_XLS = ["xls","xlsx"];
