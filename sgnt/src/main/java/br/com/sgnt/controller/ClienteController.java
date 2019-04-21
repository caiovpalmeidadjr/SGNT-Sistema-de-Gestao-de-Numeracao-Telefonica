package br.com.sgnt.controller;

import java.util.InputMismatchException;
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

	// usada nos metodos com relação de injeção de dependencia, apos a injeção de
	// dependencia do spring, eu acesso esse metodo
	@PostConstruct
	private void init() {
		listClientes = clienteService.listClientes();
		listClientesCorporativo = clienteService.listClientesCorporativos();
		listClientesResidencial = clienteService.listClientesResidencial();
		// tambem funciona listClientes = repository.findAll();
	}

	public void salvar() {
		// salvando no banco
		clienteService.salvar(cliente);

		if (!alteracao) {
			// adicionando na lista que vai atualizar a tabela
			listClientes.add(cliente);
		}

		// após salvar coloco o meu alteracao para false
		setAlteracao(false);

		// instanciando pois o proximo a ser adicionada é um novo cliente
		cliente = new Cliente();
	}

	// Java Web Frameworks com Spring Boot - Aula 04 parte 02 19:25 seg

	public void excluir(Cliente cliente) {
		// removendo do banco
		if (cliente != null) {
			clienteService.excluir(cliente);

			// removendo da lista
			listClientes.remove(cliente);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente não selecionado !", null));
		}

	}

	public void excluirCorporativo(ClienteCorporativo clienteCorporativo) {
		// removendo do banco
		if (clienteCorporativo != null) {
			clienteService.excluir(clienteCorporativo);

			// removendo da lista
			listClientes.remove(clienteCorporativo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Corporativo não selecionado !", null));
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

		Cliente c = clienteService.buscarPorCNPJ(clienteCorporativo.getCnpj());
		String cnpj = clienteCorporativo.getCnpj().replace(".", "").replace("/","").replace("-", "");
		
		if (c == null) {
			if(!isCNPJ(cnpj)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "CNPJ inválido", "Cadastro não efetuado!"));
				return;
			}
			
			clienteCorporativo.setLogradouro(e.getLogradouro());
			clienteCorporativo.setBairro(e.getBairro());
			clienteCorporativo.setMunicipio(e.getLocalidade());
			clienteService.salvar(clienteCorporativo);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));

			if (!alteracao) {
				listClientesCorporativo.add(clienteCorporativo);
			}
			setAlteracao(false);

			cliente = new Cliente();
			clienteCorporativo = new ClienteCorporativo();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Cliente já cadastrado", "Cadastro não efetuado!"));
		}

	}
	
	public void salvarResidencial() {

		Cliente c = clienteService.buscarPorCPF(clienteResidencial.getCpf());
		String cpf = clienteResidencial.getCpf().replace(".", "").replace("-", "");
		System.out.println(cpf);
		
		if (c == null) {
			if (!isCPF(cpf)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "CPF inválido", "Cadastro não efetuado!"));
				return;
			}

			clienteResidencial.setBairro(e.getBairro());
			clienteResidencial.setLogradouro(e.getLogradouro());
			clienteResidencial.setMunicipio(e.getLocalidade());
			clienteService.salvar(clienteResidencial);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));

			if (!alteracao) {
				listClientesResidencial.add(clienteResidencial);
			}
			setAlteracao(false);

			cliente = new Cliente();
			clienteResidencial = new ClienteResidencial();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Cliente já cadastrado", "Cadastro não efetuado!"));
		}

	}
	
	public void onRowEdit(RowEditEvent event) {
		Cliente c = ((Cliente) event.getObject());
		FacesMessage msg = new FacesMessage("Cliente alterado!", c.getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		clienteService.salvar(c);
	}
	
	public void onRowCancel(RowEditEvent event) {
		Cliente c = ((Cliente) event.getObject());
		FacesMessage msg = new FacesMessage("Alteração cancelada", c.getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void getEndereco(String cep) {
		cep = cep.replace(".", "").replace("-", "");
		e = ws.getEnderecoPorCep(cep);
		System.out.println(e);
		if (e == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "CEP inválido", "Favor informar um CEP válido"));
		}

	}

	public static boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public Cliente getCliente() {
		// o botão de salvar que vai atualizar, ele sabe se o objeto tem id ou não caso
		// tenha ele realiza o update
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
