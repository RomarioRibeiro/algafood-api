package com.algaworks.algafood.notificacao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;
//@Primary
@TipoDoNotificador(NivelUrgecia.URGENTE)
@Component
public class NotificadorSMS implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		System.out.printf("Notificando por SMS para %s no numero %s: %S\n ", cliente.getNome(), cliente.getTelefone(), mensagem);
		
	}

	
	
	
}
