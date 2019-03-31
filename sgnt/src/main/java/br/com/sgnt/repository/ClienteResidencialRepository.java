package br.com.sgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.ClienteResidencial;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface ClienteResidencialRepository extends JpaRepository<ClienteResidencial, Integer> {
	
	@Query("select c from ClienteResidencial c where c.cpf=:cpf")
	public Cliente buscarPorCPF(@Param("cpf")String cpf);
	
}
