/* front-constructor */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';
import UsuarioUtils from '../../cruds/usuario/UsuarioUtils';

export default class UsuarioConsultaAbstract extends Consulta {

	dados;
	login;
	nome;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Usuario";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(UsuarioUtils.getInstance().fromJsonList(array));
			},
			"usuario/consulta",
			() => this.getTo()
		);
		this.login = this.newEmail("Login");
		this.nome = this.newString("Nome", 60);
		this.excluido = this.newBoolean("Excluido");
		this.registroBloqueado = this.newBoolean("Registro Bloqueado");
	}
	consultar() {
		this.dados.clearItens();
		this.dados.carregar();
	}
	getTo() {
		this.checkInstance();
		const o = {};
		o.busca = this.getBusca().get();
		return o;
	}
	getDataSource() {
		if (UCommons.isEmpty(this.dados)) {
			return [];
		} else {
			return this.dados.getItens();
		}
	}
	checkInstance() {
		Sessao.checkInstance("UsuarioConsulta", this);
	}
}
