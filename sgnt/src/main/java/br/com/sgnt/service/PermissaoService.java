package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Permissao;

public interface PermissaoService {
	
	public void salvar(Permissao vo) ;
	
	public void atualizar(Permissao vo) ;
	
	public void excluir(Permissao vo);
	
	public List<Permissao> listPermissoes(String query);
	
	public List<Permissao> listPermissoes();
}
