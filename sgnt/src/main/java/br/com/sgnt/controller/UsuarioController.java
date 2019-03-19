package br.com.sgnt.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.Funcionario;
import br.com.sgnt.model.Perfil;
import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.FuncionarioRepository;
import br.com.sgnt.repository.PerfilRepository;
import br.com.sgnt.repository.UsuarioRepository;

//dizendo que o meu controller é um bean que se comunica com a tela
@Named
@ViewScoped
public class UsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	private Perfil perfil = new Perfil();
	private Usuario usuario = new Usuario();
	private Funcionario funcionario = new Funcionario();
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String envia() {

		usuario = usuarioRepository.getUsuario(usuario.getUserName(), usuario.getSenha());
		if (usuario == null) {
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", "Erro no Login!"));
			return null;
		} else {
			
			try {
				//redirecionando para a tela index.xhtml
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
		
		Usuario u = usuarioRepository.buscaPorUsername(usuario.getUserName());
		
		if(u == null) {
			usuario.setPerfil(perfil);
			usuarioRepository.save(usuario);
			funcionario.setAtivo(true);
			funcionario.setUsuario(usuario);
			funcionarioRepository.save(funcionario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Usuário cadastrado!", "Cadastro efetuado"));
			funcionario = new Funcionario();
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
			permissaoUsuario = usuarioRepository.buscaPorUsername(usuario.getUserName());

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

}
