package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class cidadeService {

	@Autowired
	private CidadeRepository repo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	
	public Cidade salvar(Cidade cidade) {
			if(!estadoRepo.existsById(cidade.getEstado().getId())) {
				throw new EntidadeNaoEncontradaException(
						String.format("Estado com Id %d não encontrado", cidade.getEstado().getId()));
			}
			
		return repo.save(cidade);
	}


	public void deletar(Long cidadeId) {
		try {
			if(!repo.existsById(cidadeId)) {
				throw new EntidadeNaoEncontradaException(
						String.format("Cidade com Id %d, não encontrado!", cidadeId));
			}
			repo.deleteById(cidadeId);
		} catch (EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade com Id %d, não pode ser excluida esta em uso!", cidadeId));
		} 
	}


	
	
	
}
