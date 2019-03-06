package br.com.sgnt.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgnt.model.Cliente;
import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.ClienteRepository;
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

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void cadastrar() {
		usuarioRepository.save(usuario);
	}
	
	public String testarAcesso() {
		
		Usuario permissaoUsuario = new Usuario();
		
		try {
			permissaoUsuario = usuarioRepository.buscaPorUsername(usuario.getUserName());
			
			if(permissaoUsuario.getUserName().equals(usuario.getUserName()) && permissaoUsuario.getSenha().equals(usuario.getSenha())) {
				return "index?faces-redirect=true";
			} else if (permissaoUsuario.getUserName().equals(usuario.getUserName()) && usuario.getSenha()=="") {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Informe a senha", "Erro no login"));
			} else if (permissaoUsuario.getUserName().equals(usuario.getUserName()) && !permissaoUsuario.getSenha().equals(usuario.getSenha())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha incorreta", "Erro no login"));
			}
						
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario não encontrado", "Erro no Login"));
			e.printStackTrace();
		}
		
		return "";
	}
	
}
