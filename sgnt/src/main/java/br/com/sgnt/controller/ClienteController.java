package br.com.sgnt.controller;

import java.util.List;

import javax.annotation.PostConstruct;
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
	
	private List<Cliente> listClientes;
	
	private boolean alteracao;
	
	//usada nos metodos com relação de injeção de dependencia, apos a injeção de dependencia do spring, eu acesso esse metodo
	@PostConstruct
	private void init() {
		listClientes = repository.listCliente();
		// tambem funciona listClientes = repository.findAll();
	}
	
	
	public void salvar() {
		//salvando no banco
		repository.save(cliente);
		
		if(!alteracao) {
			//adicionando na lista que vai atualizar a tabela 
			listClientes.add(cliente);
		}
		
		//após salvar coloco o meu alteracao para false
		setAlteracao(false);
		
		//instanciando pois o proximo a ser adicionada é um novo cliente
		cliente = new Cliente();
	}
	
	//Java Web Frameworks com Spring Boot - Aula 04 parte 02 19:25 seg
	
	public void excluir(Cliente cliente) {
		//removendo do banco
		repository.delete(cliente);
		
		//removendo da lista
		listClientes.remove(cliente);
		
	}
	
	public void editar(Cliente cliente) {
		setCliente(cliente);
		setAlteracao(true);
	}
	
	public void cancelar() {
		cliente = new Cliente();
		setAlteracao(false);
	}

	public Cliente getCliente() {
		//o botão de salvar que vai atualizar, ele sabe se o objeto tem id ou não caso tenha ele realiza o update
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}


	public boolean isAlteracao() {
		return alteracao;
	}


	public void setAlteracao(boolean alteracao) {
		this.alteracao = alteracao;
	}
	
	
	
}
