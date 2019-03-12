package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Perfil;

public interface PerfilService {
	
	public void salvar(Perfil vo) ;
	
	public void atualizar(Perfil vo) ;
	
	public void excluir(Perfil vo);
	
	public List<Perfil> listPerfis(String query);
	
	public List<Perfil> listPerfis();

}
