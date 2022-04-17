/* front-constructor */
import UsuarioFormConsultaAbstract from '../../auto/usuario/UsuarioFormConsultaAbstract';

export default class UsuarioFormConsulta extends UsuarioFormConsultaAbstract {
	setWidthForm = o => this.setState({widthForm:o});
	setShowEdit = o => this.setState({showEdit:o});
	setShowImportarArquivo = o => this.setState({showImportarArquivo:o});
	setMaisFiltros = o => this.setState({maisFiltros:o});}

UsuarioFormConsulta.defaultProps = UsuarioFormConsultaAbstract.defaultProps;
