package br.impl.controllers;

import br.auto.controllers.FilaScriptsControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="fila-scripts/")
public class FilaScriptsController extends FilaScriptsControllerAbstract {}
