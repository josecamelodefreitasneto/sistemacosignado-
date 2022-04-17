/* front-constructor */
import ImportacaoArquivoEditAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoEditAbstract';

export default class ImportacaoArquivoEdit extends ImportacaoArquivoEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});

}

ImportacaoArquivoEdit.defaultProps = ImportacaoArquivoEditAbstract.defaultProps;
