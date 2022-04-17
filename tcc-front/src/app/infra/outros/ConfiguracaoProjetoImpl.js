/* tcc-java */
import ConfiguracaoProjeto from '../../../fc/outros/ConfiguracaoProjeto';
import UCommons from '../../misc/utils/UCommons';

export default class ConfiguracaoProjetoImpl extends ConfiguracaoProjeto {
	getMock(entidade) {
		return null;
	}
	startEntidades() {
		return [
			"Arquivo",
			"Atendente",
			"Cep",
			"Cliente",
			"ClienteRubrica",
			"ClienteSimulacao",
			"ClienteStatus",
			"ClienteTipo",
			"ClienteTipoSimulacao",
			"EsqueciSenha",
			"ImportacaoArquivo",
			"ImportacaoArquivoErro",
			"ImportacaoArquivoStatus",
			"MudarSenha",
			"Observacao",
			"Telefone",
			"Usuario"
		];
	}
	static start() {
		if (UCommons.isEmpty(ConfiguracaoProjeto.instance)) {
			new ConfiguracaoProjetoImpl();
		}
	}
}
