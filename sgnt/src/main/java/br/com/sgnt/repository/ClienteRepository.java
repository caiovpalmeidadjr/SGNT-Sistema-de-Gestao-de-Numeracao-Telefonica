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
	
	@Query("select c from Cliente c where c.email=:email")
	public Cliente buscarPorEmail(@Param("email")String email);
	
	@Query("select c from Cliente c")
	public List<Cliente> listCliente();
}
