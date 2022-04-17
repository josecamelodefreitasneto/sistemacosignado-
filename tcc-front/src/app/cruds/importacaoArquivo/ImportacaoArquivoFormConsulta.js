/* front-constructor */
import ImportacaoArquivoFormConsultaAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoFormConsultaAbstract';

export default class ImportacaoArquivoFormConsulta extends ImportacaoArquivoFormConsultaAbstract {

	getBotaoNovo() {
		return null;
	}
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});

}

ImportacaoArquivoFormConsulta.defaultProps = ImportacaoArquivoFormConsultaAbstract.defaultProps;
