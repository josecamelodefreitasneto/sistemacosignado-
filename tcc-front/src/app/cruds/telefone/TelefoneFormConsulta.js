/* tcc-java */
import TelefoneFormConsultaAbstract from '../../auto/telefone/TelefoneFormConsultaAbstract';

export default class TelefoneFormConsulta extends TelefoneFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

TelefoneFormConsulta.defaultProps = TelefoneFormConsultaAbstract.defaultProps;
