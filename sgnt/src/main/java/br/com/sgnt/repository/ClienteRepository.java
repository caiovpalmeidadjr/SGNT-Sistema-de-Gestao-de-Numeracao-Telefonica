package br.com.sgnt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.Cliente;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Query("select c from Cliente where c.nomeClie=:nomeClie")
	public Cliente buscarPorNome(@Param("nomeClie")String nomeClie);
	
	@Query("select cli from Cliente cli")
	public List<Cliente> buscarTodos();

}
