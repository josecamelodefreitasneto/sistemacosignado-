package br.tcc.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.tcc.model.Comando;
import br.tcc.model.Entidade;
import br.tcc.model.Perfil;
import br.tcc.model.PerfilComando;
import br.tcc.model.Usuario;
import br.tcc.model.UsuarioPerfil;
import br.tcc.outros.EntityServiceBox;
import br.tcc.outros.IDS;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.IExec;
import br.tcc.outros.ThreadScopeStart;
import br.tcc.service.CepService;
import br.tcc.service.ComandoService;
import br.tcc.service.EntidadeService;
import br.tcc.service.ImportacaoArquivoErroMensagemService;
import br.tcc.service.LoginService;
import br.tcc.service.PerfilComandoService;
import br.tcc.service.PerfilService;
import br.tcc.service.Perfis;
import br.tcc.service.SystemConfigService;
import br.tcc.service.UsuarioPerfilService;
import br.tcc.service.UsuarioService;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;

@Component
public class CargaInicial {

	@Autowired Perfis perfis;
	@Autowired CepService cepService;
	@Autowired LoginService loginService;
	@Autowired PerfilService perfilService;
	@Autowired UsuarioService usuarioService;
	@Autowired ComandoService comandoService;
	@Autowired EntidadeService entidadeService;
	@Autowired ThreadScopeStart startThreadScope;
	@Autowired EntityServiceBox entityServiceBox;
	@Autowired ApplicationContext context;
	@Autowired SystemConfigService systemConfigService;
	@Autowired PerfilComandoService perfilComandoService;
	@Autowired UsuarioPerfilService usuarioPerfilService;
	@Autowired ImportacaoArquivoErroMensagemService importacaoArquivoErroMensagemService;
	
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

		importacaoArquivoErroMensagemService.cargaInicial();
		
		perfis.cargaInicial();
		
		Perfil perfilAdmin = perfis.administrador();
		Usuario usuarioAdmin = getUsuarioAdmin();
		addAdminAdmin(perfilAdmin, usuarioAdmin);
		addComandos(perfilAdmin, entidadeService.find(IDSDefault.ImportacaoArquivo.idEntidade));
		addComandos(perfilAdmin, entidadeService.find(IDS.Cliente.idEntidade));
		addComandos(perfilAdmin, entidadeService.find(IDS.Atendente.idEntidade));
		addComandos(perfilAdmin, entidadeService.find(IDS.ClienteRubrica.idEntidade));
		addComandos(perfilAdmin, entidadeService.find(IDS.ClienteSimulacao.idEntidade));
		
		Perfil perfilAtendnte = perfis.atendente();
		
		addComandos(perfilAtendnte, comandoService.select()
			.entidade().eq(entidadeService.find(IDS.Cliente.idEntidade))
			.nome().in("read","update")
			.list()
		);
		
	}

	private void addComandos(Perfil perfil, Entidade cliente) {
		Lst<Comando> comandos = comandoService.select().entidade().eq(cliente).list();
		addComandos(perfil, comandos);
	}

	private void addComandos(Perfil perfil, Lst<Comando> comandos) {
		for (Comando comando : comandos) {
			addComando(perfil, comando);
		}
	}

	private void addComando(Perfil perfil, Comando comando) {
		if (!perfilComandoService.select().perfil().eq(perfil).comando().eq(comando).exists()) {
			PerfilComando o = new PerfilComando();
			o.setPerfil(perfil);
			o.setComando(comando);
			perfilComandoService.save(o);
		}
	}

	private Usuario getUsuarioAdmin() {
		Usuario o = usuarioService.findNotObrig(2);
		if (o == null) {
			o = new Usuario();
			o.setExcluido(false);
			o.setRegistroBloqueado(false);
			o.setLogin("gerente@sysconsig.com");
			o.setSenha("*");
			o.setNome("Gerente");
			o.setBusca("gerente");
			o.setSenha(UsuarioService.criptografarSenha(2, "123456"));
			o = usuarioService.save(o);
		}
		return o;
	}
	private void addAdminAdmin(Perfil perfil, Usuario usuario) {
		if (!usuarioPerfilService.select().perfil().eq(perfil).usuario().eq(usuario).exists()) {
			UsuarioPerfil o = new UsuarioPerfil();
			o.setUsuario(usuario);
			o.setPerfil(perfil);
			o.setExcluido(false);
			o.setRegistroBloqueado(false);
			o.setBusca("*");
			usuarioPerfilService.save(o);
		}
	}
	
}
