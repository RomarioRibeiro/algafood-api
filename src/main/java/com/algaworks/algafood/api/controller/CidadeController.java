package com.algaworks.algafood.api.controller;

import java.util.List;

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

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository repo;

	@Autowired
	private CidadeService service;

	@GetMapping
	public ResponseEntity<?> lista() {
		List<Cidade> lista = repo.findAll();

		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
		return service.buscarOuFalha(cidadeId);
	}

	@PostMapping
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return service.salvar(cidade);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@RequestBody Cidade cidade, @PathVariable Long cidadeId) {
		try {
		Cidade cidadeAtual = service.buscarOuFalha(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return service.salvar(cidadeAtual);
		}catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	public void deletae(@PathVariable Long cidadeId) {
			service.deletar(cidadeId);
	}
}
