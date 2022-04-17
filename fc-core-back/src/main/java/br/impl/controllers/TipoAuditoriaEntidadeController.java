package br.impl.controllers;

import br.auto.controllers.TipoAuditoriaEntidadeControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="tipo-auditoria-entidade/")
public class TipoAuditoriaEntidadeController extends TipoAuditoriaEntidadeControllerAbstract {}
