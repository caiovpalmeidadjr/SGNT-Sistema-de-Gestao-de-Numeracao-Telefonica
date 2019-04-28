package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
import br.com.sgnt.model.TipoNumero;

public interface INumeroSTFCService {

	public void salvar(NumeroSTFC vo);
	
	public void salvarLista(List<NumeroSTFC> lista);
	
	public void atualizar(NumeroSTFC vo);

	public void excluir(NumeroSTFC vo);

	public List<NumeroSTFC> listNumerosSTFC(String query);

	public List<NumeroSTFC> listNumerosSTFC();

	public List<NumeroSTFC> findReserva(Reserva reserva);

	public List<NumeroSTFC> findReserva(Reserva reserva, TipoNumero tipoNumero);

	public List<Reserva> findReservaVencendo(Status status);

	public NumeroSTFC findNumberSTFC(AreaLocal area, Integer prefixo, Integer mcdu);
	
	public List<NumeroSTFC> findNumeroStatus(Status status);
	
	public List<NumeroSTFC> listNumeroTipo(TipoNumero tipo);
	
	public List<NumeroSTFC> listNumeroDisponivel(TipoNumero tipo, Status status);
	
	public List<NumeroSTFC> getListaAreaLocal(Integer prefixo);
	
	public List<Integer> listPrefixo(AreaLocal areaLocal, TipoNumero tipo);
	
	public List<NumeroSTFC> listNumeroAreaLocal(AreaLocal areaLocal);
}
