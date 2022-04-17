/* tcc-java */
import TelefoneEditAbstract from '../../auto/telefone/TelefoneEditAbstract';

export default class TelefoneEdit extends TelefoneEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

TelefoneEdit.defaultProps = TelefoneEditAbstract.defaultProps;
