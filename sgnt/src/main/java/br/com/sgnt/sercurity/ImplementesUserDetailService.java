package br.com.sgnt.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.UsuarioRepository;

@Repository
@Transactional
public class ImplementesUserDetailService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user = usuarioRepository.buscaPorUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		return new User(user.getUsername(), user.getPassword(),true,true,true,true, user.getAuthorities() ) ;
	}

	

}
