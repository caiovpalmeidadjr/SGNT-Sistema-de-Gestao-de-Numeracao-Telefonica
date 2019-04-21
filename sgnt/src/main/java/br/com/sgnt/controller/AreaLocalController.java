package br.com.sgnt.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.repository.AreaLocalRepository;
import br.com.sgnt.service.IAreaLocalService;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class AreaLocalController {

	@Autowired
	private IAreaLocalService areaLocalService;
	private AreaLocal areaLocal;
	private List<AreaLocal> listCN;
	private List<AreaLocal> listAreaLocal;
	private Integer cnSelecionado;
	private String area;
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@PostConstruct
	private void init() {
		listCN = areaLocalService.listDistinctCN();
		Collections.sort(listCN);
	}

	public void carregaAreaLocal() {
		listAreaLocal = areaLocalService.listAreaLocalCN(cnSelecionado);
		
	}
	
	public IAreaLocalService getAreaLocalService() {
		return areaLocalService;
	}

	public void setAreaLocalService(IAreaLocalService areaLocalService) {
		this.areaLocalService = areaLocalService;
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
