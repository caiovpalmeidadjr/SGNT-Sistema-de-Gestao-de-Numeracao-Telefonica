package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.repository.NumeroCNGRepository;

@Service
public class NumeroSTFCServiceImpl implements INumeroSTFCService {
	
	@Autowired
	private NumeroCNGRepository numeroCNGRepository;

	@Override
	public void salvar(NumeroSTFC vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(NumeroSTFC vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(NumeroSTFC vo) {
		// TODO Auto-generated method stub

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

}
