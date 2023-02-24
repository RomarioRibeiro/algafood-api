package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.notificacao.NivelUrgecia;
import com.algaworks.algafood.notificacao.Notificador;
import com.algaworks.algafood.notificacao.TipoDoNotificador;
import com.algaworks.algafood.service.ClienteAtivarEvent;

@Component
public class NotificarClienteListener {
	
	@TipoDoNotificador(NivelUrgecia.NORMAL)
	@Autowired
	private Notificador notificador;
	
	@EventListener
	public void ClienteAtivoListener(ClienteAtivarEvent ativarEvent) {
		
		notificador.notificar(ativarEvent.getCliente(), "Seu cadastro esta ativo");
		
	}

}
