package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.Localidade;

public interface ILocalidadeService {
	
	public void salvar(Localidade vo) ;
	
	public void atualizar(Localidade vo) ;
	
	public void excluir(Localidade vo);
	
	public List<Localidade> listLocalidades(String query);
	
	public List<Localidade> listLocalidades();
}
