/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class EsqueciSenhaColsAbstract {

	NOVA_SENHA;
	CONFIRMAR_SENHA;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOVA_SENHA = new Coluna(360, "Nova Senha", o => o.getNovaSenha(), TextAlign.center).setSort((a, b) => UString.compare(a.getNovaSenha(), b.getNovaSenha())).setGrupo(false).setId("EsqueciSenha-Cols-novaSenha");
		this.CONFIRMAR_SENHA = new Coluna(360, "Confirmar Senha", o => o.getConfirmarSenha(), TextAlign.center).setSort((a, b) => UString.compare(a.getConfirmarSenha(), b.getConfirmarSenha())).setGrupo(false).setId("EsqueciSenha-Cols-confirmarSenha");
		this.list = [this.NOVA_SENHA, this.CONFIRMAR_SENHA];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("EsqueciSenhaCols", this);
	}
}
