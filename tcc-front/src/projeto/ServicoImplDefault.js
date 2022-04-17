/* react */
import UCommons from '../app/misc/utils/UCommons';
import UString from '../app/misc/utils/UString';

export default class ServicoImplDefault {

	uriBase;
	getToken;
	interceptors = [];

	constructor(uriBase, getTokenFunction) {
		this.uriBase = uriBase;
		this.getToken = getTokenFunction;
	}

	addInterceptor(interceptor) {
		this.interceptors.add(interceptor);
	}

	post(uri, params, headers, onSuccess, onError, onFinally) {
		this.exec("POST", uri, params, headers, onSuccess, onError, onFinally);
	}

	get(uri, params, headers, onSuccess, onError, onFinally) {
		this.exec("GET", uri, params, headers, onSuccess, onError, onFinally);
	}

	run(requestParams) {

		if (!requestParams.interceptors.isEmpty()) {
			requestParams.interceptors.remove(0).run(requestParams, () => this.run(requestParams));
			return;
		}

		let bodyRequest = UString.toString(requestParams.params);

		if (UCommons.notEmpty(this.getToken)) {
			const token = this.getToken();
			if (UString.notEmpty(token)) {
				bodyRequest = UString.ignoreRight(bodyRequest, 1) + ",\"token\":\"" + token + "\"}";
			}
		}

		let headersDefault = new Headers();
		headersDefault.set("Content-Type", "application/json; charset=UTF-8");
		headersDefault.set("Accept", "*/*");
		headersDefault.set("Access-Control-Allow-Origin", "*");
		headersDefault.set("credentials", "include");
		headersDefault.set("Access-Control-Allow-Credentials", "true");

		requestParams.headers.forEach((k, v) => headersDefault.set(k, v));

		const fetchParams = {
			body: bodyRequest,
			headers: headersDefault,
			method: requestParams.method,
			mode: "cors",
			cache: "default",
			credentials: "omit"
		};

		fetch(this.uriBase + requestParams.uri, fetchParams)
		.then(res => {
			try {
				res.json().then(body => {
					const o = {};
					o.body = body;
					o.data = res.data;
					o.url = res.url;
					if (res.status >= 200 && res.status < 300) {
						requestParams.onSuccess(o);
					} else {
						requestParams.onError(o);
					}
				});
			} finally {}
		})
		.catch(o => {
			requestParams.onError(o);
		})
		.finally(() => {
			if (UCommons.notEmpty(requestParams.onFinally)) {
				requestParams.onFinally();
			}
		});
	}

	exec(method, uri, params, headers, onSuccess, onError, onFinally) {

		let headersFinal = new Headers();
		headersFinal.set("Content-Type", "application/json; charset=UTF-8");
		headersFinal.set("Accept", "*/*");
		headersFinal.set("Access-Control-Allow-Origin", "*");
		headersFinal.set("credentials", "include");
		headersFinal.set("Access-Control-Allow-Credentials", "true");

		if (!UCommons.isEmpty(headers)) {
			headers.forEach((key, value) => headersFinal.set(key, value));
		}

		let requestParams = {method: method, uri: uri, params: params, headers: headersFinal, onSuccess: onSuccess, onError: onError, onFinally: onFinally, interceptors: this.interceptors.copy()};
		this.run(requestParams);

	}

}
