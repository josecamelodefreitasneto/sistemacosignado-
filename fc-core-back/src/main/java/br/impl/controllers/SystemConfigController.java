package br.impl.controllers;

import br.auto.controllers.SystemConfigControllerAbstract;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping(value="system-config/")
public class SystemConfigController extends SystemConfigControllerAbstract {}
