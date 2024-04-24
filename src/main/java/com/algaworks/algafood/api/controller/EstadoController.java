package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Estado buscar(@PathVariable Long estadoId) {
		return service.buscarOuFalha(estadoId);
	}

	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody @Valid Estado estado) {
		Estado obj = repo.save(estado);
		return ResponseEntity.created(null).body(obj);
	}
	
	@PutMapping("/{estadoId}")
	public Estado atualizar(@RequestBody @Valid Estado estado,@PathVariable Long estadoId) {
		Estado estadoAtual = service.buscarOuFalha(estadoId);
				BeanUtils.copyProperties(estado, estadoAtual, "id", "dataCadastro");
				return service.salvar(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	public void  deletar(@PathVariable Long estadoId) {
			service.deleta(estadoId);
	}

}
