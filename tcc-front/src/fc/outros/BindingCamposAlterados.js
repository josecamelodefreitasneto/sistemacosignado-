/* front-constructor */
import BindingServiceList from '../components/BindingServiceList';
import Sessao from '../../projeto/Sessao';

export default class BindingCamposAlterados extends BindingServiceList {

	constructor() {
		super("Campos alterados", null, null, null);
	}

	getParametros() {
		/* TODO Auto-generated method stub*/
		return null;
	}

	preparadoParaBusca() {
		/* TODO Auto-generated method stub*/
		return false;
	}

	static getInstance() {
		return Sessao.getInstance("BindingCamposAlterados", () => new BindingCamposAlterados(), o => {});
	}

}
