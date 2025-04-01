package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	List<Cozinha> consultarPorNome(String nome);
	Cozinha buscarId(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long cozinhaId);
	
}
