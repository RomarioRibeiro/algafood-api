package com.algaworks.algafood.domain.infrastructure.repositoryImpl.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteSpec {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, builder) -> 
		builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
	
	public static Specification<Restaurante> nomeSemelhante(String nome) {
		return (root, query, builder) -> 
		builder.like(root.get("nome"), "%" + nome + "%");
	}
	
	
}
