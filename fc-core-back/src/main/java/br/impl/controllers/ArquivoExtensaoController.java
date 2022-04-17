package br.impl.controllers;

import br.auto.controllers.ArquivoExtensaoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="arquivo-extensao/")
public class ArquivoExtensaoController extends ArquivoExtensaoControllerAbstract {}
