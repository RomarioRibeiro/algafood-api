package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String NÃO_PODE_SER_DELETADA_POR_QUE_ESTA_EM_USO_D = "Não pode ser deletada por que esta em uso %d";
	@Autowired
	private EstadoRepository repo;

	public Estado salvar(Estado estado) {

		return repo.save(estado);
	}

	public void deleta(Long estadoId) {
		try {

			if (!repo.existsById(estadoId)) {
				throw new EstadoNaoEncontradaException(estadoId);
			}
			repo.deleteById(estadoId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(NÃO_PODE_SER_DELETADA_POR_QUE_ESTA_EM_USO_D, estadoId));
		}
	}

	public Estado buscarOuFalha(Long estadoId) {
		return repo.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradaException(estadoId));
	}

}
