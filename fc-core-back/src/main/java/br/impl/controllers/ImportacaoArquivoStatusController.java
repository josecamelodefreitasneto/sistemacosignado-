package br.impl.controllers;

import br.auto.controllers.ImportacaoArquivoStatusControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="importacao-arquivo-status/")
public class ImportacaoArquivoStatusController extends ImportacaoArquivoStatusControllerAbstract {}
