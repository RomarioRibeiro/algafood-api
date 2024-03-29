package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String CIDADE_COM_ID_D_NÃO_PODE_SER_EXCLUIDA_ESTA_EM_USO = "Cidade com Id %d, não pode ser excluida esta em uso!";

	@Autowired
	private CidadeRepository repo;

	@Autowired
	private EstadoService estadoService;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.buscarOuFalha(estadoId);
		
		cidade.setEstado(estado);

		return repo.save(cidade);
	}

	public void deletar(Long cidadeId) {
		try {
			if (!repo.existsById(cidadeId)) {
				throw new CidadeNaoEncontradaException(cidadeId);
			}
			repo.deleteById(cidadeId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(CIDADE_COM_ID_D_NÃO_PODE_SER_EXCLUIDA_ESTA_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalha(Long cidadeId) {
		return repo.findById(cidadeId).orElseThrow(
				() -> new CidadeNaoEncontradaException(cidadeId));
	}

}
