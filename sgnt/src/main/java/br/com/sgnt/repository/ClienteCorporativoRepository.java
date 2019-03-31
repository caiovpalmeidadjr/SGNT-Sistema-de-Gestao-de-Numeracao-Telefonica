package br.com.sgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.ClienteCorporativo;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface ClienteCorporativoRepository extends JpaRepository<ClienteCorporativo, Integer> {
	
	@Query("select c from ClienteCorporativo c where c.cnpj=:cnpj")
	public Cliente buscarPorCNPJ(@Param("cnpj")String cnpj);
	
}
