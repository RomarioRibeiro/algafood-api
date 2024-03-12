package com.algaworks.algafood.domain.infrastructure.repositoryImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criateria = builder.createQuery(Restaurante.class);
		 Root<Restaurante> root =  criateria.from(Restaurante.class);
		 
		 var predicates = new ArrayList<Predicate>();
		 
		 if(StringUtils.hasText(nome)) {
			 predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
			 
		 }
		 
		 if(taxaInicial != null) {
			 
			 predicates.add( builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		 }
		
		 if(taxaFinal != null) {
			 
	        predicates.add(	builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		 }
		
		criateria.where(predicates.toArray(new Predicate [0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criateria);
		
		return query.getResultList();
	}
	
}
