package br.com.sgnt.scheduler.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.service.IClienteService;
import br.com.sgnt.service.INumeroSTFCService;

@Component
@EnableScheduling
public class AlterarStatusNumeracao {

	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;
	private final long HORA = MINUTO * 60;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	@Autowired
	private INumeroSTFCService stfcService;
	
	/*
	 * A B C D E F
	 * A: Segundos (0 – 59).
	 * B: Minutos (0 – 59).
	 * C: Horas (0 – 23).
	 * D: Dia (1 – 31).
	 * E: Mês (1 – 12).
	 * F: Dia da semana (0 – 6).
	 * (* * * ignora os campos, qlq valor naqueles campos)*/

	@Scheduled(cron = "0 33 14 * * *", zone = TIME_ZONE)
	public void verificarStausNumeracaoSTFC() throws IOException{
		
		
		
		Properties props;
		
		String pathSTFC = "";
		String pathLogSTFC = "";
		
		FileWriter arquivoLog = null;
		
		FileInputStream file;
		
		Calendar cal = Calendar.getInstance();
		
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
	    
	    String strDate = sdf.format(cal.getTime());
	    
		try {
			
			FileReader reader;
			
			BufferedReader leitor;
	         
	        StringTokenizer st = null;
	        
	        String cn;        
	        String prefixo;   
	        String mcdu;
	        String status;
	        String linha;
			
			file = new FileInputStream("C:\\config\\config.properties");
			
			props = new Properties();
			props.load(file);
			
			pathSTFC = props.getProperty("prop.path.stfc");
			
			pathLogSTFC = props.getProperty("prop.path.log.stfc");
			
			arquivoLog = new FileWriter(new File(pathLogSTFC+"\\logSTFC"+strDate+".txt"));
			
			File arquivoRaiz = new File(pathSTFC);
			
			arquivoLog.write("----- Atualização de Status STFC -----" + "\r\n");
			
			for(File fileEncontrado : arquivoRaiz.listFiles()) {
				
				//encontrar arquivo com inicial stfc
				if(fileEncontrado.getName().contains("stfc")) {
					
					//escrevendo no arquivo de log
					arquivoLog.write("INFO: Encontramos o arquivo:" + fileEncontrado.getName() + "\r\n");
					
					System.out.println(arquivoRaiz+"\\"+fileEncontrado.getName() +"simmmm");
					
					//ler arquivo 
					reader = new FileReader(arquivoRaiz+"\\"+fileEncontrado.getName()); // Localização do Arquivo
					
					leitor = new BufferedReader(reader);
					
					while((linha = leitor.readLine()) !=null) {
					
						//delimitador parar dividir os campos
						st = new StringTokenizer(linha,  ";");
						
						String dados = "";
						
						while(st.hasMoreTokens()) {
							
							//pegandos campos da linha e colocando em variaveis
							cn = st.nextToken();
							prefixo = st.nextToken();
							mcdu = st.nextToken();
							status = st.nextToken();
							
							//para cada linha, verificar no banco o valor do status do numero com o status da linha passado e ai sim mudar caso seja possivel
							NumeroSTFC numSTFCBuscado = stfcService.findNumberSTFC(cn, prefixo, mcdu, status, linha);
							
							if(numSTFCBuscado != null) {
								//caso atenda a situação eu atualizo
							}
							
							System.out.println("cn"+cn+" prefixo"+prefixo+" mcdu"+mcdu+" status"+status);
							
							//stfcService.atualizar(reader);
						}
					
					}
					
				}else {
					arquivoLog.write("INFO: Arquivo não processado:" + fileEncontrado.getName() + "\r\n");
					System.out.println(fileEncontrado.getName() +"nãooooo");
				}
				
				
				//3 - ignorar cabeçalho
				
				
				//6 - caso contrario não faço nada, apenas escrevo no log
				//7 - ler a proxima linha do arquivo
				//8 - Fim do processo
				//System.out.println(file.getName());
			}
			
			
			arquivoLog.close();
			System.out.println(props.getProperty("prop.path.stfc"));
			
		} catch (FileNotFoundException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		} catch (IOException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		}
		
		
		System.out.println("executei as: " + LocalDateTime.now());
		
		
	}

}
