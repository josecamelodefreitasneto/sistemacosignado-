/* tcc-java */
import CepFormConsultaAbstract from '../../auto/cep/CepFormConsultaAbstract';

export default class CepFormConsulta extends CepFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

CepFormConsulta.defaultProps = CepFormConsultaAbstract.defaultProps;
