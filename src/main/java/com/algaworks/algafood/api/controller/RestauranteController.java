package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository repo;
	
	
	@Autowired
	private RestauranteService service;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Restaurante> lista = repo.findAll();
		
		return ResponseEntity.ok().body(lista);
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> obj = repo.findById(restauranteId);
		
		if(obj.isPresent()) {
			return ResponseEntity.ok(obj.get());
			
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> salva(@RequestBody Restaurante restaurante) {
		try {
		Restaurante salvaRes = service.salvar(restaurante);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvaRes);
		
		}catch (EntidadeNaoEncontradaException e) {

		return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante,@PathVariable Long restauranteId) {
		try {
			
			Optional<Restaurante> obj = repo.findById(restauranteId);
		
		if(obj.isPresent()) {
			BeanUtils.copyProperties(restaurante, obj.get(), "id", "formaPagamento", "endereco", "dataCadastro");
			Restaurante restauranteSalva = service.salvar(obj.get());
			return ResponseEntity.ok(restauranteSalva);
		}
		
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
	}
	
}
