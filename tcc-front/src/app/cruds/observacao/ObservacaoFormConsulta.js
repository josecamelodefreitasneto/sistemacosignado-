/* front-constructor */
import ObservacaoFormConsultaAbstract from '../../auto/observacao/ObservacaoFormConsultaAbstract';

export default class ObservacaoFormConsulta extends ObservacaoFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ObservacaoFormConsulta.defaultProps = ObservacaoFormConsultaAbstract.defaultProps;
