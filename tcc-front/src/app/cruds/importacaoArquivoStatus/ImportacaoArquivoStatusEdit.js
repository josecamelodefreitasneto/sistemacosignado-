/* front-constructor */
import ImportacaoArquivoStatusEditAbstract from '../../auto/importacaoArquivoStatus/ImportacaoArquivoStatusEditAbstract';

export default class ImportacaoArquivoStatusEdit extends ImportacaoArquivoStatusEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ImportacaoArquivoStatusEdit.defaultProps = ImportacaoArquivoStatusEditAbstract.defaultProps;
