package br.com.sgnt.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.repository.NumeroCNGRepository;
import br.com.sgnt.repository.ReservaRepository;
import br.com.sgnt.repository.StatusRepository;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.IReservaService;
import br.com.sgnt.service.IStatusService;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroCNGController {

	@Autowired
	private NumeroCNGRepository numeroCNGRepository;
	
	@Autowired
	private INumeroCNGService numeroCNGService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IReservaService reservaService;
	
	private NumeroCNG numeroCNG = new NumeroCNG();
	
	private List<NumeroCNG> listNumeroCNG;
	private List<NumeroCNG> listNumeroCNGDisponivel;
	private NumeroCNG numeroSelecionado;
	
	@Autowired
	private ReservaRepository reservaRepository;
	private Reserva reserva = new Reserva();
	
	@Autowired
	private StatusRepository statusRepository;
	
	
	@PostConstruct
	public void init() {
		listNumeroCNG = numeroCNGService.listNumeroCNG();
		listNumeroCNGDisponivel = numeroCNGService.findDisponivel(statusRepository.findOne(1));
	}
	
	public void cadastrar() {
		
		NumeroCNG numCNGBuscado = numeroCNGService.findNumero(numeroCNG.getPrefixoNumeroCNG(), numeroCNG.getSerieNumeroCNG(), numeroCNG.getMcduNumeroCNG());
		
		if(numCNGBuscado != null) {
//			try {
			if((numeroCNG.getPrefixoNumeroCNG().equals(numCNGBuscado.getPrefixoNumeroCNG()) && numeroCNG.getSerieNumeroCNG().equals(numCNGBuscado.getSerieNumeroCNG())) && (numeroCNG.getMcduNumeroCNG().equals(numCNGBuscado.getMcduNumeroCNG()))) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"CNG já cadastrado", "Erro no cadastro!"));
			} else {
				numeroCNG.setStatus(statusRepository.findOne(1));
				numeroCNGService.salvar(numeroCNG);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
				numeroCNG = new NumeroCNG();
			}
			
//			} catch (Exception e) {
//				//é preciso fazer algum tratamento de valor ?
//				numeroCNG.setStatus(statusService.findOne(1));
//				numeroCNGService.salvar(numeroCNG);
//				numeroCNG = new NumeroCNG();
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Favor, informe um valor !", null));
		}
		
	}
	
	public void reservar() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		
		reserva.setDataHoraReserva(data);
		reserva = reservaRepository.save(reserva);
		
		NumeroCNG numeroBuscado = numeroCNGRepository.findNumero(numeroSelecionado.getPrefixoNumeroCNG(), numeroSelecionado.getSerieNumeroCNG(), numeroSelecionado.getMcduNumeroCNG());
		
		if(numeroBuscado != null) {
			if(numeroBuscado.getStatus().getIdStatus().equals(1)) {
				numeroBuscado.setStatus(statusRepository.buscaPorNome("RESERVADO"));
				System.out.println(numeroBuscado.toString());
				numeroBuscado.setReserva(reserva);
				numeroCNGService.salvar(numeroBuscado);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"CNG reservado com sucesso!", "Reserva Efetuada"));
			} else {
				reservaRepository.delete(reserva.getIdReserva());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"CNG não pode ser reservado", "Reserva Não Efetuada"));
			}
		
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Favor, informe um valor !", null));
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

	public List<NumeroCNG> getListNumeroCNG() {
		return listNumeroCNG;
	}

	public void setListNumeroCNG(List<NumeroCNG> listNumeroCNG) {
		this.listNumeroCNG = listNumeroCNG;
	}

	public List<NumeroCNG> getListNumeroCNGDisponivel() {
		return listNumeroCNGDisponivel;
	}

	public void setListNumeroCNGDisponivel(List<NumeroCNG> listNumeroCNGDisponivel) {
		this.listNumeroCNGDisponivel = listNumeroCNGDisponivel;
	}

	public NumeroCNG getNumeroSelecionado() {
		return numeroSelecionado;
	}

	public void setNumeroSelecionado(NumeroCNG numeroSelecionado) {
		this.numeroSelecionado = numeroSelecionado;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	
	

}
