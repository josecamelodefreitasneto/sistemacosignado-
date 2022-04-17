/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import ClienteTipoUtils from '../../cruds/clienteTipo/ClienteTipoUtils';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteTipoConsultaAbstract extends Consulta {

	dados;
	nome;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "ClienteTipo";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ClienteTipoUtils.getInstance().fromJsonList(array));
			},
			"cliente-tipo/consulta",
			() => this.getTo()
		);
		this.nome = this.newString("Nome", 11);
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
		Sessao.checkInstance("ClienteTipoConsulta", this);
	}
}
