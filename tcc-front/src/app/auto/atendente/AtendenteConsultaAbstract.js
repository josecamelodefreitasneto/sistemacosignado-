/* tcc-java */
import AtendenteUtils from '../../cruds/atendente/AtendenteUtils';
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class AtendenteConsultaAbstract extends Consulta {

	dados;
	email;
	nome;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Atendente";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(AtendenteUtils.getInstance().fromJsonList(array));
			},
			"atendente/consulta",
			() => this.getTo()
		);
		this.email = this.newEmail("E-mail");
		this.nome = this.newNomeProprio("Nome");
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
		Sessao.checkInstance("AtendenteConsulta", this);
	}
}
