package br.tcc.controllers;

import br.tcc.service.OrgaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("orgao/")
public class OrgaoController extends ControllerModelo {

	@Autowired OrgaoService orgaoService;

	@Override
	protected OrgaoService getService() {
		return orgaoService;
	}
}
