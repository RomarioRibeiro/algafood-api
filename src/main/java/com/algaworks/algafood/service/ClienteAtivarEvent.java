package com.algaworks.algafood.service;

import com.algaworks.algafood.model.Cliente;

public class ClienteAtivarEvent {

private Cliente cliente;

public ClienteAtivarEvent(Cliente cliente) {
	super();
	this.cliente = cliente;
}

public Cliente getCliente() {
	return cliente;
}


	
}
