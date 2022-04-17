/* front-constructor */
import UsuarioEditAbstract from '../../auto/usuario/UsuarioEditAbstract';

export default class UsuarioEdit extends UsuarioEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

UsuarioEdit.defaultProps = UsuarioEditAbstract.defaultProps;
