package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.TipoNumero;
import br.com.sgnt.repository.TipoNumeroRepository;

@Service
public class TipoNumeroServiceImpl implements ITipoNumeroService {
	
	@Autowired
	private TipoNumeroRepository tipoNumeroRepository;
	
	@Override
	public void salvar(TipoNumero vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(TipoNumero vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(TipoNumero vo) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public TipoNumero findOne(Integer id) {
		return tipoNumeroRepository.findOne(id);
	}

	@Override
	public List<TipoNumero> listTiposNumeros(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoNumero> listTiposNumeros() {
		// TODO Auto-generated method stub
		return null;
	}

}
