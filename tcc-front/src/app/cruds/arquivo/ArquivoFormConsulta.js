/* front-constructor */
import ArquivoFormConsultaAbstract from '../../auto/arquivo/ArquivoFormConsultaAbstract';

export default class ArquivoFormConsulta extends ArquivoFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ArquivoFormConsulta.defaultProps = ArquivoFormConsultaAbstract.defaultProps;
