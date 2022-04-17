/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UString from '../../misc/utils/UString';

export default class ImportacaoArquivoErroAbstract extends EntityFront {

	original;
	erro = null;
	importacaoArquivo = null;
	linha = 0;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return UString.toString(this.getErro());
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (this.getObservacoes() !== null) {
			s += "\"observacoes\":[";
			s += this.getObservacoes().reduce((ss, o) => ss + o.asString() + ",", "");
			s += "],";
		}
		s += "\"excluido\":"+this.excluido+",";
		s += "}";
		return s;
	}
	getOriginal() {
		return this.original;
	}
	setOriginal(value) {
		this.original = value;
	}
	getErro() {
		return this.erro;
	}
	setErro(value) {
		this.erro = value;
	}
	getImportacaoArquivo() {
		return this.importacaoArquivo;
	}
	setImportacaoArquivo(value) {
		this.importacaoArquivo = value;
	}
	getLinha() {
		return this.linha;
	}
	setLinha(value) {
		this.linha = value;
	}
	getExcluido() {
		return this.excluido;
	}
	setExcluido(value) {
		this.excluido = value;
	}
	getRegistroBloqueado() {
		return this.registroBloqueado;
	}
	setRegistroBloqueado(value) {
		this.registroBloqueado = value;
	}
}
