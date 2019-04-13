package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
import br.com.sgnt.model.TipoNumero;

public interface INumeroSTFCService {
	
	public void salvar(NumeroSTFC vo) ;
	
	public void atualizar(NumeroSTFC vo) ;
	
	public void excluir(NumeroSTFC vo);
	
	public List<NumeroSTFC> listNumerosSTFC(String query);
	
	public List<NumeroSTFC> listNumerosSTFC();
	
	public List<NumeroSTFC> findReserva(Reserva reserva);
	
	public List<NumeroSTFC> findReserva(Reserva reserva, TipoNumero tipoNumero);
	
	public List<Reserva> findReservaVencendo(Status status);
	
}
