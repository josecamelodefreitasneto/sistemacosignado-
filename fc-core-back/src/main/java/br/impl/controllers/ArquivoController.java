package br.impl.controllers;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.auto.controllers.ArquivoControllerAbstract;
import br.auto.model.Arquivo;
import br.auto.model.ArquivoExtensao;
import br.impl.service.ArquivoExtensaoService;
import gm.utils.files.UFile;

@RestController
@RequestMapping(value = "arquivo/")
public class ArquivoController extends ArquivoControllerAbstract {

	@Autowired
	ArquivoExtensaoService arquivoExtensaoService;

	@RequestMapping(value = "download/{extensao}/{checksum}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadBytes(@PathVariable("extensao") final String extensao, @PathVariable("checksum") final String checksum) {
		final File file = getFile(getArquivo(extensao, checksum));
		final byte[] bytes = UFile.getBytes(file);
		return ResponseEntity.ok(bytes);
	}

	private File getFile(final Arquivo arquivo) {
		final String fileName = getService().getFileName(arquivo);
		final File file = new File(fileName);
		return file;
	}

	private Arquivo getArquivo(final String extensao, final String checksum) {
		final ArquivoExtensao ext = arquivoExtensaoService.select(null).nome().eq(extensao).unique();
		final Arquivo arquivo = getService().select(null).extensao().eq(ext).checksum().eq(checksum).unique();
		return arquivo;
	}

	@RequestMapping(value = "download/stream/{extensao}/{checksum}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> download(@PathVariable("extensao") final String extensao, @PathVariable("checksum") final String checksum) {

		try {
			
			Arquivo arquivo = getArquivo(extensao, checksum);
			final File file = getFile(arquivo);

			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + arquivo.getNome())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(file.length()) //
				.body(resource);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
