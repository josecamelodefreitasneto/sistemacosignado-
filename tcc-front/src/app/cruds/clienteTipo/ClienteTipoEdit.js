/* tcc-java */
import ClienteTipoEditAbstract from '../../auto/clienteTipo/ClienteTipoEditAbstract';

export default class ClienteTipoEdit extends ClienteTipoEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ClienteTipoEdit.defaultProps = ClienteTipoEditAbstract.defaultProps;
