package br.tcc.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import br.tcc.model.Login;
import br.tcc.model.Usuario;
import br.tcc.service.EsqueciSenhaService;
import br.tcc.service.PermissaoService;
import br.tcc.service.UsuarioService;
import gm.utils.map.MapSO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="esqueci-senha/")
public class EsqueciSenhaController extends ControllerModelo {

	@Autowired EsqueciSenhaService esqueciSenhaService;

	@Override
	protected EsqueciSenhaService getService() {
		return esqueciSenhaService;
	}

	@Autowired UsuarioService usuarioService;
	@Autowired PermissaoService permissaoService;

	@RequestMapping(value="enviar-email", method=RequestMethod.POST)
	public ResponseEntity<Object> enviarEmail(@RequestBody final MapSO map) {
		return ok(() -> {
			getService().enviarEmail(map.getStringObrig("value"));
			return new MapSO().add("messgae", "E-mail enviado com sucesso!");
		});
	}

	@RequestMapping(value="confirmar-codigo", method=RequestMethod.POST)
	public ResponseEntity<Object> confirmarCodigo(@RequestBody final MapSO map) {
		return ok(() -> {
			String login = map.getStringObrig("login");
			Usuario usuario = usuarioService.findByLoginObrig(login);
			String codigo = map.getStringObrig("codigo");
			getService().confirmarCodigo(usuario, codigo);
			Login o = loginService.exec(usuario);
			MapSO so = new MapSO();
			so.setObrig("token", o.getToken());
			so.setObrig("nome", o.getUsuario().getNome());
			so.add("permissoes", permissaoService.getPermissoes(o.getUsuario()));
			return so;
		});
	}

}
