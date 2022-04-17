package br.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.utils.map.MapSO;
import gm.utils.string.ListString;

@Component
public class ImportacaoArquivoProcessador {
	
	@Autowired ImportacaoArquivoService importacaoArquivoService;
	@Autowired ImportacaoArquivoErroService importacaoArquivoErroService;
	
	public void processar(int id) {
		
		try {
			
			ListString list = importacaoArquivoService.getList(id);
			if (list == null) {
				return;
			}
			importacaoArquivoPreProcessamento pre = importacaoArquivoService.preProcessar(id, list);
			
			boolean concluido = false;
			while (!concluido ) {
				try {
					importacaoArquivoService.processar(pre);
					concluido = true;
				} catch (Exception e) {
					importacaoArquivoPreProcessamento pre2 = new importacaoArquivoPreProcessamento();
					pre2.id = pre.id;
					pre2.service = pre.service;
					while (true) {
						MapSO map = pre.validados.remove(0);
						if (map.getIntObrig("numero-linha") == pre.linhaErro) {
							pre.linhaErro = 0;
							break;
						} else {
							pre2.validados.add(map);
						}
					}
					importacaoArquivoService.processar(pre2);
				}
			}
			
			importacaoArquivoService.marcaComoProcessado(id, pre.erros);			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}
