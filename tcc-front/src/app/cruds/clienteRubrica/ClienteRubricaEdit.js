/* tcc-java */
import ClienteRubricaEditAbstract from '../../auto/clienteRubrica/ClienteRubricaEditAbstract';

export default class ClienteRubricaEdit extends ClienteRubricaEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ClienteRubricaEdit.defaultProps = ClienteRubricaEditAbstract.defaultProps;
