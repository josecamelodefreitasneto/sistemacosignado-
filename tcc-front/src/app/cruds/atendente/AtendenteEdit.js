/* tcc-java */
import AtendenteEditAbstract from '../../auto/atendente/AtendenteEditAbstract';

export default class AtendenteEdit extends AtendenteEditAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});}

AtendenteEdit.defaultProps = AtendenteEditAbstract.defaultProps;
