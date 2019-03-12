package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.NumeroSTFC;

public interface NumeroCNGService {
	
	public void salvar(NumeroSTFC vo) ;
	
	public void atualizar(NumeroSTFC vo) ;
	
	public void excluir(NumeroSTFC vo);
	
	public List<NumeroSTFC> listNumerosSTFC(String query);
	
	public List<NumeroSTFC> listNumerosSTFC();
}
