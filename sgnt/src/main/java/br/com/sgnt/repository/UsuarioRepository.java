package br.com.sgnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.Usuario;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("select u from Usuario u where u.userName=:user")
	public Usuario buscaPorUsername(@Param("user")String user);
	
	@Query("SELECT u from Usuario u where u.userName=:userName and u.senha=:senha")
	public Usuario getUsuario(@Param("userName")String userName, @Param("senha")String senha);
	
	@Query("select u from Usuario u")
	public Usuario testuser();
	
}
