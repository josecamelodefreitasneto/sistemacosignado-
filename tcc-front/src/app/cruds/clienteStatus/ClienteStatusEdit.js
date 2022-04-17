/* tcc-java */
import ClienteStatusEditAbstract from '../../auto/clienteStatus/ClienteStatusEditAbstract';

export default class ClienteStatusEdit extends ClienteStatusEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ClienteStatusEdit.defaultProps = ClienteStatusEditAbstract.defaultProps;
