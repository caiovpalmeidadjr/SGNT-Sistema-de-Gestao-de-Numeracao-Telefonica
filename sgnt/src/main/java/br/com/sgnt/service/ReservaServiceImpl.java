package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.Reserva;
import br.com.sgnt.repository.ReservaRepository;

@Service
public class ReservaServiceImpl implements IReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;

	@Override
	public Reserva salvar(Reserva vo) {
		return reservaRepository.save(vo);

	}

	@Override
	public void atualizar(Reserva vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Reserva vo) {
		reservaRepository.delete(vo);
	}

	@Override
	public List<Reserva> listReservas(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reserva> listReservas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(Integer id) {
		reservaRepository.delete(id);
		
	}

}
