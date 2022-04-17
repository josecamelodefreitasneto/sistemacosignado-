/* front-constructor */
import EntityFront from '../../../fc/components/EntityFront';
import UCommons from '../../misc/utils/UCommons';

export default class ObservacaoAbstract extends EntityFront {

	original;
	anexo = null;
	texto = null;
	excluido = false;
	registroBloqueado = false;
	setId(value) {
		super.setId(value);
	}
	getText() {
		return this.getTexto();
	}
	asString() {
		let s = "{";
		s += "\"id\":"+this.getId()+",";
		if (UCommons.notEmpty(this.anexo)) {
			s += "\"anexo\":"+this.anexo.asString()+",";
		}
		if (UCommons.notEmpty(this.texto)) {
			s += "\"texto\":\""+this.texto+"\",";
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
	getAnexo() {
		return this.anexo;
	}
	setAnexo(value) {
		this.anexo = value;
	}
	getTexto() {
		return this.texto;
	}
	setTexto(value) {
		this.texto = value;
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
