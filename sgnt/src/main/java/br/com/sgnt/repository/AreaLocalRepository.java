package br.com.sgnt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.AreaLocal;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface AreaLocalRepository extends JpaRepository<AreaLocal, Integer> {
	
	@Query("select a from AreaLocal a")
	public List<AreaLocal> listAreaLocal();
	
	@Query("select a from AreaLocal a where a.nomeAreaLocal=:nomeAreaLocal")
	public AreaLocal areaLocalNome(@Param("nomeAreaLocal")String nomeAreaLocal);
	
	@Query("select distinct a.cnAreaLocal from AreaLocal a")
	public List<AreaLocal> listDistinctCN();
	
	@Query("select a.nomeAreaLocal from AreaLocal a where a.cnAreaLocal=:cnAreaLocal and a.numAtendimentoAreaLocal=true order by a.nomeAreaLocal")
	public List<AreaLocal> listAreaLocalCN(@Param("cnAreaLocal")Integer cnAreaLocal);
	
	@Query("select a from AreaLocal a where a.siglaAreaLocal=:siglaAreaLocal")
	public AreaLocal findIdporSigla(@Param("siglaAreaLocal")String siglaAreaLocal);
	
}
