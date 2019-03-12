package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Reserva;

public interface IReservaService {
	
	public void salvar(Reserva vo) ;
	
	public void atualizar(Reserva vo) ;
	
	public void excluir(Reserva vo);
	
	public List<Reserva> listReservas(String query);
	
	public List<Reserva> listReservas();
}
