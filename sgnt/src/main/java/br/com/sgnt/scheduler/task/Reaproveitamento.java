package br.com.sgnt.scheduler.task;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.INumeroSTFCService;
import br.com.sgnt.service.IStatusService;

@Component
@EnableScheduling
public class Reaproveitamento {
	
	@Autowired
	private INumeroSTFCService numeroSTFCService;
	
	@Autowired
	private INumeroCNGService numeroCNGService;
	
	@Autowired
	private IStatusService statusService;
	
	private List<NumeroSTFC> listNumeroSTFC;
	private List<NumeroCNG> listNumeroCNG;
	
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;
	Timestamp data = new Timestamp(System.currentTimeMillis());
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	//double dias =  (data.getTime() - reservasSTFC.get(i).getDataHoraReserva().getTime()) / 86400000;
	
	public void reaproveitamentoSTFC() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		listNumeroSTFC = numeroSTFCService.findNumeroStatus(statusService.findOne(4));
		for(int i=0; i<listNumeroSTFC.size(); i++) {
			double dias = (data.getTime() - listNumeroSTFC.get(i).getDataHoraStatus().getTime()) / 86400000;
			if(dias > 180) {
				listNumeroSTFC.get(i).setStatus(statusService.findOne(1));
				listNumeroSTFC.get(i).setDataHoraStatus(data);
				listNumeroSTFC.get(i).setReserva(null);
				numeroSTFCService.atualizar(listNumeroSTFC.get(i));
			}
		}
		
	}
	
	public void reaproveitamentoCNG() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		listNumeroCNG = numeroCNGService.findDisponivel(statusService.findOne(4));
		for(int i=0; i<listNumeroCNG.size(); i++) {
			double dias = (data.getTime() - listNumeroCNG.get(i).getDataHoraStatus().getTime()) / 86400000;
			if(dias > 180) {
				listNumeroCNG.get(i).setStatus(statusService.findOne(1));
				listNumeroCNG.get(i).setDataHoraStatus(data);
				listNumeroCNG.get(i).setReserva(null);
				numeroCNGService.atualizar(listNumeroCNG.get(i));
			}
		}
	}
	
	/*
	 * A B C D E F
	 * A: Segundos (0 – 59).
	 * B: Minutos (0 – 59).
	 * C: Horas (0 – 23).
	 * D: Dia (1 – 31).
	 * E: Mês (1 – 12).
	 * F: Dia da semana (0 – 6).
	 * (* * * ignora os campos, qlq valor naqueles campos)*/
	
	@Scheduled(cron = "0 0 21 * * ?", zone = TIME_ZONE)
	public void verificaReservas() {
		reaproveitamentoSTFC();
		reaproveitamentoCNG();
	}
	
}
