package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
import br.com.sgnt.repository.NumeroCNGRepository;

@Service
public class NumeroCNGServiceImpl implements INumeroCNGService {
	
	@Autowired
	private NumeroCNGRepository numeroCNGRepository;
	
	@Override
	public void salvar(NumeroCNG vo) {
		numeroCNGRepository.save(vo);
	}

	@Override
	public void atualizar(NumeroCNG vo) {
		numeroCNGRepository.save(vo);
	}

	@Override
	public void excluir(NumeroCNG vo) {
		numeroCNGRepository.delete(vo);
	}

	@Override
	public List<NumeroCNG> listNumeroCNG(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NumeroCNG> listNumeroCNG() {
		return numeroCNGRepository.findAll();
	}

	@Override
	public NumeroCNG findNumero(Integer prefixoNumeroCNG, Integer serieNumeroCNG, Integer mcduNumeroCNG) {
		return numeroCNGRepository.findNumero(prefixoNumeroCNG,serieNumeroCNG, mcduNumeroCNG);
	}

	@Override
	public List<NumeroCNG> findDisponivel(Status status) {
		return numeroCNGRepository.findDisponivel(status);
	}

	@Override
	public List<NumeroCNG> findReservado(Status status) {
		return numeroCNGRepository.findReservados(status);
	}

	@Override
	public List<NumeroCNG> findReserva(Reserva reserva) {
		return numeroCNGRepository.findReserva(reserva);
	}
	
}
