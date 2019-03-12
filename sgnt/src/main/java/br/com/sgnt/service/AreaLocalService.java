package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.AreaLocal;

public interface AreaLocalService {
	
	public void salvar(AreaLocal vo) ;
	
	public void atualizar(AreaLocal vo) ;
	
	public void excluir(AreaLocal vo);
	
	public List<AreaLocal> listAreas(String query);
	
	public List<AreaLocal> listAreas();
}
