package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository repo;

	public Cozinha salvar(Cozinha cozinha) {
		return repo.save(cozinha);
	}

	public void delete(Long cozinhaId) {
		try {
			if(!repo.existsById(cozinhaId)) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Cozinha com código %d", cozinhaId));
			}
			repo.deleteById(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId));
		}
	}

}
