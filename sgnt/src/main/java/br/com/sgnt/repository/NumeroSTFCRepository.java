package br.com.sgnt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
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
	
	@Query("select n from NumeroSTFC n where n.tipoNumero=:tipoNumero")
	public List<NumeroSTFC> listNumero(@Param("tipoNumero")TipoNumero tipoNumero);
	
	@Query("select n from NumeroSTFC n where n.tipoNumero=:tipoNumero and n.status=:status")
	public List<NumeroSTFC> listNumeroDisponivel(@Param("tipoNumero")TipoNumero tipoNumero, @Param("status")Status status);
	
	@Query("select distinct n.prefixoNumeroSTFC from NumeroSTFC n where n.areaLocal=:areaLocal and n.tipoNumero=:tipoNumero")
	public List<Integer> listPrefixo(@Param("areaLocal")AreaLocal areaLocal, @Param("tipoNumero")TipoNumero tipoNumero);
	
	@Query("select n from NumeroSTFC n where n.prefixoNumeroSTFC=:prefixoNumeroSTFC and n.mcduNumeroSTFC=:mcduNumeroSTFC and n.areaLocal=:areaLocal")
	public NumeroSTFC numeroAreaLocal(@Param("prefixoNumeroSTFC")Integer prefixoNumeroSTFC, @Param("mcduNumeroSTFC")Integer mcduNumeroSTFC, @Param("areaLocal")AreaLocal areaLocal);
	
	@Query("select n from NumeroSTFC n where n.reserva=:reserva")
	public List<NumeroSTFC> findRerserva(@Param("reserva")Reserva reserva);
	
	@Query("select n from NumeroSTFC n where n.reserva=:reserva and n.tipoNumero=:tipoNumero")
	public List<NumeroSTFC> findReservaTipo(@Param("reserva")Reserva reserva, @Param("tipoNumero")TipoNumero tipoNumero);
	
	@Query("select distinct n.reserva from NumeroSTFC n where n.status=:status")
	public List<Reserva> findReservasVencendo(@Param("status")Status status);
	
	@Query("select n from NumeroSTFC n where n.cn=:cn and n.prefixoNumeroSTFC=:prefixo and n.mcduNumeroSTFC=:mcdu and n.status=:status")
	public NumeroSTFC findNumberSTFC(String cn, String prefixo, String mcdu, String status);
	
	@Query("select n from NumeroSTFC n where n.status=:status")
	public List<NumeroSTFC> findNumeroStatus(@Param("status")Status status);
	
}
