/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class AtendenteColsAbstract {

	NOME;
	E_MAIL;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(300, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("Atendente-Cols-nome");
		this.E_MAIL = new Coluna(150, "E-mail", o => o.getEmail(), TextAlign.center).setSort((a, b) => UString.compare(a.getEmail(), b.getEmail())).setGrupo(false).setId("Atendente-Cols-email");
		this.list = [this.NOME, this.E_MAIL];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("AtendenteCols", this);
	}
}
