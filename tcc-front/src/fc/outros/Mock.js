/* @author Gamarra - francisco.gamarra@gmail.com */
/* front-constructor */
import ConfiguracaoProjeto from './ConfiguracaoProjeto';
import EntidadeMock from '../../app/cruds/entidade/EntidadeMock';
import GenericMock from './GenericMock';
import UString from '../../app/misc/utils/UString';

export default class Mock {

	static getMock(entidade) {
		if (UString.isEmpty(entidade)) {
			throw new Error("Entidade????");
		} else if (entidade.equals("entidade")) {
			return EntidadeMock.getInstance();
		} else {
			const mock = ConfiguracaoProjeto.getInstance().getMock(entidade);
			if (mock === null) {
				return GenericMock.get(entidade);
			} else {
				return mock;
			}
		}
	}
	static getListGrid(entidade, params) {
		return Mock.getMock(entidade).getListGrid(params);
	}
	static byId(entidade, id) {
		return Mock.getMock(entidade).byId(id);
	}
	static save(entidade, params) {
		return Mock.getMock(entidade).save(params);
	}
	static search(entidade, params) {
		return Mock.getMock(entidade).search(params);
	}
	static getSubList(comando, entidade, id) {
		return Mock.getMock(entidade).getSubList(comando, id);
	}
	static get(comando, entidade, params) {
		return Mock.getMock(entidade).get(comando, params);
	}
}
