package com.algaworks.algafood.domain.infrastructure.repositoryImpl;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.algafood.domain.repository.CustomRepository;

import jakarta.persistence.EntityManager;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {
		
	
	
	
	private EntityManager manager;
	
	public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;

		
	}

	@Override
	public Optional<T> buscarPrimeirp() {
		var jpql = "From " + getDomainClass().getName();
		
		T entity = manager.createQuery(jpql, getDomainClass())
				.setMaxResults(1)
				.getSingleResult();		
		
		
		return Optional.ofNullable(entity);
	}

}
