package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	
//	@Query(value = "select * from cozinha", nativeQuery = true)
//	List<Cozinha> lista();
//	
//	
//	@Query(value = "select * from cozinha where id = ?", nativeQuery = true)
//	Cozinha buscar(Long cozinhaId);

	
	
}


