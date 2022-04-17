/* tcc-java */
import ClienteTipoSimulacaoEditAbstract from '../../auto/clienteTipoSimulacao/ClienteTipoSimulacaoEditAbstract';

export default class ClienteTipoSimulacaoEdit extends ClienteTipoSimulacaoEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ClienteTipoSimulacaoEdit.defaultProps = ClienteTipoSimulacaoEditAbstract.defaultProps;
