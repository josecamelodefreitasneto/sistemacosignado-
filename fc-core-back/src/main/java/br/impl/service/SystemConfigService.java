package br.impl.service;

import org.springframework.stereotype.Component;

import br.auto.model.SystemConfig;
import br.auto.service.SystemConfigServiceAbstract;
import gm.utils.string.UString;

@Component
public class SystemConfigService extends SystemConfigServiceAbstract {
	
	@Override
	protected void registrarAuditoriaInsert(SystemConfig o) {}
	
	public void validaVersaoDeScript() {
		if (!equals(versaoDeScriptDisponivel(), versaoDeScriptExecutada())) {
			throw new RuntimeException("A versão do script está diferente!");
		}
	}
	
	private static boolean equals(SystemConfig a, SystemConfig b) {
		if (a == null) {
			return b == null;
		} else if (b == null) {
			return false;
		} else {
			return UString.equals(a.getValor(), b.getValor());
		}
	}

	public boolean atualizado() {
		validaVersaoDeScript();
		return equals(versaoDeAtualizacaoDisponivel(), versaoDeAtualizacaoExecutada());
	}

	public void atualizar() {
		SystemConfig executada = versaoDeAtualizacaoExecutada();
		executada.setValor(versaoDeAtualizacaoDisponivel().getValor());
		save(executada);
	}
	
}