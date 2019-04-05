package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.TipoNumero;

public interface ITipoNumeroService {
	
	public void salvar(TipoNumero vo) ;
	
	public void atualizar(TipoNumero vo) ;
	
	public void excluir(TipoNumero vo);
	
	public TipoNumero findOne(Integer id);
	
	public List<TipoNumero> listTiposNumeros(String query);
	
	public List<TipoNumero> listTiposNumeros();
}
