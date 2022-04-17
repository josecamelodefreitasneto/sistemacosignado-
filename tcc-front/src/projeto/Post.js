/* react */
import Servico from './Servico';
import UInteger from '../app/misc/utils/UInteger';

export default class Post {

	constructor(uri, params, onSuccess) {

		this.uri = uri;
		this.onSuccess = onSuccess;

		if (UInteger.isInt(params)) {
			this.params = {id: UInteger.toInt(params)};
		} else {
			this.params = params;
		}

	}

	key;
	uri;
	params;
	onSuccess;
	onError = Post.onErrorDefault;
	onFinally;
	guardarNoMap = false;
	headers;

	static onErrorDefault;

	run() {
		Servico.getInstance().post(this);
	}

	setHeaders(o){this.headers = o; return this;}
	setOnError(o){this.onError = o; return this;}
	setOnFinally(o){this.onFinally = o; return this;}
	setGuardarNoMap(o){this.guardarNoMap = o; return this;}
}
