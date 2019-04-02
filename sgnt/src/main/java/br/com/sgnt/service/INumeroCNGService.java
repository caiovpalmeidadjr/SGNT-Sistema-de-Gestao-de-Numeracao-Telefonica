package br.com.sgnt.service;

import java.util.List;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.Status;

public interface INumeroCNGService {
	
	public void salvar(NumeroCNG vo) ;
	
	public void atualizar(NumeroCNG vo) ;
	
	public void excluir(NumeroCNG vo);
	
	public List<NumeroCNG> listNumeroCNG(String query);
	
	public List<NumeroCNG> listNumeroCNG();
	
	public NumeroCNG findNumero(Integer prefixoNumeroCNG,Integer serieNumeroCNG, Integer mcduNumeroCNG);
	
	public List<NumeroCNG> findDisponivel(Status status);
}
