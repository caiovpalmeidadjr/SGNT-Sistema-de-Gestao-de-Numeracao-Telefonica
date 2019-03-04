package br.com.sgnt.controller;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.repository.ClienteRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class ClienteController {
	
	//pegando um objeto do clienteRepository para salvar o objeto da tela no banco
	@Autowired
	private ClienteRepository repository;

	private Cliente cliente = new Cliente();
	
	public void salvar() {
		repository.save(cliente);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
