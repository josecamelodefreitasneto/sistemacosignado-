/* tcc-java */
import ClienteSimulacaoEditAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoEditAbstract';

export default class ClienteSimulacaoEdit extends ClienteSimulacaoEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ClienteSimulacaoEdit.defaultProps = ClienteSimulacaoEditAbstract.defaultProps;
