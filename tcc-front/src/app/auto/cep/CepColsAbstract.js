/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class CepColsAbstract {

	NUMERO;
	UF;
	CIDADE;
	BAIRRO;
	LOGRADOURO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NUMERO = new Coluna(20, "Número", o => o.getNumero(), TextAlign.left).setSort((a, b) => UString.compare(a.getNumero(), b.getNumero())).setGrupo(false).setId("Cep-Cols-numero");
		this.UF = new Coluna(200, "UF", o => o.getUf(), TextAlign.left).setSort((a, b) => UString.compare(a.getUf(), b.getUf())).setGrupo(false).setId("Cep-Cols-uf");
		this.CIDADE = new Coluna(200, "Cidade", o => o.getCidade(), TextAlign.left).setSort((a, b) => UString.compare(a.getCidade(), b.getCidade())).setGrupo(false).setId("Cep-Cols-cidade");
		this.BAIRRO = new Coluna(200, "Bairro", o => o.getBairro(), TextAlign.left).setSort((a, b) => UString.compare(a.getBairro(), b.getBairro())).setGrupo(false).setId("Cep-Cols-bairro");
		this.LOGRADOURO = new Coluna(200, "Logradouro", o => o.getLogradouro(), TextAlign.left).setSort((a, b) => UString.compare(a.getLogradouro(), b.getLogradouro())).setGrupo(false).setId("Cep-Cols-logradouro");
		this.list = [this.NUMERO, this.UF, this.CIDADE, this.BAIRRO, this.LOGRADOURO];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("CepCols", this);
	}
}
