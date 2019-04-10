package br.com.sgnt.scheduler.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AlterarStatusNumeracao {

	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	/*
	 * A B C D E F
	 * A: Segundos (0 – 59).
	 * B: Minutos (0 – 59).
	 * C: Horas (0 – 23).
	 * D: Dia (1 – 31).
	 * E: Mês (1 – 12).
	 * F: Dia da semana (0 – 6).
	 * (* * * ignora os campos, qlq valor naqueles campos)*/

	@Scheduled(cron = "0 53 17 * * *", zone = TIME_ZONE)
	public void verificarStausNumeracaoSTFC() {
		
		Properties props;
		
		String pathSTFC = "";
		
		FileInputStream file;
		try {
			
			
			file = new FileInputStream("C:\\config\\config.properties");
			
			props = new Properties();
			props.load(file);
			
			pathSTFC = props.getProperty("prop.path.stfc");
			
			File arquivoRaiz = new File(pathSTFC);
			
			for(File f : arquivoRaiz.listFiles()) {
				//1 - encontrar arquivo com inicial stfc
				//2 - ler arquivo 
				//3 - ignorar cabeçalho
				//4 - para cada linha, verificar no banco o valor do status do numero com o status da linha passado e ai sim mudar caso seja possivel
				//5 - caso atenda a situação eu atualizo
				//6 - caso contrario não faço nada, apenas escrevo no log
				//7 - ler a proxima linha do arquivo
				//8 - Fim do processo
				System.out.println(f.getName());
			}
			
			
			
			System.out.println(props.getProperty("prop.path.stfc"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		
		System.out.println("executei as: " + LocalDateTime.now());
		
		
	}

}
