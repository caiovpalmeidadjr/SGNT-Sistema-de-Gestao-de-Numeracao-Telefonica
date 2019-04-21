package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.ClienteCorporativo;
import br.com.sgnt.model.ClienteResidencial;

public interface IClienteService {

	public void salvar(Cliente vo);

	public void atualizar(Cliente vo);

	public void excluir(Cliente vo);

	public void salvarClienteCorp(ClienteCorporativo vo);

	public void atualizarClienteCorp(ClienteCorporativo vo);

	public void excluirClienteCorp(ClienteCorporativo vo);

	public void salvarClienteResidencial(ClienteResidencial vo);

	public void atualizarClienteResidencial(ClienteResidencial vo);

	public void excluirClienteResidencial(ClienteResidencial vo);

	public List<Cliente> listClientes(String query);

	public List<Cliente> listClientes();

	public List<ClienteCorporativo> listClientesCorporativos();

	public List<ClienteResidencial> listClientesResidencial();
	
	public Cliente buscarPorCNPJ(String cnpj);
	
	public Cliente buscarPorCPF(String cpf);

}
