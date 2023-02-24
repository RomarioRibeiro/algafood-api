package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.notificacao.NivelUrgecia;
import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoDoNotificador;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class AtivacaoClienteService {
	
	@Autowired
	private ApplicationEventPublisher event;
	
//	@PostConstruct
//	public void init() {
//		System.out.println("INIT");
//	}
//	
//	@PreDestroy
//	public void destroy() {
//		System.out.println("DESTROY");
//	}
	
	
	
//	public AtivacaoClienteService(Notificador notificador) {
//		this.notificador = notificador;
//		
//		System.out.println("Ativador cliente" +  notificador);		
//	}

	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		event.publishEvent( new ClienteAtivarEvent(cliente));
		
		
//		
//		for(Notificador notificador : notificadores) {
//		}
	}
	
	
}
