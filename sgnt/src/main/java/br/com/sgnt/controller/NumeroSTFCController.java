package br.com.sgnt.controller;

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
import br.com.sgnt.model.TipoNumero;
import br.com.sgnt.repository.AreaLocalRepository;
import br.com.sgnt.repository.NumeroSTFCRepository;
import br.com.sgnt.repository.TipoNumeroRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroSTFCController {

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

	private List<AreaLocal> listCN;
	private List<AreaLocal> listAreaLocal;
	private List<NumeroSTFC> listNumeroSTFC;
	private List<NumeroSTFC> listNumeroSTFCCorporativo = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroSTFCResidencial = new ArrayList<NumeroSTFC>();
	private Integer cnSelecionado;
	private String area;
	private Integer faixaInicial, faixaFinal;
	private List<NumeroSTFC> lista = new ArrayList<NumeroSTFC>();

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
		while (faixaInicial <= faixaFinal) {
			numeroSTFC = new NumeroSTFC();
			numeroSTFC.setAreaLocal(areaLocal);
			numeroSTFC.setTipoNumero(tipo);
			numeroSTFC.setPrefixoNumeroSTFC(prefixo);
			numeroSTFC.setMcduNumeroSTFC(faixaInicial);

			try {
				numeroSTFCRepository.save(numeroSTFC);
			} catch (Exception e) {
				mcdu.add(faixaInicial);
			}

			faixaInicial++;
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

	public void teste() {
		System.out.println(area);
	}

	public void carregaAreaLocal() {
		listAreaLocal = areaLocalRepository.listAreaLocalCN(cnSelecionado);

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

}
