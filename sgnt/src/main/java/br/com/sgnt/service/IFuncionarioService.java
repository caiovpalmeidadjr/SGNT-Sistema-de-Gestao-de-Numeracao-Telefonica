package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Funcionario;

public interface IFuncionarioService {
	
	public void salvar(Funcionario vo) ;
	
	public void atualizar(Funcionario vo) ;
	
	public void excluir(Funcionario vo);
	
	public List<Funcionario> listFuncionarios(String query);
	
	public List<Funcionario> listFuncionarios();

}
