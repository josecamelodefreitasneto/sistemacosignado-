package br.tcc.service;

import br.tcc.model.Arquivo;
import br.tcc.model.ArquivoExtensao;
import br.tcc.model.ArquivoPath;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ArquivoSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.number.UInteger;

@Component
public class ArquivoService extends ServiceModelo<Arquivo> {

	@Override
	public Class<Arquivo> getClasse() {
		return Arquivo.class;
	}

	@Override
	public MapSO toMap(Arquivo o, final boolean listas) {
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
	protected final void validar(Arquivo o) {
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Arquivo > Nome ?? obrigat??rio");
		}
		if (UString.length(o.getNome()) > 500) {
			throw new MessageException("O campo Arquivo > Nome aceita no m??ximo 500 caracteres");
		}
		validar2(o);
		o.setChecksum(tratarString(o.getChecksum()));
		if (o.getChecksum() == null) {
			throw new MessageException("O campo Arquivo > Checksum ?? obrigat??rio");
		}
		if (UString.length(o.getChecksum()) > 50) {
			throw new MessageException("O campo Arquivo > Checksum aceita no m??ximo 50 caracteres");
		}
		if (o.getExtensao() == null) {
			throw new MessageException("O campo Arquivo > Extens??o ?? obrigat??rio");
		}
		if (o.getPath() == null) {
			throw new MessageException("O campo Arquivo > Path ?? obrigat??rio");
		}
		if (o.getTamanho() == null) {
			throw new MessageException("O campo Arquivo > Tamanho ?? obrigat??rio");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueChecksumExtensao(o);
		}
		validar3(o);
	}

	public void validarUniqueChecksumExtensao(Arquivo o) {
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
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Arquivo> func) {
		final ArquivoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "checksum", select.checksum());
		FiltroConsulta.fk(params, "extensao", select.extensao());
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.fk(params, "path", select.path());
		FiltroConsulta.integer(params, "tamanho", select.tamanho());
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
		Lst<Arquivo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Arquivo buscaUnicoObrig(MapSO params) {
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
		s = "N??o foi encontrado um Arquivo com os seguintes crit??rios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Arquivo setOld(Arquivo o) {
		Arquivo old = newO();
		old.setChecksum(o.getChecksum());
		old.setExtensao(o.getExtensao());
		old.setNome(o.getNome());
		old.setPath(o.getPath());
		old.setTamanho(o.getTamanho());
		o.setOld(old);
		return o;
	}

	public ArquivoSelect<?> select(Boolean excluido) {
		ArquivoSelect<?> o = new ArquivoSelect<ArquivoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public ArquivoSelect<?> select() {
		return select(false);
	}

	protected void setTransients(Arquivo o) {
		o.setType(getType(o));
		o.setUri(getUri(o));
	}



	@Override
	protected void setBusca(Arquivo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Arquivo o) {
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
		CONSTRAINTS_MESSAGES.put("arquivo_checksum_extensao", "A combina????o de campos Checksum + Extens??o n??o pode se repetir. J?? existe um registro com esta combina????o.");
	}

	@Autowired ArquivoPathService arquivoPathService;
	@Autowired ArquivoExtensaoService arquivoExtensaoService;

	protected String getUri(Arquivo o) {
		final String extensao = o.getExtensao().getNome();
		return "http://localhost:8080/arquivo/download/"+extensao+"/"+o.getChecksum()+"."+extensao;
	}

	public Arquivo get(MapSO map) {

		if (map == null) {
			return null;
		}

		try {

			final int id = map.id();
			if (id > 0) {
				return this.find(id);
			}

			final String nome = map.getString("nome");

			final String uri = map.getString("uri");

			final String base64 = UString.afterFirst(uri, "base64,");
//			int tamanho = UInteger.toInt(base64.replace("=", "").length() * 0.75);
			final byte[] bytes = java.util.Base64.getDecoder().decode(base64);

			return get(nome, bytes);


		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Arquivo getArquivo(ListString list) {
		String nome;
		if (UString.isEmpty(list.getFileName())) {
			nome = "arquivo.txt";
		} else {
			nome = UString.afterLast("/" + list.getFileName(), "/");
		}
		return get(list, nome);
	}

	public Arquivo get(ListString list, String nome) {
		byte[] bytes = list.toString("\n").getBytes();
		return get(nome, bytes);
//		try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			DataOutputStream out = new DataOutputStream(baos);
//			for (String element : list) {
//			    out.writeUTF(element);
//			}
//			byte[] bytes = baos.toByteArray();
//			return get(nome, bytes);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}

	public Arquivo get(String nome, byte[] bytes) {

		final ArquivoExtensao extensao = arquivoExtensaoService.get(UString.afterFirst(nome, "."));

		final String checksum = ArquivoService.getFileChecksum(bytes);

		Arquivo o = select().extensao().eq(extensao).checksum().eq(checksum).unique();
		if (o != null) return o;
		o = newO();
		o.setNome(nome);
		o.setChecksum(checksum);
		o.setExtensao(extensao);
		final ArquivoPath arquivoPath = arquivoPathService.get(extensao);
		o.setPath(arquivoPath);
		o.setTamanho(bytes.length);

		final String path = getFileName(o);
		if (!fileExists(o)) {
			try (OutputStream stream = new FileOutputStream(path)) {
				stream.write(bytes);
			} catch (Exception e) {
				throw UException.runtime(e);
			}
		}
		if (o.getTamanho() == 0) {
			final long size = UFile.size(Paths.get(path));
			o.setTamanho(UInteger.toInt(size));
		}

		o = this.save(o);

		arquivoPathService.incrementarItem(arquivoPath);

		return o;
	}

	public boolean fileExists(Arquivo o) {
		return UFile.exists(getFileName(o));
	}

	private static String getFileChecksum(byte[] data) {

		try {
			final MessageDigest digest = MessageDigest.getInstance("MD5");
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
			final byte[] byteArray = new byte[1024];
			int count = 0;
			while ((count = byteArrayInputStream.read(byteArray)) != -1) {
				digest.update(byteArray, 0, count);
			}
			byteArrayInputStream.close();
			final byte[] bytes = digest.digest();
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public String getFileName(Arquivo o) {
		return o.getPath().getNome() + "/" + o.getChecksum() + "." + o.getExtensao().getNome();
	}

	protected String getType(Arquivo o) {
		return null;
	}

}
