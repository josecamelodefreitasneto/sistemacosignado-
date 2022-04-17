package br.impl.controllers;

import br.auto.controllers.AuditoriaCampoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="auditoria-campo/")
public class AuditoriaCampoController extends AuditoriaCampoControllerAbstract {}
