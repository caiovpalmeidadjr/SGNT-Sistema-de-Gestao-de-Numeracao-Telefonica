package br.com.sgnt.controller;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.Funcionario;
import br.com.sgnt.repository.FuncionarioRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class FuncionarioController {

	// pegando um objeto do clienteRepository para salvar o objeto da tela no banco
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	private Funcionario funcionario = new Funcionario();

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void cadastrar() {
		funcionarioRepository.save(funcionario);
	}

}
