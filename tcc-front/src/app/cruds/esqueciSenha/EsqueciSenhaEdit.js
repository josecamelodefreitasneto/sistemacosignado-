/* front-constructor */
import EsqueciSenhaEditAbstract from '../../auto/esqueciSenha/EsqueciSenhaEditAbstract';
import Session from '../../estado/Session';

export default class EsqueciSenhaEdit extends EsqueciSenhaEditAbstract {

	getTitle() {
		return "Mudar Senha";
	}

	getWidthModal() {
		return 40;
	}

	ehModal() {
		return true;
	}

	close() {
		let session = Session.getInstance();
		session.modalEsqueciSenhaCadastrar.set(false);
		session.efetuaLogout();
	}

	getTabs() {
		return this.grupo_geral_0(this.getCampos());
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});

}

EsqueciSenhaEdit.defaultProps = EsqueciSenhaEditAbstract.defaultProps;
