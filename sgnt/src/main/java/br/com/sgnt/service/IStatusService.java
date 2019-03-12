package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Status;

public interface IStatusService {
	
	public void salvar(Status vo) ;
	
	public void atualizar(Status vo) ;
	
	public void excluir(Status vo);
	
	public List<Status> listStatus(String query);
	
	public List<Status> listStatus();
}
