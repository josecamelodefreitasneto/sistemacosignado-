package br.impl.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.Arquivo;
import br.auto.model.ArquivoExtensao;
import br.auto.model.ArquivoPath;
import br.auto.service.ArquivoServiceAbstract;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.map.MapSO;
import gm.utils.number.UInteger;
import gm.utils.string.ListString;
import gm.utils.string.UString;

@Component
public class ArquivoService extends ArquivoServiceAbstract {

	@Autowired ArquivoPathService arquivoPathService;
	@Autowired ArquivoExtensaoService arquivoExtensaoService;
	
	@Override
	protected String getUri(Arquivo o) {
		final String extensao = o.getExtensao().getNome();
		return "http://localhost:8080/arquivo/download/"+extensao+"/"+o.getChecksum()+"."+extensao;
	}
	
	public Arquivo get(final MapSO map) {
		
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
			
			
		} catch (final Exception e) {
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
			} catch (final Exception e) {
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
	
	public boolean fileExists(final Arquivo o) {
		return UFile.exists(getFileName(o));
	}
	
	private static String getFileChecksum(final byte[] data) {
		
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
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public String getFileName(final Arquivo o) {
		return o.getPath().getNome() + "/" + o.getChecksum() + "." + o.getExtensao().getNome();
	}

	@Override
	protected String getType(Arquivo o) {
		return null;
	}
	
}