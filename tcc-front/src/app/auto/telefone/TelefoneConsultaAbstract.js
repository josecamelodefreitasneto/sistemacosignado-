/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import TelefoneUtils from '../../cruds/telefone/TelefoneUtils';
import UCommons from '../../misc/utils/UCommons';

export default class TelefoneConsultaAbstract extends Consulta {

	dados;
	ddd;
	nome;
	numero;
	recado;
	whatsapp;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Telefone";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(TelefoneUtils.getInstance().fromJsonList(array));
			},
			"telefone/consulta",
			() => this.getTo()
		);
		this.ddd = this.newInteger("DDD");
		this.nome = this.newString("Como vai aparecer", 28);
		this.numero = this.newString("Número", 9);
		this.recado = this.newBoolean("Recado");
		this.whatsapp = this.newBoolean("WhatsApp");
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
		Sessao.checkInstance("TelefoneConsulta", this);
	}
}
