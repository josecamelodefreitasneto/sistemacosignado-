/* tcc-java */
import ClienteFormConsultaAbstract from '../../auto/cliente/ClienteFormConsultaAbstract';

export default class ClienteFormConsulta extends ClienteFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ClienteFormConsulta.defaultProps = ClienteFormConsultaAbstract.defaultProps;
