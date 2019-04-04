package br.com.sgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void salvar(Usuario usuario) {
		usuarioRepository.save(usuario);

	}

	@Override
	public void atualizar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	public void excluir(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}

	@Override
	public List<Usuario> listUsuarios(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> listUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscaPorUsername(String user) {
		return usuarioRepository.buscaPorUsername(user);
	}

	@Override
	public Usuario getUsuario(String userName, String senha) {
		return usuarioRepository.getUsuario(userName, senha);
	}

}
