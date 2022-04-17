/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UString from '../../misc/utils/UString';

export default class UsuarioColsAbstract {

	NOME;
	LOGIN;
	SENHA;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.NOME = new Coluna(120, "Nome", o => o.getNome(), TextAlign.left).setSort((a, b) => UString.compare(a.getNome(), b.getNome())).setGrupo(false).setId("Usuario-Cols-nome");
		this.LOGIN = new Coluna(150, "Login", o => o.getLogin(), TextAlign.center).setSort((a, b) => UString.compare(a.getLogin(), b.getLogin())).setGrupo(false).setId("Usuario-Cols-login");
		this.SENHA = new Coluna(90, "Senha", o => o.getSenha(), TextAlign.center).setSort((a, b) => UString.compare(a.getSenha(), b.getSenha())).setGrupo(false).setId("Usuario-Cols-senha");
		this.list = [this.NOME, this.LOGIN, this.SENHA];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("UsuarioCols", this);
	}
}
