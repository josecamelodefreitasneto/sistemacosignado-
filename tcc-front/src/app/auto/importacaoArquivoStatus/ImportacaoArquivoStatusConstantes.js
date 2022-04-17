/* front-constructor */
import IdText from '../../misc/utils/IdText';

export default class ImportacaoArquivoStatusConstantes {
	static getList() {
		return ImportacaoArquivoStatusConstantes.list;
	}
}
ImportacaoArquivoStatusConstantes.AGUARDANDO_PROCESSAMENTO = 1;
ImportacaoArquivoStatusConstantes.EM_ANALISE = 2;
ImportacaoArquivoStatusConstantes.EM_PROCESSAMENTO = 3;
ImportacaoArquivoStatusConstantes.PROCESSADO = 4;
ImportacaoArquivoStatusConstantes.list = [
	new IdText(ImportacaoArquivoStatusConstantes.AGUARDANDO_PROCESSAMENTO, "Aguardando Processamento"),
	new IdText(ImportacaoArquivoStatusConstantes.EM_ANALISE, "Em Análise"),
	new IdText(ImportacaoArquivoStatusConstantes.EM_PROCESSAMENTO, "Em Processamento"),
	new IdText(ImportacaoArquivoStatusConstantes.PROCESSADO, "Processado")
];
