/* front-constructor */
import UCommons from '../../app/misc/utils/UCommons';
import UInteger from '../../app/misc/utils/UInteger';
import UString from '../../app/misc/utils/UString';

export default class EntityFront {

	static referenceCount = 0;

	referenceId = ++EntityFront.referenceCount;
	id;
	observacoes = null;
	observacoesHouveMudancas = false;
	houveMudancas = false;
	stringify = () => {
		let s = this.asString();
		s = UString.replace(s, " ,", ",");
		s = UString.replace(s, ",,", ",");
		s = UString.replace(s, " }", "}");
		s = UString.replace(s, ",}", "}");
		s = UString.replace(s, " ]", "]");
		s = UString.replace(s, ",]", "]");
		return s;
	};

	getId() {
		return this.id;
	}

	setId(value) {
		if (UCommons.notEmpty(this.id) && !UInteger.equals(this.id, value)) {
			throw new Error("Alteracao de id: de " + this.id + " para " + value);
		} else if (UCommons.isEmpty(value)) {
			this.id = null;
		} else {
			this.id = value;
		}
	}
	getObservacoes() {
		return this.observacoes;
	}
	setObservacoes(value) {
		this.observacoes = value;
	}
	getReferenceId() {
		return this.referenceId;
	}
	getObservacoesHouveMudancas() {
		return this.observacoesHouveMudancas;
	}
	setObservacoesHouveMudancas(value) {
		this.observacoesHouveMudancas = value;
	}
	getHouveMudancas() {
		return this.houveMudancas;
	}
	setHouveMudancas(value) {
		this.houveMudancas = value;
	}
}
