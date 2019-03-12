package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Cliente;

public interface IClienteService {
	
	public void salvar(Cliente vo) ;
	
	public void atualizar(Cliente vo) ;
	
	public void excluir(Cliente vo);
	
	public List<Cliente> listClientes(String query);
	
	public List<Cliente> listClientes();

}
