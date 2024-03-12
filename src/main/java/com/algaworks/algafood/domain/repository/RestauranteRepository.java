package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomRepository<Restaurante, Long>, RestauranteRepositoryQueries,
JpaSpecificationExecutor<Restaurante>{
	
//	@Query(value = "SELECT * FROM restaurante", nativeQuery = true)
//	List<Restaurante> lista();
//
//	@Query(value = "SELECT * FROM restaurante where id = ?", nativeQuery = true)
//	Restaurante buscar(Long restauranteId);

	
	
	
}
