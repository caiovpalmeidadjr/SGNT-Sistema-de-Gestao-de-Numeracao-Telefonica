package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
import br.com.sgnt.model.TipoNumero;
import br.com.sgnt.repository.NumeroSTFCRepository;

@Service
public class NumeroSTFCServiceImpl implements INumeroSTFCService {
	
	@Autowired
	private NumeroSTFCRepository numeroSTFCrepository;

	@Override
	public void salvar(NumeroSTFC vo) {
		numeroSTFCrepository.save(vo);

	}

	@Override
	public void atualizar(NumeroSTFC vo) {
		numeroSTFCrepository.save(vo);

	}

	@Override
	public void excluir(NumeroSTFC vo) {
		numeroSTFCrepository.delete(vo);

	}

	@Override
	public List<NumeroSTFC> listNumerosSTFC(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NumeroSTFC> listNumerosSTFC() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NumeroSTFC> findReserva(Reserva reserva) {
		return numeroSTFCrepository.findRerserva(reserva);
	}

	@Override
	public List<NumeroSTFC> findReserva(Reserva reserva, TipoNumero tipoNumero) {
		return numeroSTFCrepository.findReservaTipo(reserva, tipoNumero);
	}

	@Override
	public List<Reserva> findReservaVencendo(Status status) {
		return numeroSTFCrepository.findReservasVencendo(status);
	}

	@Override
	public NumeroSTFC findNumberSTFC(String cn, String prefixo, String mcdu, String status) {
		// TODO Auto-generated method stub
		return numeroSTFCrepository.findNumberSTFC();
	}
	

	@Override
	public List<NumeroSTFC> findNumeroStatus(Status status) {
		return numeroSTFCrepository.findNumeroStatus(status);
	}
	
}
