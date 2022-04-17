/* front-constructor */
import MudarSenhaEditAbstract from '../../auto/mudarSenha/MudarSenhaEditAbstract';
import Session from '../../estado/Session';

export default class MudarSenhaEdit extends MudarSenhaEditAbstract {

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
		Session.getInstance().mudarSenha.set(false);
	}

	getTabs() {
		return this.grupo_geral_0(this.getCampos());
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});

}

MudarSenhaEdit.defaultProps = MudarSenhaEditAbstract.defaultProps;
