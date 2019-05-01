package br.com.sgnt.scheduler.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.sgnt.model.NumeroCNG;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Status;
import br.com.sgnt.service.IAreaLocalService;
import br.com.sgnt.service.INumeroCNGService;
import br.com.sgnt.service.INumeroSTFCService;

@Component
@EnableScheduling
public class AlterarStatusNumeracao {

//	private final long SEGUNDO = 1000;
//	private final long MINUTO = SEGUNDO * 60;
//	private final long HORA = MINUTO * 60;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	@Autowired
	private INumeroSTFCService stfcService;
	
	@Autowired
	private IAreaLocalService areaLocalService;
	
	@Autowired
	private INumeroCNGService cngService;
	
	/*
	 * A B C D E F
	 * A: Segundos (0 – 59).
	 * B: Minutos (0 – 59).
	 * C: Horas (0 – 23).
	 * D: Dia (1 – 31).
	 * E: Mês (1 – 12).
	 * F: Dia da semana (0 – 6).
	 * (* * * ignora os campos, qlq valor naqueles campos)*/
	
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
	        
	        String areaLocal;        
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
						
						while(st.hasMoreTokens()) {
							
							//pegandos campos da linha e colocando em variaveis
							areaLocal = st.nextToken();
							prefixo = st.nextToken();
							mcdu = st.nextToken();
							status = st.nextToken();
							
							int statusConvertido = Integer.parseInt(status);
							
							//para cada linha, verificar no banco o valor do status do numero com o status da linha passado e ai sim mudar caso seja possivel
							NumeroSTFC numSTFCBuscado = stfcService.findNumberSTFC(areaLocalService.findIdporSigla(areaLocal), Integer.parseInt(prefixo), Integer.parseInt(mcdu));
							
							if(numSTFCBuscado != null) {
								//Se status arquivo = "ATIVADO" E status banco = "RESERVADO"
								//Altera Status no banco para "ATIVADO
								if(statusConvertido == 3 && numSTFCBuscado.getStatus().getIdStatus() == 2) {
									Status novoStatus = new Status();
									novoStatus.setIdStatus(3);
									
									numSTFCBuscado.setStatus(novoStatus);
									numSTFCBuscado.setDataHoraStatus(new Timestamp(System.currentTimeMillis()));
									
									stfcService.atualizar(numSTFCBuscado);
									arquivoLog.write("INFO: Atualizando Status para 3(ATIVADO) do STFC com ID: " + numSTFCBuscado.getIdNumeroSTFC() + "\r\n");
								
								//Se status arquivo = "DESATIVADO" E status banco = "ATIVADO"
							    //Altera status no banco para "DESATIVADO"	
								}else if (statusConvertido == 4 && numSTFCBuscado.getStatus().getIdStatus() == 3) {
									Status novoStatus = new Status();
									novoStatus.setIdStatus(4);
									
									numSTFCBuscado.setStatus(novoStatus);
									numSTFCBuscado.setDataHoraStatus(new Timestamp(System.currentTimeMillis()));
									
									stfcService.atualizar(numSTFCBuscado);
									arquivoLog.write("INFO: Atualizando Status para 4(DESATIVADO) do STFC com ID: " + numSTFCBuscado.getIdNumeroSTFC() + "\r\n");
									
								}else {
									//Senão
									//Não altera o status no banco e somente registra em log o numero, status do txt e status do banco
									arquivoLog.write("INFO: Status invalido para ser modificado" + "\r\n");
								
								}
									
							}
							
							//System.out.println("cn"+cn+" prefixo"+prefixo+" mcdu"+mcdu+" status"+status);
							
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
			
			arquivoLog.write("INFO: Fim do Processo de Atualização" + "\r\n");
			arquivoLog.close();
			System.out.println(props.getProperty("prop.path.stfc"));
			
		} catch (FileNotFoundException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		} catch (IOException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	public void verificarStausNumeracaoCNG() throws IOException{
		
		Properties props;
		
		String pathCNG = "";
		String pathLogCNG = "";
		
		FileWriter arquivoLog = null;
		
		FileInputStream file;
		
		Calendar cal = Calendar.getInstance();
		
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
	    
	    String strDate = sdf.format(cal.getTime());

	    
		try {
			
			FileReader reader;
			
			BufferedReader leitor;
	         
	        StringTokenizer st = null;
	        
	        String prefixo;        
	        String serie;   
	        String mcdu;
	        String status;
	        String linha;
			
			file = new FileInputStream("C:\\config\\config.properties");
			
			props = new Properties();
			props.load(file);
			
			pathCNG = props.getProperty("prop.path.cng");
			
			pathLogCNG = props.getProperty("prop.path.log.cng");
			
			arquivoLog = new FileWriter(new File(pathLogCNG+"\\logCNG"+strDate+".txt"));
			
			File arquivoRaiz = new File(pathCNG);
			
			arquivoLog.write("----- Atualização de Status CMG -----" + "\r\n");
			
			for(File fileEncontrado : arquivoRaiz.listFiles()) {
				
				//encontrar arquivo com inicial stfc
				if(fileEncontrado.getName().contains("cng")) {
					
					//escrevendo no arquivo de log
					arquivoLog.write("INFO: Encontramos o arquivo:" + fileEncontrado.getName() + "\r\n");
					
					System.out.println(arquivoRaiz+"\\"+fileEncontrado.getName() +"simmmm");
					
					//ler arquivo 
					reader = new FileReader(arquivoRaiz+"\\"+fileEncontrado.getName()); // Localização do Arquivo
					
					leitor = new BufferedReader(reader);
					
					while((linha = leitor.readLine()) !=null) {
					
						//delimitador parar dividir os campos
						st = new StringTokenizer(linha,  ";");
						
						while(st.hasMoreTokens()) {
							
							//pegandos campos da linha e colocando em variaveis
							prefixo = st.nextToken();
							serie = st.nextToken();
							mcdu = st.nextToken();
							status = st.nextToken();
							
							int statusConvertido = Integer.parseInt(status);
							
							//para cada linha, verificar no banco o valor do status do numero com o status da linha passado e ai sim mudar caso seja possivel
							NumeroCNG numCNGBuscado = cngService.findNumero(Integer.parseInt(prefixo), Integer.parseInt(serie), Integer.parseInt(mcdu));
							
							if(numCNGBuscado != null) {
								//Se status arquivo = "ATIVADO" E status banco = "RESERVADO"
								//Altera Status no banco para "ATIVADO
								if(statusConvertido == 3 && numCNGBuscado.getStatus().getIdStatus() == 2) {
									Status novoStatus = new Status();
									novoStatus.setIdStatus(3);
									
									numCNGBuscado.setStatus(novoStatus);
									numCNGBuscado.setDataHoraStatus(new Timestamp(System.currentTimeMillis()));
									
									cngService.atualizar(numCNGBuscado);
									arquivoLog.write("INFO: Atualizando Status para 3(ATIVADO) do STFC com ID: " + numCNGBuscado.getIdNumeroCNG() + "\r\n");
								
								//Se status arquivo = "DESATIVADO" E status banco = "ATIVADO"
							    //Altera status no banco para "DESATIVADO"	
								}else if (statusConvertido == 4 && numCNGBuscado.getStatus().getIdStatus() == 3) {
									Status novoStatus = new Status();
									novoStatus.setIdStatus(4);
									
									numCNGBuscado.setStatus(novoStatus);
									numCNGBuscado.setDataHoraStatus(new Timestamp(System.currentTimeMillis()));
									
									cngService.atualizar(numCNGBuscado);
									arquivoLog.write("INFO: Atualizando Status para 4(DESATIVADO) do STFC com ID: " + numCNGBuscado.getIdNumeroCNG() + "\r\n");
									
								}else {
									//Senão
									//Não altera o status no banco e somente registra em log o numero, status do txt e status do banco
									arquivoLog.write("INFO: Status invalido para ser modificado" + "\r\n");
								
								}
									
							}
							
							//System.out.println("cn"+cn+" prefixo"+prefixo+" mcdu"+mcdu+" status"+status);
							
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
			
			arquivoLog.write("INFO: Fim do Processo de Atualização" + "\r\n");
			arquivoLog.close();
			System.out.println(props.getProperty("prop.path.cng"));
			
		} catch (FileNotFoundException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		} catch (IOException e) {
			arquivoLog.write("ERROR: Foi encontrado um erro:" + e.getMessage() + "\r\n");
			e.printStackTrace();
			
		}
		
	}
	
	@Scheduled(cron = "* 16 15 * * *", zone = TIME_ZONE)
	public void verificarStausNumeracao() throws IOException {
		verificarStausNumeracaoSTFC();
		verificarStausNumeracaoCNG();
	}
	
}
