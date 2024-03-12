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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.cidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository repo;
	
	@Autowired
	private cidadeService service;
	
	@GetMapping
	public ResponseEntity<?> lista() {
		List<Cidade> lista = repo.findAll();
		
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{cidadeId}")
	public ResponseEntity<?> buscar(@PathVariable Long cidadeId) {
		Optional<Cidade> obj = repo.findById(cidadeId);
		
		if(obj.isPresent()) {
			return ResponseEntity.ok(obj.get());
			
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			Cidade obj = service.salvar(cidade);
			return ResponseEntity.created(null).body(obj);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<Cidade> atualizar(@RequestBody Cidade cidade,@PathVariable Long cidadeId) {
			Optional<Cidade> obj = repo.findById(cidadeId);
			
			if(obj.isPresent()) {
				BeanUtils.copyProperties(cidade, obj.get(), "id");
			 Cidade cidadeSalva = service.salvar(cidade);
				return ResponseEntity.ok(cidadeSalva);
			}
		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> deletae(@PathVariable Long cidadeId) {
		try {
		 service.deletar(cidadeId);
			return ResponseEntity.noContent().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
