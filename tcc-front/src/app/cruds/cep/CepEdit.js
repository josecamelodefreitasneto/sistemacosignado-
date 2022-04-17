/* tcc-java */
import CepEditAbstract from '../../auto/cep/CepEditAbstract';

export default class CepEdit extends CepEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

CepEdit.defaultProps = CepEditAbstract.defaultProps;
