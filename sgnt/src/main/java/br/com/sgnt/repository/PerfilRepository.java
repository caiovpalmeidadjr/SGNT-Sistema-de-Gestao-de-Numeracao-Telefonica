package br.com.sgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.Perfil;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
	
	@Query("select p from Perfil p where p.nome=:nome")
	public Perfil buscaPorNome(@Param("nome")String nome);
	
}
