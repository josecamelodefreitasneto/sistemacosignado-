package br.auto.service;

import br.auto.model.EsqueciSenha;
import br.impl.outros.ServiceModelo;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;

public abstract class EsqueciSenhaServiceAbstract extends ServiceModelo<EsqueciSenha> {

	@Override
	public Class<EsqueciSenha> getClasse() {
		return EsqueciSenha.class;
	}

	@Override
	public MapSO toMap(final EsqueciSenha o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getConfirmarSenha() != null) {
			map.put("confirmarSenha", o.getConfirmarSenha());
		}
		if (o.getNovaSenha() != null) {
			map.put("novaSenha", o.getNovaSenha());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected EsqueciSenha fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final EsqueciSenha o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setConfirmarSenha(mp.getString("confirmarSenha"));
		o.setNovaSenha(mp.getString("novaSenha"));
		return o;
	}

	@Override
	public EsqueciSenha newO() {
		EsqueciSenha o = new EsqueciSenha();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(final EsqueciSenha o) {
		o.setConfirmarSenha(tratarString(o.getConfirmarSenha()));
		if (o.getConfirmarSenha() == null) {
			throw new MessageException("O campo Esqueci Senha > Confirmar Senha é obrigatório");
		}
		if (UString.length(o.getConfirmarSenha()) > 50) {
			throw new MessageException("O campo Esqueci Senha > Confirmar Senha aceita no máximo 50 caracteres");
		}
		o.setNovaSenha(tratarString(o.getNovaSenha()));
		if (o.getNovaSenha() == null) {
			throw new MessageException("O campo Esqueci Senha > Nova Senha é obrigatório");
		}
		if (UString.length(o.getNovaSenha()) > 50) {
			throw new MessageException("O campo Esqueci Senha > Nova Senha aceita no máximo 50 caracteres");
		}
		validar2(o);
		validar3(o);
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected EsqueciSenha buscaUnicoObrig(final MapSO params) {
		throw new MessageException("?");
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected abstract void persistInsert(final EsqueciSenha o);

	@Override
	public String getText(final EsqueciSenha o) {
		if (o == null) return null;
		return o.getConfirmarSenha();
	}

	@Override
	public EsqueciSenha findNotObrig(final int id) {
		throw new MessageException("?");
	}
}
