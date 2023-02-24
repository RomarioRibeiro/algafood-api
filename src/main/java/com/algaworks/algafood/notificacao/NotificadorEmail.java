package com.algaworks.algafood.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;

//@Primary
//@Profile("prod")
@TipoDoNotificador(NivelUrgecia.NORMAL)
@Component
public class NotificadorEmail implements Notificador {

	private boolean caixaAlta;
	private String hostServidoSmtp;
	
	@Autowired
	private NotificadorProperties propriedade;
	
	
	
	public NotificadorEmail(String hostServidoSmtp) {
		this.hostServidoSmtp = hostServidoSmtp;
		System.out.println("Notificador Email");
	}

	@Autowired
	public NotificadorEmail() {
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		if (this.caixaAlta = true) {
			mensagem = mensagem.toUpperCase();
		}
		
		System.out.println("HOST: " + propriedade.getHostServidor());
		System.out.println("PORTA: " + propriedade.getPortServidor());
		System.out.printf("Notificando por E-MaIL %s %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);

	}

	public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}

}
