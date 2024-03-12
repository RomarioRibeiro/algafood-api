package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
//	
//	@Query(value = "select * from estado", nativeQuery = true)
//	List<Estado> lista();
//
//	@Query(value = "select * from estado where id = ?", nativeQuery = true)
//	Estado buscarId(Long estadoId);

}
