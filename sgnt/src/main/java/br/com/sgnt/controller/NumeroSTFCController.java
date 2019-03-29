package br.com.sgnt.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.AreaLocal;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Status;
import br.com.sgnt.model.TipoNumero;
import br.com.sgnt.repository.AreaLocalRepository;
import br.com.sgnt.repository.NumeroSTFCRepository;
import br.com.sgnt.repository.StatusRepository;
import br.com.sgnt.repository.TipoNumeroRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroSTFCController implements Serializable {

	@Autowired
	private NumeroSTFCRepository numeroSTFCRepository;
	private NumeroSTFC numeroSTFC = new NumeroSTFC();
	private AreaLocalController areaLocalController;

	@Autowired
	private AreaLocalRepository areaLocalRepository;
	private AreaLocal areaLocal = new AreaLocal();

	@Autowired
	private TipoNumeroRepository tipoNumerorepository;
	private TipoNumero tipo = new TipoNumero();
	
	@Autowired
	private StatusRepository statusRepository;
	private Status status = new Status();

	private List<AreaLocal> listCN;
	private List<AreaLocal> listAreaLocal;
	private List<NumeroSTFC> listNumeroSTFC;
	private List<NumeroSTFC> listNumeroSTFCCorporativo = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroSTFCResidencial = new ArrayList<NumeroSTFC>();
	private List<Integer> listPrefixo;
	private Integer cnSelecionado;
	private Integer prefixo, qtdeMCDUOk;
	private String area;
	private Integer faixaInicial, faixaFinal, tempInicial, tempFinal;
	private List<NumeroSTFC> lista = new ArrayList<NumeroSTFC>();
	List<Integer> mcduOk = new ArrayList<Integer>();
	List<Integer> mcduErro = new ArrayList<Integer>();
	
	@PostConstruct
	private void init() {
		listCN = areaLocalRepository.listDistinctCN();
		Collections.sort(listCN);
		listNumeroSTFC = numeroSTFCRepository.listNumeroCorporativo();	
		carregaListaNumeros();
	}

	public void cadastrar(String nomeTipo) {
		areaLocal = areaLocalRepository.areaLocalNome(area);
		tipo = tipoNumerorepository.pesquisaNome(nomeTipo);
		numeroSTFC.setTipoNumero(tipo);
		numeroSTFC.setAreaLocal(areaLocal);
		status = statusRepository.buscaPorNome("DISPONIVEL");
		boolean confereAL = verificaAL();
		if (confereAL == true) {
			Integer prefixo = numeroSTFC.getPrefixoNumeroSTFC();
			cadastraMCDU(prefixo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Prefixo cadastrado para outra área local", "Erro no cadastro!"));
		}
		
	}
	
	public void carregaListaNumeros() {
		for (NumeroSTFC numeroSTFC : listNumeroSTFC) {
			if(numeroSTFC.getTipoNumero().getIdTipoNumero() == 1) {
				listNumeroSTFCCorporativo.add(numeroSTFC);
			} else {
				listNumeroSTFCResidencial.add(numeroSTFC);
			}
		}
	}

	public boolean verificaAL() {
		lista = numeroSTFCRepository.getListaAreaLocal(numeroSTFC.getPrefixoNumeroSTFC());

		try {
			if (numeroSTFC.getAreaLocal().getCnAreaLocal().equals(lista.get(0).getAreaLocal().getCnAreaLocal())) {
				if (numeroSTFC.getAreaLocal().equals(lista.get(0).getAreaLocal())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

	}

	public void cadastraMCDU(Integer prefixo) {
		List<Integer> mcdu = new ArrayList<Integer>();
		int quantidade = (faixaFinal - faixaInicial) + 1;
		
		tempInicial = faixaInicial;
		tempFinal = faixaFinal;
		
		if (tempInicial > tempFinal) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Faixa Inicial deve ser menor que Faixa Final!", "Erro no cadastro!"));
		} else {
			
			while (tempInicial <= tempFinal) {
				numeroSTFC = new NumeroSTFC();
				numeroSTFC.setAreaLocal(areaLocal);
				numeroSTFC.setTipoNumero(tipo);
				numeroSTFC.setPrefixoNumeroSTFC(prefixo);
				numeroSTFC.setStatus(status);
				numeroSTFC.setMcduNumeroSTFC(tempInicial);
	
				try {
					numeroSTFCRepository.save(numeroSTFC);
				} catch (Exception e) {
					mcdu.add(tempInicial);
				}
	
				tempInicial++;
			}
	
			if (mcdu.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Prefixo cadastrado!", "Cadastro com sucesso"));
			} else if (mcdu.size() < quantidade) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Alguns números já estavam cadastrados. Demais cadastrados com sucesso", "Cadastro com sucesso"));
			} else if (mcdu.size() == quantidade) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Faixa já cadastrada", "Erro de cadastro"));
			}
		}

	}
	
	public void reservar() {
		AreaLocal a = areaLocalRepository.areaLocalNome(area);
		tempInicial = faixaInicial;
		tempFinal = faixaFinal;
		status = statusRepository.buscaPorNome("RESERVADO");
				
		if(tempInicial > tempFinal) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Faixa Inicial deve ser menor que Faixa Final!", "Erro no cadastro!"));
		} else {
			while(tempInicial <= tempFinal) {
				NumeroSTFC n = numeroSTFCRepository.numeroAreaLocal(prefixo, tempInicial, a);
				
				if (n.getStatus().getIdStatus().equals(1)) {
					n.setStatus(status);
					numeroSTFCRepository.save(n);
					mcduOk.add(faixaInicial);
				} else {
					mcduErro.add(tempInicial);
				}
				
				tempInicial++;
			}
		}
		
		qtdeMCDUOk = mcduOk.size();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"MCDU cadastrados: " + mcduOk.size() , "MCDU com erros: " + mcduErro.size()));
		
	}
	
	
	public void onCNChange() {
		listAreaLocal = areaLocalRepository.listAreaLocalCN(cnSelecionado);
	}

	public void onAreaLocalChange() {
		listPrefixo = numeroSTFCRepository.listPrefixo(areaLocalRepository.areaLocalNome(area));

	}

	public NumeroSTFCRepository getNumeroSTFCRepository() {
		return numeroSTFCRepository;
	}

	public void setNumeroSTFCRepository(NumeroSTFCRepository numeroSTFCRepository) {
		this.numeroSTFCRepository = numeroSTFCRepository;
	}

	public NumeroSTFC getNumeroSTFC() {
		return numeroSTFC;
	}

	public void setNumeroSTFC(NumeroSTFC numeroSTFC) {
		this.numeroSTFC = numeroSTFC;
	}

	public AreaLocalController getAreaLocalController() {
		return areaLocalController;
	}

	public void setAreaLocalController(AreaLocalController areaLocalController) {
		this.areaLocalController = areaLocalController;
	}

	public AreaLocalRepository getAreaLocalRepository() {
		return areaLocalRepository;
	}

	public void setAreaLocalRepository(AreaLocalRepository areaLocalRepository) {
		this.areaLocalRepository = areaLocalRepository;
	}

	public AreaLocal getAreaLocal() {
		return areaLocal;
	}

	public void setAreaLocal(AreaLocal areaLocal) {
		this.areaLocal = areaLocal;
	}

	public List<AreaLocal> getListCN() {
		return listCN;
	}

	public void setListCN(List<AreaLocal> listCN) {
		this.listCN = listCN;
	}

	public List<AreaLocal> getListAreaLocal() {
		return listAreaLocal;
	}

	public void setListAreaLocal(List<AreaLocal> listAreaLocal) {
		this.listAreaLocal = listAreaLocal;
	}

	public Integer getCnSelecionado() {
		return cnSelecionado;
	}

	public void setCnSelecionado(Integer cnSelecionado) {
		this.cnSelecionado = cnSelecionado;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getFaixaInicial() {
		return faixaInicial;
	}

	public void setFaixaInicial(Integer faixaInicial) {
		this.faixaInicial = faixaInicial;
	}

	public Integer getFaixaFinal() {
		return faixaFinal;
	}

	public void setFaixaFinal(Integer faixaFinal) {
		this.faixaFinal = faixaFinal;
	}

	public TipoNumeroRepository getTipoNumerorepository() {
		return tipoNumerorepository;
	}

	public void setTipoNumerorepository(TipoNumeroRepository tipoNumerorepository) {
		this.tipoNumerorepository = tipoNumerorepository;
	}

	public TipoNumero getTipo() {
		return tipo;
	}

	public void setTipo(TipoNumero tipo) {
		this.tipo = tipo;
	}

	public List<NumeroSTFC> getListNumeroSTFCCorporativo() {
		return listNumeroSTFCCorporativo;
	}

	public void setListNumeroSTFCCorporativo(List<NumeroSTFC> listNumeroSTFCCorporativo) {
		this.listNumeroSTFCCorporativo = listNumeroSTFCCorporativo;
	}

	public List<NumeroSTFC> getListNumeroSTFC() {
		return listNumeroSTFC;
	}

	public void setListNumeroSTFC(List<NumeroSTFC> listNumeroSTFC) {
		this.listNumeroSTFC = listNumeroSTFC;
	}

	public List<NumeroSTFC> getListNumeroSTFCResidencial() {
		return listNumeroSTFCResidencial;
	}

	public void setListNumeroSTFCResidencial(List<NumeroSTFC> listNumeroSTFCResidencial) {
		this.listNumeroSTFCResidencial = listNumeroSTFCResidencial;
	}

	public StatusRepository getStatusRepository() {
		return statusRepository;
	}

	public void setStatusRepository(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Integer> getListPrefixo() {
		return listPrefixo;
	}

	public void setListPrefixo(List<Integer> listPrefixo) {
		this.listPrefixo = listPrefixo;
	}

	public Integer getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(Integer prefixo) {
		this.prefixo = prefixo;
	}

	public List<NumeroSTFC> getLista() {
		return lista;
	}

	public void setLista(List<NumeroSTFC> lista) {
		this.lista = lista;
	}

	public List<Integer> getMcduOk() {
		return mcduOk;
	}

	public void setMcduOk(List<Integer> mcduOk) {
		this.mcduOk = mcduOk;
	}

	public List<Integer> getMcduErro() {
		return mcduErro;
	}

	public void setMcduErro(List<Integer> mcduErro) {
		this.mcduErro = mcduErro;
	}

	public Integer getQtdeMCDUOk() {
		return qtdeMCDUOk;
	}

	public void setQtdeMCDUOk(Integer qtdeMCDUOk) {
		this.qtdeMCDUOk = qtdeMCDUOk;
	}
	
	
	
}
