package br.com.sgnt.scheduler.task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import br.com.sgnt.model.Reserva;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.INumeroSTFCService;
import br.com.sgnt.service.IReservaService;
import br.com.sgnt.service.IStatusService;


@Named
@ViewScoped
@Component
@EnableScheduling
public class EnviarEmail {
	
	@Autowired
	private IReservaService reservaService;
	
	@Autowired
	private INumeroSTFCService numeroSTFCService;
	
	@Autowired
	private INumeroCNGService numeroCNGService;
	
	@Autowired
	private IStatusService statusService;
	
	List<Reserva> reservasSTFC;
	List<Reserva> reservasCNG;
	List<Reserva> reservasNotificar = new ArrayList<Reserva>();
	List<Reserva> reservasCancelar = new ArrayList<Reserva>();
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;
	Timestamp data = new Timestamp(System.currentTimeMillis());
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	public void enviarEmail(String reservas) {		
		Properties props = new Properties();
        //Parâmetros de conexão com servidor Gmail
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() 
                         {
                               return new PasswordAuthentication("andreocoutinho@gmail.com", "pamadre3");
                         }
                    });

        //Ativa Debug para sessão
        session.setDebug(true);
        
        try {

              Message message = new MimeMessage(session);
              message.setFrom(new InternetAddress("seuemail@gmail.com")); //Remetente

              Address[] toUser = InternetAddress //Destinatário(s)
                         .parse("andreocoutinho@gmail.com");  

              message.setRecipients(Message.RecipientType.TO, toUser);
              message.setSubject("Enviando email com JavaMail");//Assunto
              message.setText(reservas);
              //Método para enviar a mensagem criada
              Transport.send(message);
              
         } catch (MessagingException e) {
              throw new RuntimeException(e);
        }
        
	}
	
	public void verificaReservasSTFC() {
		reservasSTFC = numeroSTFCService.findReservaVencendo(statusService.findOne(2));
		int qtdeReserva = 0;
		int qtdeCancelar = 0;
		for(int i=0; i<reservasSTFC.size(); i++) {
			System.out.println(reservasSTFC.get(i).toString());
			double dias =  (data.getTime() - reservasSTFC.get(i).getDataHoraReserva().getTime()) / 86400000;
			System.out.println(dias);
			if(dias >= 130 && dias < 180) {
				qtdeReserva++;
				reservasNotificar.add(reservasSTFC.get(i));
			} else if(dias >= 180) {
				qtdeCancelar++;
				System.out.println(qtdeCancelar);
				reservasCancelar.add(reservasSTFC.get(i));
			}
		}
		
		if(qtdeReserva == 0) {
			enviarEmail("Sem reservas vencendo!");
		} else {
			String reservaVencer = "Reservas com data próxima de expirar! \n";
			for (Reserva reserva : reservasNotificar) {
				reservaVencer = reservaVencer + reserva.getIdReserva() + " - " + reserva.getUsuario().getEmail() + "\n";
			}
			enviarEmail(reservaVencer);
		}
		
		if(qtdeCancelar == 0) {
			enviarEmail("Sem reservas para cancelar");
		} else {
			String cancelar = "Reservas canceladas! \n";
			for (Reserva reserva : reservasCancelar) {
				cancelar = cancelar + reserva.getIdReserva() + " - " + reserva.getUsuario().getEmail() + "\n";
				
			}
			enviarEmail(cancelar);
		}
	}
	
	public void verificaReservasCNG() {
		reservasCNG = numeroCNGService.findReservaVencendo(statusService.findOne(2));
		int qtdeReserva = 0;
		int qtdeCancelar = 0;
		for(int i=0; i<reservasCNG.size(); i++) {
			System.out.println(reservasCNG.get(i).toString());
			double dias =  (data.getTime() - reservasCNG.get(i).getDataHoraReserva().getTime()) / 86400000;
			System.out.println(dias);
			if(dias >= 130 && dias < 180) {
				qtdeReserva++;
				reservasNotificar.add(reservasCNG.get(i));
			} else if(dias >= 180) {
				qtdeCancelar++;
				System.out.println(qtdeCancelar);
				reservasCancelar.add(reservasCNG.get(i));
			}
		}
		
		if(qtdeReserva == 0) {
			enviarEmail("Sem reservas vencendo!");
		} else {
			String reservaVencer = "Reservas com data próxima de expirar! \n";
			for (Reserva reserva : reservasNotificar) {
				reservaVencer = reservaVencer + reserva.getIdReserva() + " - " + reserva.getUsuario().getEmail() + "\n";
			}
			enviarEmail(reservaVencer);
		}
		
		if(qtdeCancelar == 0) {
			enviarEmail("Sem reservas para cancelar");
		} else {
			String cancelar = "Reservas canceladas! \n";
			for (Reserva reserva : reservasCancelar) {
				cancelar = cancelar + reserva.getIdReserva() + " - " + reserva.getUsuario().getEmail() + "\n";
				
			}
			enviarEmail(cancelar);
		}
	}
	
	
}
