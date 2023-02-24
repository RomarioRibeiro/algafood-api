package com.algaworks.algafood.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.service.ClienteAtivarEvent;

@Component
public class EmitirNotaFiscalLiestener {
	
	@EventListener
	public void EmitirNotaFiscal(ClienteAtivarEvent event) {
		System.out.println("Emitindo uma nota fiscal para " + event.getCliente().getNome());
	}
	
}
