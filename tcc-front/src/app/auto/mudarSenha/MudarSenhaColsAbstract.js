/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class MudarSenhaColsAbstract {

	SENHA_ATUAL;
	NOVA_SENHA;
	CONFIRMAR_SENHA;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.SENHA_ATUAL = new Coluna(360, "Senha Atual", o => o.getSenhaAtual(), TextAlign.center).setSort((a, b) => UString.compare(a.getSenhaAtual(), b.getSenhaAtual())).setGrupo(false).setId("MudarSenha-Cols-senhaAtual");
		this.NOVA_SENHA = new Coluna(360, "Nova Senha", o => o.getNovaSenha(), TextAlign.center).setSort((a, b) => UString.compare(a.getNovaSenha(), b.getNovaSenha())).setGrupo(false).setId("MudarSenha-Cols-novaSenha");
		this.CONFIRMAR_SENHA = new Coluna(360, "Confirmar Senha", o => o.getConfirmarSenha(), TextAlign.center).setSort((a, b) => UString.compare(a.getConfirmarSenha(), b.getConfirmarSenha())).setGrupo(false).setId("MudarSenha-Cols-confirmarSenha");
		this.list = [this.SENHA_ATUAL, this.NOVA_SENHA, this.CONFIRMAR_SENHA];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("MudarSenhaCols", this);
	}
}
