package com.algaworks.algafood.domain.infrastructure.repositoryImpl;

import static com.algaworks.algafood.domain.infrastructure.repositoryImpl.spec.RestauranteSpec.comFreteGratis;
import static com.algaworks.algafood.domain.infrastructure.repositoryImpl.spec.RestauranteSpec.nomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository repo;
	
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

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		
		
		return repo.findAll(comFreteGratis().and(nomeSemelhante(nome)));
	}
	
}
