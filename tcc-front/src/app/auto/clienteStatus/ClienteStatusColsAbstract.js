/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class ClienteStatusColsAbstract {

	NOME;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(40, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("ClienteStatus-Cols-nome");
		this.list = [this.NOME];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteStatusCols", this);
	}
}
