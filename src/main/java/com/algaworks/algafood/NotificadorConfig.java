package com.algaworks.algafood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.notificacao.NotificadorEmail;

//@Configuration
public class NotificadorConfig {

	@Bean
	public NotificadorEmail notificadorEmail() {
		
		NotificadorEmail notificar = new NotificadorEmail("smtp@gmail.com");
		notificar.setCaixaAlta(false);
		return notificar;
	}
	
	
}
