/* front-constructor */
import BindingServiceList from './BindingServiceList';

export default class BindingConsultaList extends BindingServiceList {

	getParams;

	constructor(titleP,mergeFunctionP, carragarCallBackP, uri, getParamsP) {
		super(titleP, mergeFunctionP, carragarCallBackP, uri);
		this.getParams = getParamsP;
	}

	getParametros() {
		return this.getParams();
	}

	preparadoParaBusca() {
		return true;
	}
}
