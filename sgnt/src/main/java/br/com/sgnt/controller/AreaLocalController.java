package br.com.sgnt.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.repository.AreaLocalRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class AreaLocalController {

	@Autowired
	private AreaLocalRepository areaLocalRepository;
	private AreaLocal areaLocal;

	private List<AreaLocal> listCN;
	private List<AreaLocal> listAreaLocal;
	private Integer cnSelecionado;
	private String area;
	
	public void teste() {
		System.out.println(area);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@PostConstruct
	private void init() {
		listCN = areaLocalRepository.listDistinctCN();
		Collections.sort(listCN);
	}

	public void carregaAreaLocal() {
		listAreaLocal = areaLocalRepository.listAreaLocalCN(cnSelecionado);
		
	}

	public AreaLocalRepository getAreaLocalRepository() {
		return areaLocalRepository;
	}

	public void setAreaLocalRepository(AreaLocalRepository areaLocalRepository) {
		this.areaLocalRepository = areaLocalRepository;
	}

	public AreaLocal getAreaLocal() {
		return areaLocal;
	}

	public void setAreaLocal(AreaLocal areaLocal) {
		this.areaLocal = areaLocal;
	}

	public List<AreaLocal> getListCN() {
		return listCN;
	}

	public void setListCN(List<AreaLocal> listCN) {
		this.listCN = listCN;
	}

	public List<AreaLocal> getListAreaLocal() {
		return listAreaLocal;
	}

	public void setListAreaLocal(List<AreaLocal> listAreaLocal) {
		this.listAreaLocal = listAreaLocal;
	}

	public Integer getCnSelecionado() {
		return cnSelecionado;
	}

	public void setCnSelecionado(Integer cnSelecionado) {
		this.cnSelecionado = cnSelecionado;
	}

}
