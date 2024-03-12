package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public Estado salvar(Estado estado) {

		return repo.save(estado);
	}

	public void deleta(Long estadoId) {
		try {
			
			if(!repo.existsById(estadoId)) {
				throw new EntidadeNaoEncontradaException(String.format("Estado com Id não encontrado %d", estadoId));
			}
			repo.deleteById(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Não pode ser deletada por que esta em uso %d", estadoId));

		}

	}

}
