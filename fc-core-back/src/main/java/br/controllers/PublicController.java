package br.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {
	
	@RequestMapping("/")
	public String def() {
		return "Tudo ok por aqui";
	}
	
	@RequestMapping("headless-content.js.map")
	public String a() {
		return null;
	}
	@RequestMapping("contentDocumentStart.js")
	public String b() {
		return null;
	}
	
}
