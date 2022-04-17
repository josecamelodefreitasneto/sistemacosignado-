/* tcc-java */
import ConfiguracaoProjeto from '../../fc/outros/ConfiguracaoProjeto';
import ConfiguracaoProjetoImpl from '../infra/outros/ConfiguracaoProjetoImpl';
import Post from '../../projeto/Post';
import Servico from '../../projeto/Servico';
import ServicoImpl from '../../fc/outros/ServicoImpl';
import Session from '../estado/Session';
import StartTema from './StartTema';
import UString from '../misc/utils/UString';
import {message} from 'antd';

export default class Config {
	static start() {

		StartTema.exec();
		Post.onErrorDefault = m => message.error(m);
		Servico.creator = () => new ServicoImpl(Config.getUrlBase(), () => Session.getInstance().token.get());
		ConfiguracaoProjetoImpl.start();
		ConfiguracaoProjeto.urlBase = Config.getUrlBase();
	}

	static getUrlBase() {
		const stage = process.env.REACT_APP_STAGE;
		if (UString.equals(stage, "docker")) {
			return "http://tcc-back:8080/";
		} else if (UString.equals(stage, "selfhost")) {
			return "/api/";
		} else {
			return "http://localhost:8080/";
		}
	}

}
