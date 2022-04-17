/* front-constructor */
import ImportacaoArquivoErroEditAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroEditAbstract';

export default class ImportacaoArquivoErroEdit extends ImportacaoArquivoErroEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

ImportacaoArquivoErroEdit.defaultProps = ImportacaoArquivoErroEditAbstract.defaultProps;
