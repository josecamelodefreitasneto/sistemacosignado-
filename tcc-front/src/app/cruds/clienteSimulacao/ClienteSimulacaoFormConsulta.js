/* tcc-java */
import ClienteSimulacaoFormConsultaAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoFormConsultaAbstract';

export default class ClienteSimulacaoFormConsulta extends ClienteSimulacaoFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ClienteSimulacaoFormConsulta.defaultProps = ClienteSimulacaoFormConsultaAbstract.defaultProps;
