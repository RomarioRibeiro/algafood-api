package com.algaworks.algafood.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificar.email")
public class NotificadorProperties {

	private String hostServidor;
	
	private Integer portServidor = 25;

	public String getHostServidor() {
		return hostServidor;
	}

	public void setHostServidor(String hostServidor) {
		this.hostServidor = hostServidor;
	}

	public Integer getPortServidor() {
		return portServidor;
	}

	public void setPortServidor(Integer portServidor) {
		this.portServidor = portServidor;
	}

}
