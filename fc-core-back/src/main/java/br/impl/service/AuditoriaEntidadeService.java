package br.impl.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.AuditoriaEntidade;
import br.auto.model.AuditoriaTransacao;
import br.auto.model.Entidade;
import br.auto.model.Login;
import br.auto.select.AuditoriaEntidadeSelect;
import br.auto.service.AuditoriaEntidadeBox;
import br.auto.service.AuditoriaEntidadeServiceAbstract;
import br.impl.outros.ThreadScope;
import gm.utils.comum.Lst;
import gm.utils.map.MapSO;

@Component
public class AuditoriaEntidadeService extends AuditoriaEntidadeServiceAbstract {

	@Autowired TipoAuditoriaEntidadeService tipoLogEntidadeService;
	@Autowired AuditoriaTransacaoService auditoriaTransacaoService;
	@Autowired AuditoriaCampoService auditoriaCampoService;
	@Autowired CampoService campoService;
	
	@Transactional
	public void registrar() {
		
		List<AuditoriaEntidadeBox> auditorias = ThreadScope.getAuditorias();
		
		if (auditorias.isEmpty()) {
			return;
		}
		
		auditorias.removeIf(o -> o.getTipo() == TipoAuditoriaEntidadeService.ALTERACAO && o.getCampos().isEmpty());

		if (auditorias.isEmpty()) {
			return;
		}
		
		AuditoriaTransacao transacao = auditoriaTransacaoService.criarTransacao();
		
		MapSO contadorOperacoes = new MapSO();
		
		for (AuditoriaEntidadeBox box : auditorias) {
			AuditoriaEntidade o = newO();
			o.setTransacao(transacao);
			o.setEntidade(entidadeService.find(box.getEntidade()));
			o.setRegistro(box.getRegistro());
			o.setTipo(box.getTipo());
			String key = box.getEntidade() + "-" + box.getTipo() + "-" + box.getRegistro();
			Integer numeroDaOperacao = contadorOperacoes.get(key);
			if (numeroDaOperacao == null) {
				numeroDaOperacao = 1;
			} else {
				numeroDaOperacao++;
			}
			contadorOperacoes.put(key, numeroDaOperacao);
			o.setNumeroDaOperacao(numeroDaOperacao);
			o = insertSemAuditoria(o);
			auditoriaCampoService.registrar(o, box.getCampos());
		}
		
		ThreadScope.clear();
		
	}
	
	public Lst<AuditoriaEntidade> list(Entidade entidade, int registro) {
		return select(null).entidade().eq(entidade).registro().eq(registro).list();
	}
	
	@Transactional
	public int getLoginInsert(int entidade, int registro) {
		AuditoriaEntidadeSelect<?> select = select();
		select.entidade().id().eq(entidade);
		select.registro().eq(registro);
		select.tipo().eq(TipoAuditoriaEntidadeService.INCLUSAO);
		AuditoriaEntidade auditoriaEntidade = select.unique();
		Login login = auditoriaEntidade.getTransacao().getLogin();
		return login.getId();
	}
	
}