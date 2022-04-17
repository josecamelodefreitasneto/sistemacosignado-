package br.auto.service;

import br.auto.model.MudarSenha;
import br.impl.outros.ServiceModelo;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;

public abstract class MudarSenhaServiceAbstract extends ServiceModelo<MudarSenha> {

	@Override
	public Class<MudarSenha> getClasse() {
		return MudarSenha.class;
	}

	@Override
	public MapSO toMap(final MudarSenha o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getConfirmarSenha() != null) {
			map.put("confirmarSenha", o.getConfirmarSenha());
		}
		if (o.getNovaSenha() != null) {
			map.put("novaSenha", o.getNovaSenha());
		}
		if (o.getSenhaAtual() != null) {
			map.put("senhaAtual", o.getSenhaAtual());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected MudarSenha fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final MudarSenha o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setConfirmarSenha(mp.getString("confirmarSenha"));
		o.setNovaSenha(mp.getString("novaSenha"));
		o.setSenhaAtual(mp.getString("senhaAtual"));
		return o;
	}

	@Override
	public MudarSenha newO() {
		MudarSenha o = new MudarSenha();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(final MudarSenha o) {
		o.setConfirmarSenha(tratarString(o.getConfirmarSenha()));
		if (o.getConfirmarSenha() == null) {
			throw new MessageException("O campo Mudar Senha > Confirmar Senha é obrigatório");
		}
		if (UString.length(o.getConfirmarSenha()) > 50) {
			throw new MessageException("O campo Mudar Senha > Confirmar Senha aceita no máximo 50 caracteres");
		}
		o.setNovaSenha(tratarString(o.getNovaSenha()));
		if (o.getNovaSenha() == null) {
			throw new MessageException("O campo Mudar Senha > Nova Senha é obrigatório");
		}
		if (UString.length(o.getNovaSenha()) > 50) {
			throw new MessageException("O campo Mudar Senha > Nova Senha aceita no máximo 50 caracteres");
		}
		o.setSenhaAtual(tratarString(o.getSenhaAtual()));
		if (o.getSenhaAtual() == null) {
			throw new MessageException("O campo Mudar Senha > Senha Atual é obrigatório");
		}
		if (UString.length(o.getSenhaAtual()) > 50) {
			throw new MessageException("O campo Mudar Senha > Senha Atual aceita no máximo 50 caracteres");
		}
		validar2(o);
		validar3(o);
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected MudarSenha buscaUnicoObrig(final MapSO params) {
		throw new MessageException("?");
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected abstract void persistInsert(final MudarSenha o);

	@Override
	public String getText(final MudarSenha o) {
		if (o == null) return null;
		return o.getConfirmarSenha();
	}

	@Override
	public MudarSenha findNotObrig(final int id) {
		throw new MessageException("?");
	}
}
