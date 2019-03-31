package br.com.sgnt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.TipoNumero;

//metodos padroes de crud estão no JpaRepository extendido, utilizando a class cliente
@Repository // a partir dessa anotação essa class vira um bean do spring, objeto construido pelo spring 
public interface NumeroSTFCRepository extends JpaRepository<NumeroSTFC, Integer> {
	
	@Query("select n from NumeroSTFC n where n.prefixoNumeroSTFC=:prefixoNumeroSTFC")
	public NumeroSTFC getAreaLocal(@Param("prefixoNumeroSTFC")Integer prefixoNumeroSTFC);
	
	@Query("select n from NumeroSTFC n where n.prefixoNumeroSTFC=:prefixoNumeroSTFC")
	public List<NumeroSTFC> getListaAreaLocal(@Param("prefixoNumeroSTFC")Integer prefixoNumeroSTFC);
	
	@Query("select n from NumeroSTFC n")
	public List<NumeroSTFC> listNumeroCorporativo();
	
	@Query("select distinct n.prefixoNumeroSTFC from NumeroSTFC n where n.areaLocal=:areaLocal and n.tipoNumero=:tipoNumero")
	public List<Integer> listPrefixo(@Param("areaLocal")AreaLocal areaLocal, @Param("tipoNumero")TipoNumero tipoNumero);
	
	@Query("select n from NumeroSTFC n where n.prefixoNumeroSTFC=:prefixoNumeroSTFC and n.mcduNumeroSTFC=:mcduNumeroSTFC and n.areaLocal=:areaLocal")
	public NumeroSTFC numeroAreaLocal(@Param("prefixoNumeroSTFC")Integer prefixoNumeroSTFC, @Param("mcduNumeroSTFC")Integer mcduNumeroSTFC, @Param("areaLocal")AreaLocal areaLocal);

	
}
