/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import CepUtils from '../../cruds/cep/CepUtils';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class CepConsultaAbstract extends Consulta {

	dados;
	bairro;
	cidade;
	logradouro;
	numero;
	uf;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Cep";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(CepUtils.getInstance().fromJsonList(array));
			},
			"cep/consulta",
			() => this.getTo()
		);
		this.bairro = this.newString("Bairro", 100);
		this.cidade = this.newString("Cidade", 100);
		this.logradouro = this.newString("Logradouro", 100);
		this.numero = this.newString("Número", 10);
		this.uf = this.newString("UF", 100);
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
		Sessao.checkInstance("CepConsulta", this);
	}
}
