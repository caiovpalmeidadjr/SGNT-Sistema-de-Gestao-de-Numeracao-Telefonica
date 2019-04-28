package br.com.sgnt.controller;

import java.io.Serializable;
import java.sql.Timestamp;
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
import br.com.sgnt.model.ClienteCorporativo;
import br.com.sgnt.model.NumeroSTFC;
import br.com.sgnt.model.Reserva;
import br.com.sgnt.model.Status;
import br.com.sgnt.model.TipoNumero;
import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.AreaLocalRepository;
import br.com.sgnt.repository.NumeroSTFCRepository;
import br.com.sgnt.repository.ReservaRepository;
import br.com.sgnt.repository.StatusRepository;
import br.com.sgnt.repository.TipoNumeroRepository;
import br.com.sgnt.service.IAreaLocalService;
import br.com.sgnt.service.INumeroSTFCService;
import br.com.sgnt.service.IReservaService;
import br.com.sgnt.service.IStatusService;
import br.com.sgnt.service.ITipoNumeroService;
import br.com.sgnt.service.IUsuarioService;
import br.com.sgnt.service.ReservaServiceImpl;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class NumeroSTFCController implements Serializable {

	private NumeroSTFC numeroSTFC = new NumeroSTFC();
	private AreaLocalController areaLocalController;

	private AreaLocal areaLocal = new AreaLocal();


	private TipoNumero tipo = new TipoNumero();
	
	private Status status = new Status();
	
	@Autowired
	private INumeroSTFCService numeroSTFCService;
	
	@Autowired
	private IStatusService statusService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ITipoNumeroService tipoService;
	
	@Autowired
	private IReservaService reservaService;
	
	@Autowired
	private IAreaLocalService areaLocalService;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	private List<AreaLocal> listCN;
	private List<AreaLocal> listAreaLocal;
	private List<NumeroSTFC> listNumeroSTFC, listNumeroReserva;
	private List<NumeroSTFC> listNumeroSTFCCorporativo = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroSTFCCorporativoDisponivel = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroSTFCResidencialDisponivel = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroSTFCResidencial = new ArrayList<NumeroSTFC>();
	private List<NumeroSTFC> listNumeroCorporativoSelecionado, listNumeroResidencialSelecionado;
	private List<Integer> listPrefixoCorporativo, listPrefixoResidencial;
	private List<NumeroSTFC> listNumeroRelatorio = new ArrayList<NumeroSTFC>();
	private double qtdeDisponivel, qtdeAtivado, qtdeReservado, qtdeDesativado;
	private double taxaUtilizacao;
	private Integer cnSelecionado, idReserva;
	private Integer prefixo, qtdeMCDUOk;
	private String area;
	private Integer faixaInicial, faixaFinal, tempInicial, tempFinal;
	private List<NumeroSTFC> lista = new ArrayList<NumeroSTFC>();
	private List<Integer> mcduOk = new ArrayList<Integer>();
	private List<Integer> mcduErro = new ArrayList<Integer>();
	private Reserva reserva = new Reserva();

	@PostConstruct
	private void init() {
		listCN = areaLocalService.listDistinctCN();
		Collections.sort(listCN);
		listNumeroSTFC = numeroSTFCService.listNumerosSTFC();
		listNumeroSTFCCorporativo = numeroSTFCService.listNumeroTipo(tipoService.findOne(1));
		listNumeroSTFCResidencial = numeroSTFCService.listNumeroTipo(tipoService.findOne(2));
		listNumeroSTFCCorporativoDisponivel = numeroSTFCService.listNumeroDisponivel(tipoService.findOne(1), statusService.findOne(1));
		System.out.println(statusService.findOne(1));
		listNumeroSTFCResidencialDisponivel = numeroSTFCService.listNumeroDisponivel(tipoService.findOne(2), statusService.findOne(1));
	}

	public void cadastrar(String nomeTipo) {
		areaLocal = areaLocalService.areaLocalNome(area);
		tipo = tipoService.pesquisaNome(nomeTipo);
		Timestamp data = new Timestamp(System.currentTimeMillis());
		numeroSTFC.setDataHoraStatus(data);
		numeroSTFC.setTipoNumero(tipo);
		numeroSTFC.setAreaLocal(areaLocal);
		status = statusService.buscaPorNome("DISPONIVEL");
		boolean confereAL = verificaAL();
		if (confereAL == true) {
			Integer prefixo = numeroSTFC.getPrefixoNumeroSTFC();
			cadastraMCDU(prefixo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Prefixo cadastrado para outra área local", "Erro no cadastro!"));
		}
		
	}
	
	public boolean verificaAL() {
		lista = numeroSTFCService.getListaAreaLocal(numeroSTFC.getPrefixoNumeroSTFC());

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
					numeroSTFCService.salvar(numeroSTFC);
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
	
	public void reservarCorporativo() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		try {
		reserva.setDataHoraReserva(data);
		reserva.setUsuario(usuario);
		reserva = reservaRepository.save(reserva);
		
		for (int i = 0; i < listNumeroCorporativoSelecionado.size(); i++) {
			listNumeroCorporativoSelecionado.get(i).setReserva(reserva);
			listNumeroCorporativoSelecionado.get(i).setStatus(statusService.findOne(2));
			listNumeroCorporativoSelecionado.get(i).setDataHoraStatus(data);
			listNumeroSTFCCorporativoDisponivel.remove(listNumeroCorporativoSelecionado.get(i));
		}
		
		numeroSTFCService.salvarLista(listNumeroCorporativoSelecionado);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Reserva realizada", "Sucesso"));
		} catch (Exception e) {
			System.out.println("Deu erro aqui!");
		}
	
	}
	
	public void reservarResidencial() {
		Timestamp data = new Timestamp(System.currentTimeMillis());
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		
		reserva.setDataHoraReserva(data);
		reserva.setUsuario(usuario);
		reserva = reservaRepository.save(reserva);
		
		for (int i = 0; i < listNumeroResidencialSelecionado.size(); i++) {
			listNumeroResidencialSelecionado.get(i).setReserva(reserva);
			listNumeroResidencialSelecionado.get(i).setStatus(statusService.findOne(2));
			listNumeroResidencialSelecionado.get(i).setDataHoraStatus(data);
			listNumeroSTFCResidencialDisponivel.remove(listNumeroResidencialSelecionado.get(i));
		}
		
		//numeroSTFCRepository.save(listNumeroResidencialSelecionado);
		numeroSTFCService.salvarLista(listNumeroResidencialSelecionado);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Reserva realizada", "Sucesso"));
	
	}
	
	public void onCNChange() {
		listAreaLocal = areaLocalService.listAreaLocalCN(cnSelecionado);
	}

	public void onAreaLocalChange() {
		listPrefixoCorporativo = numeroSTFCService.listPrefixo(areaLocalService.areaLocalNome(area),tipoService.findOne(1));
		listPrefixoResidencial = numeroSTFCService.listPrefixo(areaLocalService.areaLocalNome(area),tipoService.findOne(2));

	}
	
	public void carregaReservado(Integer tipo) {
		try{
			reserva = reservaService.findOne(idReserva);
			listNumeroReserva = numeroSTFCService.findReserva(reserva, tipoService.findOne(tipo));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Informe o ID da reserva", null));
		}
		
	}
	
	public void excluirReserva() {
		int tam = listNumeroReserva.size();
		List<NumeroSTFC> listTemp = new ArrayList<NumeroSTFC>();
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		
		if(reserva.getUsuario().equals(usuario) || usuario.getPerfil().getIdPerfil().equals(1)) {
			for (int i=0; i<tam; i=i+1) {
				listNumeroReserva.get(i).setReserva(null);
				listNumeroReserva.get(i).setStatus(statusService.findOne(1));
				
				numeroSTFCService.salvar(listNumeroReserva.get(i));
				listTemp.add(listNumeroReserva.get(i));
			}
			listNumeroReserva.removeAll(listTemp);
			reservaService.excluir(reserva);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sem permissão para exclusão", null));
		}
		
	}
	
	public void revalidarReserva() {
		int tam = listNumeroReserva.size();
		Usuario usuario = new Usuario();
		SecurityController security = new SecurityController();
		usuario = usuarioService.buscaPorUsername(security.currentUserName());
		Timestamp data = new Timestamp(System.currentTimeMillis());
		
		if(reserva.getUsuario().equals(usuario) || usuario.getPerfil().getIdPerfil().equals(1)) {
			for (int i=0; i<tam; i=i+1) {
				listNumeroReserva.get(i).setDataHoraStatus(data);		
				numeroSTFCService.salvar(listNumeroReserva.get(i));
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Reserva validada!", null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Sem permissão para exclusão", null));
		}
		
	}
	
	public void quantidadeRelatorio() {
		areaLocal = areaLocalService.areaLocalNome(area);
		listNumeroRelatorio = numeroSTFCService.listNumeroAreaLocal(areaLocal);
		qtdeDisponivel = 0;
		qtdeAtivado = 0;
		qtdeReservado = 0;
		qtdeDesativado = 0;
		
		for (NumeroSTFC numeroSTFC : listNumeroRelatorio) {
			if(numeroSTFC.getStatus().getIdStatus().equals(1)) {
				qtdeDisponivel++;
			} else if (numeroSTFC.getStatus().getIdStatus().equals(2)) {
				qtdeAtivado++;
			} else if(numeroSTFC.getStatus().getIdStatus().equals(3)) {
				qtdeReservado++;
			} else if(numeroSTFC.getStatus().getIdStatus().equals(4)) {
				qtdeDesativado++;
			}
		}
		taxaUtilizacao = ((qtdeAtivado + qtdeReservado) / (qtdeDisponivel+qtdeAtivado+qtdeReservado+qtdeDesativado)) * 100;
		//taxaUtilizacao = (qtdeDisponivel+qtdeAtivado+qtdeReservado+qtdeDesativado);
		System.out.println(qtdeDisponivel + " - " + qtdeReservado + " - " + qtdeAtivado + " - " + qtdeDesativado );
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Integer> getListPrefixoCorporativo() {
		return listPrefixoCorporativo;
	}

	public void setListPrefixoCorporativo(List<Integer> listPrefixoCorporativo) {
		this.listPrefixoCorporativo = listPrefixoCorporativo;
	}

	public List<Integer> getListPrefixoResidencial() {
		return listPrefixoResidencial;
	}

	public void setListPrefixoResidencial(List<Integer> listPrefixoResidencial) {
		this.listPrefixoResidencial = listPrefixoResidencial;
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

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public List<NumeroSTFC> getListNumeroSTFCCorporativoDisponivel() {
		return listNumeroSTFCCorporativoDisponivel;
	}

	public void setListNumeroSTFCCorporativoDisponivel(List<NumeroSTFC> listNumeroSTFCCorporativoDisponivel) {
		this.listNumeroSTFCCorporativoDisponivel = listNumeroSTFCCorporativoDisponivel;
	}

	public List<NumeroSTFC> getListNumeroSTFCResidencialDisponivel() {
		return listNumeroSTFCResidencialDisponivel;
	}

	public void setListNumeroSTFCResidencialDisponivel(List<NumeroSTFC> listNumeroSTFCResidencialDisponivel) {
		this.listNumeroSTFCResidencialDisponivel = listNumeroSTFCResidencialDisponivel;
	}

	public List<NumeroSTFC> getListNumeroCorporativoSelecionado() {
		return listNumeroCorporativoSelecionado;
	}

	public void setListNumeroCorporativoSelecionado(List<NumeroSTFC> listNumeroCorporativoSelecionado) {
		this.listNumeroCorporativoSelecionado = listNumeroCorporativoSelecionado;
	}

	public List<NumeroSTFC> getListNumeroResidencialSelecionado() {
		return listNumeroResidencialSelecionado;
	}

	public void setListNumeroResidencialSelecionado(List<NumeroSTFC> listNumeroResidencialSelecionado) {
		this.listNumeroResidencialSelecionado = listNumeroResidencialSelecionado;
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public List<NumeroSTFC> getListNumeroReserva() {
		return listNumeroReserva;
	}

	public void setListNumeroReserva(List<NumeroSTFC> listNumeroReserva) {
		this.listNumeroReserva = listNumeroReserva;
	}

	public List<NumeroSTFC> getListNumeroRelatorio() {
		return listNumeroRelatorio;
	}

	public void setListNumeroRelatorio(List<NumeroSTFC> listNumeroRelatorio) {
		this.listNumeroRelatorio = listNumeroRelatorio;
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
