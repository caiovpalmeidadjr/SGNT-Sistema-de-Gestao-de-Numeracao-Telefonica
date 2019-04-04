package br.com.sgnt.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.ClienteCorporativo;
import br.com.sgnt.model.ClienteResidencial;
import br.com.sgnt.repository.ClienteCorporativoRepository;
import br.com.sgnt.repository.ClienteRepository;
import br.com.sgnt.repository.ClienteResidencialRepository;
import br.com.sgnt.service.IClienteService;
import br.com.viacep.ClienteCEP;
import br.com.viacep.EnderecoCEP;


//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class ClienteController {
	
	//pegando um objeto do clienteRepository para salvar o objeto da tela no banco
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private ClienteCorporativoRepository clienteCorporativoRepository;
	
	@Autowired
	private ClienteResidencialRepository clienteResidencialRepository;
	
	@Autowired
	private IClienteService clienteService;
	
	private Cliente cliente = new Cliente();
	private ClienteCorporativo clienteCorporativo = new ClienteCorporativo();
	private ClienteResidencial clienteResidencial = new ClienteResidencial();
	private Cliente selectedCliente = new Cliente();
	private EnderecoCEP e = new EnderecoCEP();
	private ClienteCEP ws = new ClienteCEP();
	private ClienteCorporativo clienteCorporativoSelecionado;
	
	private List<Cliente> listClientes;
	private List<ClienteCorporativo> listClientesCorporativo;
	private List<ClienteResidencial> listClientesResidencial;
	
	private boolean alteracao;
	
	//usada nos metodos com relação de injeção de dependencia, apos a injeção de dependencia do spring, eu acesso esse metodo
	@PostConstruct
	private void init() {
		listClientes = clienteService.listClientes();
		listClientesCorporativo = clienteService.listClientesCorporativos();
		listClientesResidencial = clienteService.listClientesResidencial();
		// tambem funciona listClientes = repository.findAll();
	}
	
	
	public void salvar() {
		//salvando no banco
		clienteService.salvar(cliente);
		
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
		if(cliente != null) {
			clienteService.excluir(cliente);
			
			//removendo da lista
			listClientes.remove(cliente);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cliente não selecionado !", null));
		}
		
		
	}
	
	public void excluirCorporativo(ClienteCorporativo clienteCorporativo) {
		//removendo do banco
		if(clienteCorporativo != null) {
			clienteService.excluir(clienteCorporativo);
			
			//removendo da lista
			listClientes.remove(clienteCorporativo);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cliente Corporativo não selecionado !", null));
		}
		
	}
	
	public void excluirSelecionadoCorporativo() {
		try {
			clienteService.excluir(selectedCliente);
			listClientes.remove(selectedCliente);
			listClientesCorporativo.remove(selectedCliente);
			FacesMessage msg = new FacesMessage("Cliente removido da base", selectedCliente.getNome());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Cliente possui reservas em seu nome", selectedCliente.getNome());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	public void excluirSelecionadoResidencial() {
		try {
			clienteService.excluir(selectedCliente);
			listClientes.remove(selectedCliente);
			listClientesResidencial.remove(selectedCliente);
			FacesMessage msg = new FacesMessage("Cliente removido da base", selectedCliente.getNome());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("Cliente possui reservas em seu nome", selectedCliente.getNome());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void editar(ClienteCorporativo cliente) {
		setCliente(cliente);
		setClienteCorporativo(cliente);
		setClienteResidencial(clienteResidencial);
		setAlteracao(true);
	}
	
	public void cancelar() {
		cliente = new Cliente();
		clienteCorporativo = new ClienteCorporativo();
		setAlteracao(false);
	}
	
	public void salvarCorporativo() {
		
		Cliente c = clienteCorporativoRepository.buscarPorCNPJ(clienteCorporativo.getCnpj());
		
		if(c == null) {
			clienteCorporativo.setLogradouro(e.getLogradouro());
			clienteCorporativo.setBairro(e.getBairro());
			clienteCorporativo.setMunicipio(e.getLocalidade());
			repository.save(clienteCorporativo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
			
			if(!alteracao) {
				listClientesCorporativo.add(clienteCorporativo);
			}
			setAlteracao(false);
			
			cliente = new Cliente();
			clienteCorporativo = new ClienteCorporativo();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Cliente já cadastrado", "Cadastro não efetuado!"));
		}
		
	}
	
	public void salvarResidencial() {
		
		Cliente c = clienteResidencialRepository.buscarPorCPF(clienteResidencial.getCpf());
		
		if(c == null) {
			clienteResidencial.setBairro(e.getBairro());
			clienteResidencial.setLogradouro(e.getLogradouro());
			clienteResidencial.setMunicipio(e.getLocalidade());
			repository.save(clienteResidencial);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
			
			if(!alteracao) {
				listClientesResidencial.add(clienteResidencial);
			}
			setAlteracao(false);
			
			cliente = new Cliente();
			clienteResidencial = new ClienteResidencial();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Cliente já cadastrado", "Cadastro não efetuado!"));
		}
		
	}
	
	
	public void onRowEdit(RowEditEvent event) {
        Cliente c = ((Cliente) event.getObject());
		FacesMessage msg = new FacesMessage("Cliente alterado!", c.getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        repository.save(c);
    }
	
	public void onRowCancel(RowEditEvent event) {
		Cliente c = ((Cliente) event.getObject());
		FacesMessage msg = new FacesMessage("Alteração cancelada", c.getNome());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void getEndereco(String cep) {
		cep = cep.replace(".", "").replace("-", "");
		e = ws.getEnderecoPorCep(cep);
		
		if(e == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"CEP inválido", "Favor informar um CEP válido"));
		}
		
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


	public ClienteCorporativo getClienteCorporativo() {
		return clienteCorporativo;
	}


	public void setClienteCorporativo(ClienteCorporativo clienteCorporativo) {
		this.clienteCorporativo = clienteCorporativo;
	}


	public ClienteResidencial getClienteResidencial() {
		return clienteResidencial;
	}


	public void setClienteResidencial(ClienteResidencial clienteResidencial) {
		this.clienteResidencial = clienteResidencial;
	}


	public List<ClienteCorporativo> getListClientesCorporativo() {
		return listClientesCorporativo;
	}


	public void setListClientesCorporativo(List<ClienteCorporativo> listClientesCorporativo) {
		this.listClientesCorporativo = listClientesCorporativo;
	}


	public List<ClienteResidencial> getListClientesResidencial() {
		return listClientesResidencial;
	}


	public void setListClientesResidencial(List<ClienteResidencial> listClientesResidencial) {
		this.listClientesResidencial = listClientesResidencial;
	}


	public Cliente getSelectedCliente() {
		return selectedCliente;
	}


	public void setSelectedCliente(Cliente selectedCliente) {
		this.selectedCliente = selectedCliente;
	}


	public EnderecoCEP getE() {
		return e;
	}


	public void setE(EnderecoCEP e) {
		this.e = e;
	}


	public ClienteCEP getWs() {
		return ws;
	}


	public void setWs(ClienteCEP ws) {
		this.ws = ws;
	}


	public ClienteCorporativo getClienteCorporativoSelecionado() {
		return clienteCorporativoSelecionado;
	}


	public void setClienteCorporativoSelecionado(ClienteCorporativo clienteCorporativoSelecionado) {
		this.clienteCorporativoSelecionado = clienteCorporativoSelecionado;
	}
	
	
	
}
