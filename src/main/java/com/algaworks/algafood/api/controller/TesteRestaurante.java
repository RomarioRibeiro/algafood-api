package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.domain.infrastructure.repositoryImpl.spec.RestauranteSpec.comFreteGratis;
import static com.algaworks.algafood.domain.infrastructure.repositoryImpl.spec.RestauranteSpec.nomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteRestaurante {

	@Autowired
	private RestauranteRepository repo;
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantePorNome(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return repo.find(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome) {
		
		return repo.findAll(comFreteGratis().and(nomeSemelhante(nome)));
	}
	
	
	@GetMapping("/restaurantes/primeiros")
	public Optional<Restaurante> buscarprimeiro() {
		return repo.buscarPrimeirp();
	}
	
	
}
