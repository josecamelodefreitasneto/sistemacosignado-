/* tcc-java */
import ClienteRubricaFormConsultaAbstract from '../../auto/clienteRubrica/ClienteRubricaFormConsultaAbstract';

export default class ClienteRubricaFormConsulta extends ClienteRubricaFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ClienteRubricaFormConsulta.defaultProps = ClienteRubricaFormConsultaAbstract.defaultProps;
