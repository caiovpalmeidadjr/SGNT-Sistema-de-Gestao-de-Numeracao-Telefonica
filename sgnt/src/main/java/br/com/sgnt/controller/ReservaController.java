package br.com.sgnt.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.IReservaService;
import br.com.sgnt.service.IStatusService;

@Named
@ViewScoped
public class ReservaController {

	private Reserva reserva = new Reserva();

	@Autowired
	private IReservaService reservaService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private INumeroCNGService numeroCNGService;

	private List<Reserva> listReserva;
	private List<NumeroCNG> listNumerosReserva;

	@PostConstruct
	public void init() {
		listNumerosReserva = numeroCNGService.findReservado(statusService.findOne(2));
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public List<Reserva> getListReserva() {
		return listReserva;
	}

	public void setListReserva(List<Reserva> listReserva) {
		this.listReserva = listReserva;
	}

	public List<NumeroCNG> getListNumerosReserva() {
		return listNumerosReserva;
	}

	public void setListNumerosReserva(List<NumeroCNG> listNumerosReserva) {
		this.listNumerosReserva = listNumerosReserva;
	}

}
