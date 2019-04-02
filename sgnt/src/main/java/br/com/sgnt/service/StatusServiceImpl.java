package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.Status;
import br.com.sgnt.repository.StatusRepository;

@Service
public class StatusServiceImpl implements IStatusService {
	
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public void salvar(Status vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(Status vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void excluir(Status vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Status> listStatus(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Status> listStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status findOne(Integer id) {
		return statusRepository.findOne(id);
	}

}
