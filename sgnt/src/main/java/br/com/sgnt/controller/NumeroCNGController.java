package br.com.sgnt.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.repository.NumeroCNGRepository;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.IUsuarioService;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroCNGController {

	@Autowired
	private NumeroCNGRepository numeroCNGRepository;
	
	@Autowired
	private INumeroCNGService numeroCGNService;
	
	private NumeroCNG numeroCNG = new NumeroCNG();
	
	private List<NumeroCNG> listNumeroCNG;
	
	@PostConstruct
	public void init() {
		listNumeroCNG = numeroCGNService.listNumeroCNG();   //numeroCNGRepository.findAll();
	}
	
	public void cadastrar() {
		
		//if(numeroCNG.getSerieNumeroCNG() != 0) {
		NumeroCNG numeroCGN = numeroCGNService.findNumero(numeroCNG.getSerieNumeroCNG(), numeroCNG.getMcduNumeroCNG());
		
		
		try {
			
			if(numeroCGN != null) {
				if((numeroCNG.getSerieNumeroCNG().equals(numeroCGN.getSerieNumeroCNG())) && (numeroCNG.getMcduNumeroCNG().equals(numeroCGN.getMcduNumeroCNG()))) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"CNG já cadastrado", "Erro no cadastro!"));
				} else {
					numeroCNGRepository.save(numeroCNG);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
				}
		
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Erro, número não encontrado !", null));
			}
		} catch (Exception e) {
			numeroCNGRepository.save(numeroCNG);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
		}
//		else {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//					"Por favor digite um valor valido !"));
//		}
		
	}
	
	public NumeroCNGRepository getNumeroCNGRepository() {
		return numeroCNGRepository;
	}

	public void setNumeroCNGRepository(NumeroCNGRepository numeroCNGRepository) {
		this.numeroCNGRepository = numeroCNGRepository;
	}

	public NumeroCNG getNumeroCNG() {
		return numeroCNG;
	}

	public void setNumeroCNG(NumeroCNG numeroCNG) {
		this.numeroCNG = numeroCNG;
	}

	public List<NumeroCNG> getListNumeroCNG() {
		return listNumeroCNG;
	}

	public void setListNumeroCNG(List<NumeroCNG> listNumeroCNG) {
		this.listNumeroCNG = listNumeroCNG;
	}
	
	

}
