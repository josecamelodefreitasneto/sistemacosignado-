/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class ClienteTipoColsAbstract {

	NOME;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(22, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("ClienteTipo-Cols-nome");
		this.list = [this.NOME];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteTipoCols", this);
	}
}
