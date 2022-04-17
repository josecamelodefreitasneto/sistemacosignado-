package br.auto.service;

import br.auto.model.Arquivo;
import br.auto.select.ArquivoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class ArquivoServiceAbstract extends ServiceModelo<Arquivo> {
	@Override
	public Class<Arquivo> getClasse() {
		return Arquivo.class;
	}
	@Override
	public MapSO toMap(final Arquivo o, final boolean listas) {
		setTransients(o);
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getNome() != null) {
			map.put("nome", o.getNome());
		}
		if (o.getTamanho() != null) {
			map.put("tamanho", o.getTamanho());
		}
		if (o.getType() != null) {
			map.put("type", o.getType());
		}
		if (o.getUri() != null) {
			map.put("uri", o.getUri());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Arquivo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Arquivo o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setNome(mp.getString("nome"));
		o.setType(mp.getString("type"));
		o.setUri(mp.getString("uri"));
		setTransients(o);
		return o;
	}
	@Override
	public Arquivo newO() {
		Arquivo o = new Arquivo();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Arquivo o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Arquivo > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 500) {
			throw new MessageException("O campo Arquivo > Nome aceita no máximo 500 caracteres");
		}
		validar2(o);
		o.setChecksum(tratarString(o.getChecksum()));
		if (o.getChecksum() == null) {
			throw new MessageException("O campo Arquivo > Checksum é obrigatório");
		}
		if (UString.length(o.getChecksum()) > 50) {
			throw new MessageException("O campo Arquivo > Checksum aceita no máximo 50 caracteres");
		}
		if (o.getExtensao() == null) {
			throw new MessageException("O campo Arquivo > Extensão é obrigatório");
		}
		if (o.getPath() == null) {
			throw new MessageException("O campo Arquivo > Path é obrigatório");
		}
		if (o.getTamanho() == null) {
			throw new MessageException("O campo Arquivo > Tamanho é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueChecksumExtensao(o);
		}
		validar3(o);
	}
	public void validarUniqueChecksumExtensao(final Arquivo o) {
		ArquivoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.checksum().eq(o.getChecksum());
		select.extensao().eq(o.getExtensao());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("arquivo_checksum_extensao"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Arquivo.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	public ResultadoConsulta consulta(final MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Arquivo> func) {
		final ArquivoSelect<?> select = select();
		Integer pagina = params.getInt("pagina");
		String busca = params.getString("busca");
		if (!UString.isEmpty(busca)) select.busca().like(UString.toCampoBusca(busca));
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.registros = select.count();
		} else {
			select.page(pagina);
		}
		select.limit(30);
		Lst<Arquivo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Arquivo buscaUnicoObrig(final MapSO params) {
		final ArquivoSelect<?> select = select();
		String checksum = params.getString("checksum");
		if (!UString.isEmpty(checksum)) select.checksum().eq(checksum);
		Integer extensao = getId(params, "extensao");
		if (extensao != null) {
			select.extensao().id().eq(extensao);
		}
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Integer path = getId(params, "path");
		if (path != null) {
			select.path().id().eq(path);
		}
		Integer tamanho = params.get("tamanho");
		if (tamanho != null) select.tamanho().eq(tamanho);
		Arquivo o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (checksum != null) {
			s += "&& checksum = '" + checksum + "'";
		}
		if (extensao != null) {
			s += "&& extensao = '" + extensao + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (path != null) {
			s += "&& path = '" + path + "'";
		}
		if (tamanho != null) {
			s += "&& tamanho = '" + tamanho + "'";
		}
		s = "Não foi encontrado um Arquivo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected Arquivo setOld(final Arquivo o) {
		Arquivo old = newO();
		old.setChecksum(o.getChecksum());
		old.setExtensao(o.getExtensao());
		old.setNome(o.getNome());
		old.setPath(o.getPath());
		old.setTamanho(o.getTamanho());
		o.setOld(old);
		return o;
	}
	public ArquivoSelect<?> select(final Boolean excluido) {
		ArquivoSelect<?> o = new ArquivoSelect<ArquivoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ArquivoSelect<?> select() {
		return select(false);
	}
	protected void setTransients(final Arquivo o) {
		o.setType(getType(o));
		o.setUri(getUri(o));
	}
	protected abstract String getType(final Arquivo o);
	protected abstract String getUri(final Arquivo o);
	@Override
	protected void setBusca(final Arquivo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Arquivo o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Arquivo");
		list.add("nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("arquivo_checksum_extensao", "A combinação de campos Checksum + Extensão não pode se repetir. Já existe um registro com esta combinação.");
	}
}
