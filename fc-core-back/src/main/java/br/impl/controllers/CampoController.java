package br.impl.controllers;

import br.auto.controllers.CampoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="campo/")
public class CampoController extends CampoControllerAbstract {}
