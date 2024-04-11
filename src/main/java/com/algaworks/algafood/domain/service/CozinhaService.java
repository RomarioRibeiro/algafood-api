package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CozinhaRepository repo;

	public Cozinha salvar(Cozinha cozinha) {
		return repo.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			if (!repo.existsById(cozinhaId)) {
				throw new CozinhaNaoEncontradaException(cozinhaId);
			}
			repo.deleteById(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO, cozinhaId));
		}
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return repo.findById(cozinhaId).orElseThrow(() ->  new CozinhaNaoEncontradaException(cozinhaId));
	}

}
