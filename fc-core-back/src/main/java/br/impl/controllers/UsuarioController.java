package br.impl.controllers;

import br.auto.controllers.UsuarioControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="usuario/")
public class UsuarioController extends UsuarioControllerAbstract {}
