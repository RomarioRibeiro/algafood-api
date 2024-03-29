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

	private static final String COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
	private static final String NÃO_EXISTE_UM_CADASTRO_DE_COZINHA_COM_CÓDIGO_D = "Não existe um cadastro de cozinha com código %d";
	@Autowired
	private CozinhaRepository repo;

	public Cozinha salvar(Cozinha cozinha) {
		return repo.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
			if (!repo.existsById(cozinhaId)) {
				throw new EntidadeNaoEncontradaException(
						String.format(NÃO_EXISTE_UM_CADASTRO_DE_COZINHA_COM_CÓDIGO_D, cozinhaId));
			}
			repo.deleteById(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(COZINHA_DE_CÓDIGO_D_NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO, cozinhaId));
		}
	}
	
	
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return repo.findById(cozinhaId).orElseThrow(() ->  new EntidadeNaoEncontradaException(
				String.format(NÃO_EXISTE_UM_CADASTRO_DE_COZINHA_COM_CÓDIGO_D, cozinhaId)));
	}

}
