package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String RESTAURANTE_NÃO_ENCONTRADO_COM_CÓDIGO_D = "Restaurante não encontrado com código %d";

	@Autowired
	private RestauranteRepository repo;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha  cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		
		return repo.save(restaurante);
	}
	
	
	public Restaurante buscarOuFalha(Long restauranteId) {
		return repo.findById(restauranteId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(RESTAURANTE_NÃO_ENCONTRADO_COM_CÓDIGO_D, restauranteId)));
	}
	
}
