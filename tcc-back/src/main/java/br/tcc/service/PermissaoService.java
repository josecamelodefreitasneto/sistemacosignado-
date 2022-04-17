package br.tcc.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.model.Entidade;
import br.tcc.model.Perfil;
import br.tcc.model.Usuario;
import br.tcc.outros.PermissaoDTO;
import br.tcc.select.PerfilComandoSelect;
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
		
		boolean admin = perfis.exists(o -> o.getId() == 1);
		
		for (Entidade entidade : entidades) {
			PerfilComandoSelect<?> select = perfilComandoService.select();
			if (!admin) {
				select.perfil().in(perfis);
			}
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
