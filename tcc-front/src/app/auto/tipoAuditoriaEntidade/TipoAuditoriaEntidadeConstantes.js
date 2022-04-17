/* front-constructor */
import IdText from '../../misc/utils/IdText';

export default class TipoAuditoriaEntidadeConstantes {
	static getList() {
		return TipoAuditoriaEntidadeConstantes.list;
	}
}
TipoAuditoriaEntidadeConstantes.INCLUSAO = 1;
TipoAuditoriaEntidadeConstantes.ALTERACAO = 2;
TipoAuditoriaEntidadeConstantes.EXCLUSAO = 4;
TipoAuditoriaEntidadeConstantes.EXECUCAO = 3;
TipoAuditoriaEntidadeConstantes.RECUPERACAO = 5;
TipoAuditoriaEntidadeConstantes.BLOQUEIO = 6;
TipoAuditoriaEntidadeConstantes.DESBLOQUEIO = 7;
TipoAuditoriaEntidadeConstantes.list = [
	new IdText(TipoAuditoriaEntidadeConstantes.INCLUSAO, "Inclusão"),
	new IdText(TipoAuditoriaEntidadeConstantes.ALTERACAO, "Alteração"),
	new IdText(TipoAuditoriaEntidadeConstantes.EXCLUSAO, "Exclusão"),
	new IdText(TipoAuditoriaEntidadeConstantes.EXECUCAO, "Execução"),
	new IdText(TipoAuditoriaEntidadeConstantes.RECUPERACAO, "Recuperação"),
	new IdText(TipoAuditoriaEntidadeConstantes.BLOQUEIO, "Bloqueio"),
	new IdText(TipoAuditoriaEntidadeConstantes.DESBLOQUEIO, "Desbloqueio")
];
