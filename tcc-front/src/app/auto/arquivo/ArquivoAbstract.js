/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class ArquivoAbstract extends EntityFront {

	original;
	nome = null;
	tamanho = null;
	type = null;
	uri = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getNome();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.nome)) {
			s += "\"nome\":\""+this.nome+"\",";
		}
		if (UCommons.notEmpty(this.type)) {
			s += "\"type\":\""+this.type+"\",";
		}
		if (UCommons.notEmpty(this.uri)) {
			s += "\"uri\":\""+this.uri+"\",";
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
	getNome() {
		return this.nome;
	}
	setNome(value) {
		this.nome = value;
	}
	getTamanho() {
		return this.tamanho;
	}
	setTamanho(value) {
		this.tamanho = value;
	}
	getType() {
		return this.type;
	}
	setType(value) {
		this.type = value;
	}
	getUri() {
		return this.uri;
	}
	setUri(value) {
		this.uri = value;
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
