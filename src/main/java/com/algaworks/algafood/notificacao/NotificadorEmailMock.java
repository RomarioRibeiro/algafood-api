package com.algaworks.algafood.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;

//@Primary
//@Profile("dev")
//@TipoDoNotificador(NivelUrgecia.NORMAL)
//@Component
public class NotificadorEmailMock implements Notificador {

	private boolean caixaAlta;
	private String hostServidoSmtp;

	public NotificadorEmailMock(String hostServidoSmtp) {
		this.hostServidoSmtp = hostServidoSmtp;
		System.out.println("Notificador Email");
	}

	@Autowired
	public NotificadorEmailMock() {
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		if (this.caixaAlta = true) {
			mensagem = mensagem.toUpperCase();
		}
		System.out.printf("Notificando MOCK: por E-MAIL %s %s em um Servidor %s: %s\n", cliente.getNome(), cliente.getEmail(),this.hostServidoSmtp, mensagem);

	}

	public void setCaixaAlta(boolean caixaAlta) {
		this.caixaAlta = caixaAlta;
	}

}
