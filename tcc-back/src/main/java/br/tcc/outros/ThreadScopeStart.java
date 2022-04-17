package br.tcc.outros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.model.Comando;
import br.tcc.model.Entidade;
import br.tcc.model.Login;
import br.tcc.service.AuditoriaEntidadeService;
import br.tcc.service.ComandoService;
import br.tcc.service.LoginService;
import gm.utils.map.MapSO;

@Component
public class ThreadScopeStart {

	@Autowired LoginService loginService;
	@Autowired ComandoService comandoService;
	@Autowired AuditoriaEntidadeService auditoriaEntidadeService;

	public void start(Entidade entidade, MapSO map, String nomeComando) {
		String token = map.getObrig("token");
		Comando comando = comandoService.get(entidade, nomeComando);
		Login login = loginService.valida(token, comando);
		start(login, comando);
	}
	public void start(Login login, Comando comando) {
		start(login.getId(), comando.getId());
	}
	public void start(int login, int comando) {
		if (ThreadScope.get() == null) {
			new ThreadScope(login, comando);
			ThreadScope.addOnSuccess(() -> auditoriaEntidadeService.registrar());
		}
	}

}

