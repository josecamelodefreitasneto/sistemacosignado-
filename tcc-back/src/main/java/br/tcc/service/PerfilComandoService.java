package br.tcc.service;

import br.tcc.model.PerfilComando;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.PerfilComandoSelect;
import br.tcc.service.ComandoService;
import br.tcc.service.PerfilService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PerfilComandoService extends ServiceModelo<PerfilComando> {

	@Autowired ComandoService comandoService;

	@Autowired PerfilService perfilService;

	@Override
	public Class<PerfilComando> getClasse() {
		return PerfilComando.class;
	}

	@Override
	public MapSO toMap(PerfilComando o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected PerfilComando fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final PerfilComando o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setComando(find(comandoService, mp, "comando"));
		o.setPerfil(find(perfilService, mp, "perfil"));
		return o;
	}

	@Override
	public PerfilComando newO() {
		PerfilComando o = new PerfilComando();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(PerfilComando o) {
		if (o.getComando() == null) {
			throw new MessageException("O campo Perfil Comando > Comando é obrigatório");
		}
		if (o.getPerfil() == null) {
			throw new MessageException("O campo Perfil Comando > Perfil é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueComandoPerfil(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueComandoPerfil(PerfilComando o) {
		PerfilComandoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.comando().eq(o.getComando());
		select.perfil().eq(o.getPerfil());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("perfilcomando_comando_perfil"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.PerfilComando.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, PerfilComando> func) {
		final PerfilComandoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "comando", select.comando());
		FiltroConsulta.fk(params, "perfil", select.perfil());
		FiltroConsulta.bool(params, "excluido", select.excluido());
		FiltroConsulta.bool(params, "registroBloqueado", select.registroBloqueado());
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.pagina = 1;
			result.registros = select.count();
			result.paginas = result.registros / 30;
			if (result.registros > result.paginas * 30) {
				result.paginas++;
			}
		} else {
			select.page(pagina);
			result.pagina = pagina;
		}
		select.limit(30);
		Lst<PerfilComando> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected PerfilComando buscaUnicoObrig(MapSO params) {
		final PerfilComandoSelect<?> select = select();
		Integer comando = getId(params, "comando");
		if (comando != null) {
			select.comando().id().eq(comando);
		}
		Integer perfil = getId(params, "perfil");
		if (perfil != null) {
			select.perfil().id().eq(perfil);
		}
		PerfilComando o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (comando != null) {
			s += "&& comando = '" + comando + "'";
		}
		if (perfil != null) {
			s += "&& perfil = '" + perfil + "'";
		}
		s = "Não foi encontrado um PerfilComando com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected PerfilComando setOld(PerfilComando o) {
		PerfilComando old = newO();
		old.setComando(o.getComando());
		old.setPerfil(o.getPerfil());
		o.setOld(old);
		return o;
	}

	public PerfilComandoSelect<?> select(Boolean excluido) {
		PerfilComandoSelect<?> o = new PerfilComandoSelect<PerfilComandoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public PerfilComandoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(PerfilComando o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(PerfilComando o) {
		if (o == null) return null;
		return comandoService.getText(o.getComando());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("PerfilComando");
		list.add("perfil;comando");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("perfilcomando_comando_perfil", "A combinação de campos Comando + Perfil não pode se repetir. Já existe um registro com esta combinação.");
	}
}
