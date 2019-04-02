package br.com.sgnt.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import br.com.sgnt.model.NumeroCNG;

public interface INumeroCNGService {
	
	public void salvar(NumeroCNG vo) ;
	
	public void atualizar(NumeroCNG vo) ;
	
	public void excluir(NumeroCNG vo);
	
	public List<NumeroCNG> listNumeroCNG(String query);
	
	public List<NumeroCNG> listNumeroCNG();
	
	public NumeroCNG findNumero(Integer serieNumeroCNG, Integer mcduNumeroCNG);
}
