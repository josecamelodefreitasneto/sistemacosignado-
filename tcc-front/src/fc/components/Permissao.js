/* front-constructor */
import Session from '../../app/estado/Session';

export default class Permissao {

	entidade;

	constructor(entidade) {
		this.entidade = entidade;
	}

	read() {
		return this.comando("read");
	}
	insert() {
		return this.comando("insert");
	}
	update() {
		return this.comando("update");
	}
	delete() {
		return this.comando("delete");
	}
	comando(nome) {
		return Session.getInstance().hasPermissao(this.entidade, nome);
	}
}
