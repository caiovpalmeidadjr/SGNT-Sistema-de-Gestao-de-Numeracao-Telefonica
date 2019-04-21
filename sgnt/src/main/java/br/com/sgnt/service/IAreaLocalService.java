package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.AreaLocal;

public interface IAreaLocalService {

	public void salvar(AreaLocal vo);

	public void atualizar(AreaLocal vo);

	public void excluir(AreaLocal vo);

	public List<AreaLocal> listAreas(String query);

	public List<AreaLocal> listAreas();

	public List<AreaLocal> listAreaLocalCN(Integer cn);

	public List<AreaLocal> listDistinctCN();
	
	public AreaLocal areaLocalNome(String nome);
}
