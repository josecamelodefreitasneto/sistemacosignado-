package br.impl.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.ArquivoExtensao;
import br.auto.model.ArquivoPath;
import br.auto.service.ArquivoPathServiceAbstract;
import br.impl.outros.ApplicationProperties;
import gm.utils.date.Data;

@Component
public class ArquivoPathService extends ArquivoPathServiceAbstract {
	
	@Autowired ApplicationProperties applicationProperties;

	public ArquivoPath get(final ArquivoExtensao extensao) {
		ArquivoPath o = select().extensao().eq(extensao).itens().menor(1000).first();
		if (o == null) {
			o = newO();
			o.setExtensao(extensao);
			o.setItens(0);
			final Data data = Data.now();
			String fileSystem = applicationProperties.getFileSystem();
			if (!fileSystem.endsWith("/")) {
				fileSystem += "/";
			}
			o.setNome(fileSystem + data.getAno() + "/" + extensao.getNome() + "/" + data.format("[mm][dd][yy][nn][ss]"));
			final File diretorio = new File(o.getNome());
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}
			o = this.save(o);
		}
		return setOld(o);
	}

//	@Autowired private JmsTemplate jmsTemplate;
//
	public void incrementarItem(final ArquivoPath o) {
//		this.jmsTemplate.convertAndSend("queue.sample", o.getId());
	}
//	
//	@JmsListener(destination = "queue.sample")
//	public void onReceiverQueue(final Integer id) {
//		final ArquivoPath o = this.find(id);
//		o.setItens(o.getItens()+1);
//		this.save(o);
//	}
	

}
