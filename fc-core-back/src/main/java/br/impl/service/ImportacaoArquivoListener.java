package br.impl.service;

import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.impl.outros.Log;
import br.impl.outros.ThreadScope;

@Component
public class ImportacaoArquivoListener {

	@Autowired ImportacaoArquivoProcessador importacaoArquivoProcessador;
	
    @JmsListener(destination = ImportacaoArquivoService.INICIAR_PROCESSAMENTO)
    public void iniciarProcessamento(Integer id, Message message) {
		Log.info("ImportacaoArquivoListener.iniciarProcessamento("+id+") >");
		try {
			importacaoArquivoProcessador.processar(id);
			ThreadScope.finalizarComSucesso();
			Log.info("ImportacaoArquivoListener.iniciarProcessamento("+id+") <");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("ImportacaoArquivoListener.iniciarProcessamento("+id+") <");
		}
    }
}