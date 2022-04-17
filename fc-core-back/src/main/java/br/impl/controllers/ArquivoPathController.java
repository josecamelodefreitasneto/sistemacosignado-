package br.impl.controllers;

import br.auto.controllers.ArquivoPathControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="arquivo-path/")
public class ArquivoPathController extends ArquivoPathControllerAbstract {}
