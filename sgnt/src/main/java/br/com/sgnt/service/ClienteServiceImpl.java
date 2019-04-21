package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.ClienteCorporativo;
import br.com.sgnt.model.ClienteResidencial;
import br.com.sgnt.repository.ClienteCorporativoRepository;
import br.com.sgnt.repository.ClienteRepository;
import br.com.sgnt.repository.ClienteResidencialRepository;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteCorporativoRepository clienteCorporativoRepository;

	@Autowired
	private ClienteResidencialRepository clienteResidencialRepository;

	@Override
	public void salvar(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	public void atualizar(Cliente cliente) {
		clienteRepository.save(cliente);

	}

	@Override
	public void excluir(Cliente cliente) {
		clienteRepository.delete(cliente);

	}

	@Override
	public List<Cliente> listClientes(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> listClientes() {
		// TODO Auto-generated method stub
		return clienteRepository.listCliente();
	}

	@Override
	public List<ClienteCorporativo> listClientesCorporativos() {
		// TODO Auto-generated method stub
		return clienteCorporativoRepository.findAll();
	}

	@Override
	public List<ClienteResidencial> listClientesResidencial() {
		// TODO Auto-generated method stub
		return clienteResidencialRepository.findAll();
	}

	@Override
	public void salvarClienteCorp(ClienteCorporativo vo) {
		clienteCorporativoRepository.save(vo);

	}

	@Override
	public void atualizarClienteCorp(ClienteCorporativo vo) {
		clienteCorporativoRepository.save(vo);

	}

	@Override
	public void excluirClienteCorp(ClienteCorporativo vo) {
		clienteCorporativoRepository.delete(vo);

	}

	@Override
	public void salvarClienteResidencial(ClienteResidencial vo) {
		clienteResidencialRepository.save(vo);

	}

	@Override
	public void atualizarClienteResidencial(ClienteResidencial vo) {
		clienteResidencialRepository.save(vo);

	}

	@Override
	public void excluirClienteResidencial(ClienteResidencial vo) {
		clienteResidencialRepository.delete(vo);

	}

	@Override
	public Cliente buscarPorCNPJ(String cnpj) {
		return clienteCorporativoRepository.buscarPorCNPJ(cnpj);
	}

	@Override
	public Cliente buscarPorCPF(String cpf) {
		return clienteResidencialRepository.buscarPorCPF(cpf);
	}
	
	

}
