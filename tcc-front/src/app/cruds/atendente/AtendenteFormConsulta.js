/* tcc-java */
import AtendenteFormConsultaAbstract from '../../auto/atendente/AtendenteFormConsultaAbstract';

export default class AtendenteFormConsulta extends AtendenteFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

AtendenteFormConsulta.defaultProps = AtendenteFormConsultaAbstract.defaultProps;
