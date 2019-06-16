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
	private List<NumeroCNG> listNumerosReserva;
	
	private double qtdeDisponivel, qtdeAtivado, qtdeReservado, qtdeDesativado;
	private double taxaUtilizacao;
	
	private NumeroCNG numeroSelecionado;
	
	private Reserva reserva = new Reserva();
	private Integer idReserva;
	
	@PostConstruct
	public void init() {
		listNumeroCNG = numeroCNGService.listNumeroCNG();
		listNumeroCNGDisponivel = numeroCNGService.findDisponivel(statusService.findOne(1));
	}
	
	public void cadastrar() {
		
		NumeroCNG numCNGBuscado = numeroCNGService.findNumero(numeroCNG.getPrefixoNumeroCNG(), numeroCNG.getSerieNumeroCNG(), numeroCNG.getMcduNumeroCNG());
		Timestamp data = new Timestamp(System.currentTimeMillis());
		
		if (numCNGBuscado == null) {
			numeroCNG.setStatus(statusService.findOne(1));
			numeroCNG.setDataHoraStatus(data);
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
		
		NumeroCNG numeroBuscado = numeroCNGService.findNumero(numeroSelecionado.getPrefixoNumeroCNG(), numeroSelecionado.getSerieNumeroCNG(), numeroSelecionado.getMcduNumeroCNG());
		
		if(numeroBuscado != null) {
			if(numeroBuscado.getStatus().getIdStatus().equals(1)) {
				numeroBuscado.setStatus(statusService.buscaPorNome("RESERVADO"));
				numeroBuscado.setDataHoraStatus(data);
				System.out.println(numeroBuscado.toString());
				numeroBuscado.setReserva(reserva);
				numeroCNGService.salvar(numeroBuscado);
				listNumeroCNGDisponivel.remove(numeroSelecionado);
				idReserva = reserva.getIdReserva();
				reserva = new Reserva();
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
	
	public void carregaReserva() {		
		try {
			reserva = reservaService.findOne(idReserva);
			listNumerosReserva = numeroCNGService.findReserva(reserva);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Informe o ID da reserva", null));
		}
		
	}
	
	public void excluirReserva() {
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		
		if(reserva.getUsuario().equals(usuario) || usuario.getPerfil().getIdPerfil().equals(1)) {
			for (int i=0; i<listNumerosReserva.size(); i++) {
				listNumerosReserva.get(i).setReserva(null);
				listNumerosReserva.get(i).setStatus(statusService.findOne(1));
				numeroCNGService.atualizar(listNumerosReserva.get(i));
				listNumerosReserva.remove(i);
			}
			reservaService.excluir(reserva);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sem permissão para exclusão", null));
		}
		
	}
	
	public void revalidarReserva() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		
		if(reserva.getUsuario().equals(usuario) || usuario.getPerfil().getIdPerfil().equals(1)) {
			for (int i=0; i<listNumerosReserva.size(); i++) {
				listNumerosReserva.get(i).setDataHoraStatus(data);
				numeroCNGService.atualizar(listNumerosReserva.get(i));
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Reserva validada!", null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sem permissão!", null));
		}
	}
	
	public void quantidadeRelatorio() {
		qtdeDisponivel = 0;
		qtdeAtivado = 0;
		qtdeReservado = 0;
		qtdeDesativado = 0;
		
		for (NumeroCNG numeroCNG : listNumeroCNG) {
			if(numeroCNG.getStatus().getIdStatus().equals(1)) {
				qtdeDisponivel++;
			} else if (numeroCNG.getStatus().getIdStatus().equals(2)) {
				qtdeAtivado++;
			} else if(numeroCNG.getStatus().getIdStatus().equals(3)) {
				qtdeReservado++;
			} else if(numeroCNG.getStatus().getIdStatus().equals(4)) {
				qtdeDesativado++;
			}
			
			taxaUtilizacao = ((qtdeAtivado + qtdeReservado) / (qtdeDisponivel+qtdeAtivado+qtdeReservado+qtdeDesativado)) * 100;
			
		}
		
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

	public List<NumeroCNG> getListNumerosReserva() {
		return listNumerosReserva;
	}

	public void setListNumerosReserva(List<NumeroCNG> listNumerosReserva) {
		this.listNumerosReserva = listNumerosReserva;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public double getQtdeDisponivel() {
		return qtdeDisponivel;
	}

	public void setQtdeDisponivel(double qtdeDisponivel) {
		this.qtdeDisponivel = qtdeDisponivel;
	}

	public double getQtdeAtivado() {
		return qtdeAtivado;
	}

	public void setQtdeAtivado(double qtdeAtivado) {
		this.qtdeAtivado = qtdeAtivado;
	}

	public double getQtdeReservado() {
		return qtdeReservado;
	}

	public void setQtdeReservado(double qtdeReservado) {
		this.qtdeReservado = qtdeReservado;
	}

	public double getQtdeDesativado() {
		return qtdeDesativado;
	}

	public void setQtdeDesativado(double qtdeDesativado) {
		this.qtdeDesativado = qtdeDesativado;
	}

	public double getTaxaUtilizacao() {
		return taxaUtilizacao;
	}

	public void setTaxaUtilizacao(double taxaUtilizacao) {
		this.taxaUtilizacao = taxaUtilizacao;
	}
	
}
