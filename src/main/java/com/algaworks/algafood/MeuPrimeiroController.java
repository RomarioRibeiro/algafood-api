package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoClienteService;
	
	public MeuPrimeiroController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;
		
	}

	@GetMapping("/hello")
	@ResponseBody
	public String Hello() {
		Cliente joao = new Cliente("João", "11111111", "joao@gmail.com");
		ativacaoClienteService.ativar(joao);
		
		return "Hello";
	}
	
}
