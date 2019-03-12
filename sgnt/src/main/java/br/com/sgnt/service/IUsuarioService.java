package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Usuario;

public interface IUsuarioService {
	
	public void salvar(Usuario vo) ;
	
	public void atualizar(Usuario vo) ;
	
	public void excluir(Usuario vo);
	
	public List<Usuario> listUsuarios(String query);
	
	public List<Usuario> listUsuarios();
}
