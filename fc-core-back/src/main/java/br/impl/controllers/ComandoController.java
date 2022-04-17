package br.impl.controllers;

import br.auto.controllers.ComandoControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="comando/")
public class ComandoController extends ComandoControllerAbstract {}
