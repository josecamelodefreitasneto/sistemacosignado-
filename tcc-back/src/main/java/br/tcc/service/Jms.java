package br.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.tcc.outros.ThreadScope;

@Component
public class Jms {

	@Autowired JmsTemplate jmsTemplate;
	
	public void send(String destination, Object o) {
		ThreadScope.addOnSuccess(() -> exec(destination, o));
	}

	private void exec(String destination, Object o) {
		jmsTemplate.convertAndSend(destination, o);
	}
	
}
