package com.algaworks.algafood.di;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.emails")
public class NotificadorProperties {
	
	/**
	 * Host do Servidor E-mail
	 */
	private String hostServidor;
	/**
	 * Port do Servidor E-mail
	 */
	private Integer portServidor = 26;

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
