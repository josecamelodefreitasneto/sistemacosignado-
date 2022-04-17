package br.impl.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.auto.model.Entidade;
import br.auto.model.Perfil;
import br.auto.model.Usuario;
import br.auto.select.PerfilComandoSelect;
import br.impl.outros.PermissaoDTO;
import gm.utils.comum.Lst;

@Component
public class PermissaoService {
	
	@Autowired EntidadeService entidadeService;
	@Autowired UsuarioPerfilService usuarioPerfilService;
	@Autowired PerfilComandoService perfilComandoService;

	public List<PermissaoDTO> getPermissoes(Usuario usuario) {
		Lst<Perfil> perfis = usuarioPerfilService.select().usuario().eq(usuario).perfil().distinct();
		if (perfis.isEmpty()) {
			return new ArrayList<>();
		}
		return getPermissoes(perfis);
	}

	public List<PermissaoDTO> getPermissoes(Lst<Perfil> perfis) {
		
		Lst<Entidade> entidades = entidadeService.select().list();
		List<PermissaoDTO> list = new ArrayList<>();
		
		for (Entidade entidade : entidades) {
			PerfilComandoSelect<?> select = perfilComandoService.select();
			select.perfil().in(perfis);
			select.comando().entidade().eq(entidade);
			PermissaoDTO o = new PermissaoDTO();
			list.add(o);
			o.setId(entidade.getId());
			o.setNome(entidade.getNomeClasse());
			o.setComandos(select.comando().distinct().map(comando -> comando.getNome()));
		}
		
		return list;
	}
}