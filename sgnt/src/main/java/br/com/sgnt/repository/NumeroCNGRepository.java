package br.com.sgnt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sgnt.model.NumeroCNG;

public interface NumeroCNGRepository extends JpaRepository<NumeroCNG, Integer> {
	
	@Query("select n from NumeroCNG n where n.serieNumeroCNG=:serieNumeroCNG and n.mcduNumeroCNG=:mcduNumeroCNG")
	public NumeroCNG findNumero(@Param("serieNumeroCNG")Integer serieNumeroCNG, @Param("mcduNumeroCNG")Integer mcduNumeroCNG);

	
}
