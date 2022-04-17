package br.impl.outros;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.impl.service.ComandoService;
import br.impl.service.LoginService;
import br.impl.service.PerfilService;
import br.impl.service.SystemConfigService;
import gm.utils.classes.UClass;

@Component
public class CargaInicialCore {
	
	@Autowired ThreadScopeStart startThreadScope;
	@Autowired EntityServiceBox entityServiceBox;
	@Autowired ApplicationContext context;
	@Autowired PerfilService perfilService;
	@Autowired LoginService loginService;
	@Autowired ComandoService comandoService;
	@Autowired SystemConfigService systemConfigService;
	
	private static boolean executado = false;
	
	@Transactional
	public void exec() {
		
		if (executado) {
			return;
		}
		
		if (systemConfigService.atualizado()) {
			return;
		}
		
		startThreadScope.start(loginService.find(1), comandoService.find(1));
		
		Class<? extends IExec> classe = UClass.getClass("br.outros.CargaInicial");
		if (classe != null) {
			IExec cargaInicial = context.getBean(classe);
			cargaInicial.exec();
		}
		
		executado = true;
		systemConfigService.atualizar();
		
	}

	
}
