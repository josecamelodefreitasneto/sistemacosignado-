package br.impl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.auto.controllers.LoginControllerAbstract;
import br.auto.model.Login;
import br.impl.service.PermissaoService;
import gm.utils.map.MapSO;

@RestController @RequestMapping(value="login/")
public class LoginController extends LoginControllerAbstract {
	
	@Autowired PermissaoService permissaoService;

	@RequestMapping(value="efetuar", method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody final MapSO map) {
		return ok(() -> {
			final String login = map.getObrig("login");
			final String senha = map.getObrig("pass");
			final Login o = loginService.exec(login, senha);
			final MapSO so = new MapSO();
			so.setObrig("token", o.getToken());
			so.setObrig("nome", o.getUsuario().getNome());
			so.add("permissoes", permissaoService.getPermissoes(o.getUsuario()));
			return so;
		});

	}
	
}