/* react */
import RequestInterceptor from '../../projeto/RequestInterceptor';
import Servico from '../../projeto/Servico';

export default class KeycloakInterceptor extends RequestInterceptor {

	kc;

	constructor(kc) {
		super();
		this.kc = kc;
		let servico = Servico.getInstance();
		servico.addInterceptor(this);
	}

	run(requestParams, onSuccess) {
		this.kc.updateToken(5).then(x => {
			requestParams.headers.set("Authorization", "Bearer " + this.kc.token);
			onSuccess();
		}).catch(e => {
			this.kc.logout();
		});
	}

}
