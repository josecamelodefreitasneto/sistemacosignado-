package br.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.AuditoriaTransacao;
import br.auto.service.AuditoriaTransacaoServiceAbstract;
import br.impl.outros.Log;
import br.impl.outros.ThreadScope;
import gm.utils.date.Data;
import gm.utils.number.Numeric3;
import gm.utils.number.UInteger;

@Component
public class AuditoriaTransacaoService extends AuditoriaTransacaoServiceAbstract {
	
	@Autowired LoginService loginService;
	@Autowired ComandoService comandoService;
	
	public AuditoriaTransacao criarTransacao() {
		Log.info("AuditoriaTransacaoService.criarTransacao >");
		try {
			final AuditoriaTransacao o = newO();
			o.setLogin(loginService.find(ThreadScope.getLogin()));
			o.setComando(comandoService.find(ThreadScope.getComando()));
			o.setData(new Data(ThreadScope.getDataHora()).getCalendar());
			Integer milisegundos = UInteger.toInt(System.currentTimeMillis() - ThreadScope.getDataHora());
			o.setTempo(new Numeric3(milisegundos).dividido(1000).getValor());
			insertSemAuditoria(o);
			Log.info("AuditoriaTransacaoService.criarTransacao <");
			return o;
		} catch (Exception e) {
			Log.error("AuditoriaTransacaoService.criarTransacao <");
			throw e;
		}
	}
	
}
