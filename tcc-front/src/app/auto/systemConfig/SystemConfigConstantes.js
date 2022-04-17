/* front-constructor */
import IdText from '../../misc/utils/IdText';

export default class SystemConfigConstantes {
	static getList() {
		return SystemConfigConstantes.list;
	}
}
SystemConfigConstantes.VERSAO_DE_ATUALIZACAO_DISPONIVEL = 1;
SystemConfigConstantes.VERSAO_DE_ATUALIZACAO_EXECUTADA = 2;
SystemConfigConstantes.VERSAO_DE_SCRIPT_DISPONIVEL = 3;
SystemConfigConstantes.VERSAO_DE_SCRIPT_EXECUTADA = 4;
SystemConfigConstantes.list = [
	new IdText(SystemConfigConstantes.VERSAO_DE_ATUALIZACAO_DISPONIVEL, "Versão de atualização disponível"),
	new IdText(SystemConfigConstantes.VERSAO_DE_ATUALIZACAO_EXECUTADA, "Versão de atualização executada"),
	new IdText(SystemConfigConstantes.VERSAO_DE_SCRIPT_DISPONIVEL, "Versão de script disponível"),
	new IdText(SystemConfigConstantes.VERSAO_DE_SCRIPT_EXECUTADA, "Versão de script executada")
];
