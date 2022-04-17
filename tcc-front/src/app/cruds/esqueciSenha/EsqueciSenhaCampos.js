/* front-constructor */
import EsqueciSenhaCamposAbstract from '../../auto/esqueciSenha/EsqueciSenhaCamposAbstract';
import Sessao from '../../../projeto/Sessao';
import Session from '../../estado/Session';
import StringBox from '../../misc/utils/StringBox';

export default class EsqueciSenhaCampos extends EsqueciSenhaCamposAbstract {

	init2() {

		this.confirmarSenha.getInvalidMessageFunction = () => {
			if (!this.confirmarSenha.eq(this.novaSenha.get())) {
				return "Senhas não conferem!";
			}
			return null;
		};

		this.afterSaveObservers.add(() => {
			let session = Session.getInstance();
			session.modalEsqueciSenhaCadastrar.set(false);
			session.token.notifyObservers();
		});

	}

	static getInstance() {
		return Sessao.getInstance("EsqueciSenhaCampos", () => new EsqueciSenhaCampos(), o => o.init());
	}

	enviarEmail() {
		let session = Session.getInstance();
		let s = new StringBox(session.login.get());
		this.post("enviar-email", s, res => {
			session.modalEsqueciSenhaEmail.set(false);
			session.modalEsqueciSenhaCodigo.set(true);
		});
	}

	confirmarCodigo() {
		const session = Session.getInstance();
		const dto = {login: session.login.get(), codigo: session.esqueciSenhaCodigo.get()};
		this.post("confirmar-codigo", dto, res => {
			session.callBackLogin(null, res);
			session.modalEsqueciSenhaCodigo.set(false);
			session.modalEsqueciSenhaCadastrar.set(true);
		});
	}

}
