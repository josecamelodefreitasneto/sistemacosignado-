/* front-constructor */
import ImportacaoArquivoErroFormConsultaAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroFormConsultaAbstract';

export default class ImportacaoArquivoErroFormConsulta extends ImportacaoArquivoErroFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

ImportacaoArquivoErroFormConsulta.defaultProps = ImportacaoArquivoErroFormConsultaAbstract.defaultProps;
