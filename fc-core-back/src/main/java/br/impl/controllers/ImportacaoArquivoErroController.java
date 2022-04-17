package br.impl.controllers;

import br.auto.controllers.ImportacaoArquivoErroControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="importacao-arquivo-erro/")
public class ImportacaoArquivoErroController extends ImportacaoArquivoErroControllerAbstract {}
