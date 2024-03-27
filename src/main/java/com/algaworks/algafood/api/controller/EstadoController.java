package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository repo;

	@Autowired
	private EstadoService service;

	@GetMapping
	public ResponseEntity<?> lista() {
		List<Estado> lista = repo.findAll();

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
		Optional<Estado> obj = repo.findById(estadoId);

		if (obj.isPresent()) {
			return ResponseEntity.ok(obj.get());

		}
		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		Estado obj = repo.save(estado);

		return ResponseEntity.created(null).body(obj);

	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar(@RequestBody Estado estado,@PathVariable Long estadoId) {
		try {
			Optional<Estado> obj = repo.findById(estadoId);

			if (obj.isPresent()) {
				BeanUtils.copyProperties(estado, obj.get(), "id", "dataCadastro");
				Estado estadoSalva = service.salvar(obj.get());
				return ResponseEntity.ok(estadoSalva);
			}

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		}
		return ResponseEntity.notFound().build();

	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> deletar(@PathVariable Long estadoId) {
		try {
			service.deleta(estadoId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			
		}
		
	}

}
