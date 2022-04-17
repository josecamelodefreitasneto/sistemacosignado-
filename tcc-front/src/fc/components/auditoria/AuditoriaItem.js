/* front-constructor */
export default class AuditoriaItem {

	id = 0;
	idTipo = 0;
	tipo;
	usuario;
	data;
	tempo;
	alteracoes;

	getId() {
		return this.id;
	}

	setId(value) {
		this.id = value;
	}

}
