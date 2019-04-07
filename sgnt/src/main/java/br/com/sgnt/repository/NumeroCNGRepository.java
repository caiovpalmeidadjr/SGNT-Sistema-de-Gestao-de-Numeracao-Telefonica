package br.com.sgnt.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;

public interface NumeroCNGRepository extends JpaRepository<NumeroCNG, Integer> {
	
	@Query("select n from NumeroCNG n where n.prefixoNumeroCNG=:prefixoNumeroCNG and n.serieNumeroCNG=:serieNumeroCNG and n.mcduNumeroCNG=:mcduNumeroCNG")
	public NumeroCNG findNumero(@Param("prefixoNumeroCNG")Integer prefixoNumeroCNG, @Param("serieNumeroCNG")Integer serieNumeroCNG, @Param("mcduNumeroCNG")Integer mcduNumeroCNG);

	@Query("select n from NumeroCNG n where n.status=:status")
	public List<NumeroCNG> findDisponivel(@Param("status")Status status);
	
	@Query("select n from NumeroCNG n where n.status=:status and n.reserva is not null")
	public List<NumeroCNG> findReservados(@Param("status")Status status);
	
	@Query("select n from NumeroCNG n where n.reserva=:reserva")
	public List<NumeroCNG> findReserva(@Param("reserva")Reserva reserva);
	
}
