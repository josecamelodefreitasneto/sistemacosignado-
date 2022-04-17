/* react */
import Console from '../app/misc/utils/Console';
import Sessao from './Sessao';
import StringBox from '../app/misc/utils/StringBox';
import UCommons from '../app/misc/utils/UCommons';
import UNative from '../app/misc/utils/UNative';
import UString from '../app/misc/utils/UString';

export default class Servico {

	static creator;
	static impl;
	static itensRodando = new Map();
	static executarQuandoVazio = [];
	static fila = [];

	static addExecutarQuandoVazio(func) {
		Servico.executarQuandoVazio.add(func);
		Servico.runExecutarQuandoVazio();
	}

	addInterceptor(interceptor) {
		Servico.getImpl().addInterceptor(interceptor);
	}

	static getImpl() {
		if (UCommons.isEmpty(Servico.impl)) {
			if (UCommons.isEmpty(Servico.creator)) {
				throw new Error("Servico.creator === null");
			} else {
				Servico.impl = Servico.creator();
			}
		}
		return Servico.impl;
	}

	static debug = true;
	static rodando = 0;
	static esperando = 0;
	static keysCount = 0;
	static exception;

	post(postParams) {
		this.checkInstance();
		postParams.key = postParams.uri + " " + UString.toString(postParams.params);
		if (!Servico.fila.exists(o => UString.equals(postParams.key, o.key))) {
			Servico.fila.add(postParams);
			this.runNext(null);
		}
	}

	runNext(anterior) {
		Servico.fila.removeIf(o => UString.equals(anterior, o.key));
		setTimeout(() => this.run());
	}

	run() {

		if (Servico.rodando > 0 || Servico.fila.isEmpty()) {
			return;
		}

		let postParams = Servico.fila.removeFirst();

		const value = postParams.uri + " " + UString.toString(postParams.params);

		Servico.fila.removeIf(o => UString.equals(value, o.key));

		if (Servico.rodando > 0) {
			Servico.esperando++;
			setTimeout(() => {
				Servico.esperando--;
				this.post(postParams);
			}, 500);
			Console.log("Servico", "esperado post ...");
			return;
		}

		const key = Servico.nextPost(value);

		Servico.getImpl().post(postParams.uri, postParams.params, postParams.headers,
			res => {

				try {
					postParams.onSuccess(res);
				} catch (e) {
					Servico.exception = e;
					Console.error("Servico", "ocorreu um erro no callback do servico " + postParams.uri);
					Console.error("params", postParams.params);
					Console.error("exception", Servico.exception);
					this.callOnError(postParams, key);
				} finally {
					Servico.finish(key);
					this.runNext(value);
				}
			},
			res => {
				try {
					Servico.exception = res.body;
					if (UCommons.isEmpty(Servico.exception)) {
						Servico.exception = UCommons.cast(res);
					}
					Console.error("Servico", "ocorreu um erro no servico " + postParams.uri);
					Console.error("params", postParams.params);
					Console.error("exception", Servico.exception);
					this.callOnError(postParams, key);
				} finally {
					Servico.finish(key);
					this.runNext(value);
				}
			},
			() => {
				try {
					if (UCommons.notEmpty(postParams.onFinally)) {
						postParams.onFinally();
					}
				} finally {
					Servico.finish(key);
					this.runNext(value);
				}
			}
		);
	}

	callOnError(postParams, key) {
		if (UCommons.notEmpty(postParams.onError)) {
			const message = UNative.getAtributo(Servico.exception, "message");
			try {
				postParams.onError(message);
			} catch (e2) {
				Console.error("Servico", "ocorreu um erro no tratamento de excessao do servico " + postParams.uri);
				Console.error("params", postParams.params);
				Console.error("exception", e2);
			} finally {
				Servico.finish(key);
			}
		}
	}

	static finish(key) {
		Servico.rodando--;
		Servico.itensRodando.delete(key);
		Servico.runExecutarQuandoVazio();
	}

	static runExecutarQuandoVazio() {
		if (Servico.esperando === 0 && !Servico.executarQuandoVazio.isEmpty()) {
			const list = Servico.executarQuandoVazio.concat([]);
			Servico.executarQuandoVazio.clear();
			list.forEach(f => f());
		}
	}

	static nextPost(value) {
		Servico.keysCount++;
		Servico.itensRodando.set(Servico.keysCount, value);
		Servico.rodando++;
		return Servico.keysCount;
	}

	static esperar() {
		let count = 0;
		while (Servico.rodando > 0) {
			count++;
			if (count > 20 && !Servico.debug) {
				const s = new StringBox("Travou (1)");
				Servico.itensRodando.forEach((k, v) => s.add("; " + k + " - " + v));
				throw new Error(s.get());
			}
		}
	}

	static newInstance = () => new Servico();

	static getInstance() {
		return Sessao.getInstance("Servico", Servico.newInstance, o => {});
	}
	checkInstance() {
		Sessao.checkInstance("Servico", this);
	}
}
