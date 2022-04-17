/* tcc-java */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UIdText from '../../misc/utils/UIdText';
import UString from '../../misc/utils/UString';

export default class ClienteColsAbstract {

	NOME;
	CPF;
	STATUS;
	ATENDENTE_RESPONSAVEL;
	TIPO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(300, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(true).setId("Cliente-Cols-nome");
		this.CPF = new Coluna(150, "CPF", o => o.getCpf(), TextAlign.center).setSort((a, b) => UString.compare(a.getCpf(), b.getCpf())).setGrupo(true).setId("Cliente-Cols-cpf");
		this.STATUS = new Coluna(300, "Status", o => o.getStatus(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getStatus(), b.getStatus())).setGrupo(true).setId("Cliente-Cols-status");
		this.ATENDENTE_RESPONSAVEL = new Coluna(300, "Atendente Responsável", o => o.getAtendenteResponsavel(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getAtendenteResponsavel(), b.getAtendenteResponsavel())).setGrupo(true).setId("Cliente-Cols-atendenteResponsavel");
		this.TIPO = new Coluna(300, "Tipo", o => o.getTipo(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getTipo(), b.getTipo())).setGrupo(true).setId("Cliente-Cols-tipo");
		this.list = [this.NOME, this.CPF, this.STATUS, this.ATENDENTE_RESPONSAVEL, this.TIPO];
		this.grupos = [new Coluna(450, "Dados Pessoais", null, TextAlign.center).setCols(2), new Coluna(600, "Situação", null, TextAlign.center).setCols(2), new Coluna(300, "Dados Funcionais", null, TextAlign.center).setCols(1)];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteCols", this);
	}
}
