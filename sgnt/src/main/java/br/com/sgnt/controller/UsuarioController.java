package br.com.sgnt.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.sgnt.model.Perfil;
import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.UsuarioRepository;
import br.com.sgnt.service.IPerfilService;
import br.com.sgnt.service.IUsuarioService;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@Controller
public class UsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPerfilService perfilService;
	
	private Perfil perfil = new Perfil();
	private Usuario usuario = new Usuario();
	private List<Usuario> listUsuario;
	private Usuario usuarioSelecionado = new Usuario();
	private boolean alteracao;
	private Integer idPerfil;

	@PostConstruct
	public void init() {
		listUsuario = usuarioService.listUsuarios();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public boolean isAlteracao() {
		return alteracao;
	}

	public void setAlteracao(boolean alteracao) {
		this.alteracao = alteracao;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String envia() {

		usuario = usuarioService.getUsuario(usuario.getUserName(), usuario.getSenha());
		if (usuario == null) {
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", "Erro no Login!"));
			return null;
		} else {

			try {
				// redirecionando para a tela index.xhtml
				redirecionar();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;

		}

	}

	public void redirecionar() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext().redirect("/sgnt/index/index.xhtml");
	}

	public void cadastrar() {

		Usuario user = usuarioService.buscaPorUsername(usuario.getUserName());

		if (user == null) {
			perfil = perfilService.findOne(idPerfil);
			usuario.setPerfil(perfil);
			usuarioService.salvar(usuario);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado!", "Cadastro efetuado"));

			if (!alteracao) {
				listUsuario.add(usuario);
			}
			setAlteracao(false);
			usuario = new Usuario();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Usuário já existe no banco", "Cadastro não efetuado"));
		}

		/*
		
		*/
	}

	public String testarAcesso() {

		Usuario permissaoUsuario = new Usuario();

		try {
			permissaoUsuario = usuarioService.buscaPorUsername(usuario.getUserName());

			if (permissaoUsuario.getUserName().equals(usuario.getUserName())
					&& permissaoUsuario.getSenha().equals(usuario.getSenha())) {
				return "index?faces-redirect=true";
			} else if (permissaoUsuario.getUserName().equals(usuario.getUserName()) && usuario.getSenha() == "") {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Informe a senha", "Erro no login"));
			} else if (permissaoUsuario.getUserName().equals(usuario.getUserName())
					&& !permissaoUsuario.getSenha().equals(usuario.getSenha())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha incorreta", "Erro no login"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario não encontrado", "Erro no Login"));
			e.printStackTrace();
		}

		return "";
	}

	public void onRowEdit(RowEditEvent event) {
		Usuario u = ((Usuario) event.getObject());
		FacesMessage msg = new FacesMessage("Usuário alterado!", u.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
		usuarioService.salvar(u);
	}

	public void onRowCancel(RowEditEvent event) {
		Usuario u = ((Usuario) event.getObject());
		FacesMessage msg = new FacesMessage("Alteração cancelada", u.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void deletarUsuario() {
		usuarioService.excluir(usuarioSelecionado);
		listUsuario.remove(usuarioSelecionado);
		FacesMessage msg = new FacesMessage("Usuário deletado", usuarioSelecionado.getUserName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
