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
import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.NumeroCNGRepository;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.IReservaService;
import br.com.sgnt.service.IStatusService;
import br.com.sgnt.service.IUsuarioService;

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
	
	@Autowired
	private IUsuarioService usuarioService;
	
	private NumeroCNG numeroCNG = new NumeroCNG();
	
	private List<NumeroCNG> listNumeroCNG;
	private List<NumeroCNG> listNumeroCNGDisponivel;
	
	private NumeroCNG numeroSelecionado;
	
	private Reserva reserva = new Reserva();
	
	@PostConstruct
	public void init() {
		listNumeroCNG = numeroCNGService.listNumeroCNG();
		listNumeroCNGDisponivel = numeroCNGService.findDisponivel(statusService.findOne(1));
	}
	
	public void cadastrar() {
		
		NumeroCNG numCNGBuscado = numeroCNGService.findNumero(numeroCNG.getPrefixoNumeroCNG(), numeroCNG.getSerieNumeroCNG(), numeroCNG.getMcduNumeroCNG());
		
		if (numCNGBuscado == null) {
			numeroCNG.setStatus(statusService.findOne(1));
			numeroCNGService.salvar(numeroCNG);
			numeroCNG = new NumeroCNG();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cadastro efetuado com sucesso", "Cadastro com sucesso!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"CNG já cadastrado", "Erro no cadastro!"));
		}
	}
	
	public void reservar() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		
		reserva.setDataHoraReserva(data);
		reserva.setUsuario(usuario);
		reserva = reservaService.salvar(reserva);
		
		NumeroCNG numeroBuscado = numeroCNGRepository.findNumero(numeroSelecionado.getPrefixoNumeroCNG(), numeroSelecionado.getSerieNumeroCNG(), numeroSelecionado.getMcduNumeroCNG());
		
		if(numeroBuscado != null) {
			if(numeroBuscado.getStatus().getIdStatus().equals(1)) {
				numeroBuscado.setStatus(statusService.buscaPorNome("RESERVADO"));
				System.out.println(numeroBuscado.toString());
				numeroBuscado.setReserva(reserva);
				numeroCNGService.salvar(numeroBuscado);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"CNG reservado com sucesso!", "Reserva Efetuada"));
			} else {
				reservaService.excluir(reserva.getIdReserva());
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
