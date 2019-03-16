package br.com.sgnt.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.repository.NumeroCNGRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroCNGController {

	@Autowired
	private NumeroCNGRepository numeroCNGRepository;
	private NumeroCNG numeroCNG = new NumeroCNG();
	
	public void cadastrar() {
		
		NumeroCNG num = numeroCNGRepository.findNumero(numeroCNG.getSerieNumeroCNG(), numeroCNG.getMcduNumeroCNG());
		
		try {
		if((numeroCNG.getSerieNumeroCNG().equals(num.getSerieNumeroCNG())) && (numeroCNG.getMcduNumeroCNG().equals(num.getMcduNumeroCNG()))) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"CNG já cadastrado", "Erro no cadastro!"));
		} else {
			numeroCNGRepository.save(numeroCNG);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
		}
		} catch (Exception e) {
			numeroCNGRepository.save(numeroCNG);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
		}
		
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

}
