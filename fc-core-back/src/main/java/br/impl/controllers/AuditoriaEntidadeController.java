package br.impl.controllers;

import br.auto.controllers.AuditoriaEntidadeControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="auditoria-entidade/")
public class AuditoriaEntidadeController extends AuditoriaEntidadeControllerAbstract {}
