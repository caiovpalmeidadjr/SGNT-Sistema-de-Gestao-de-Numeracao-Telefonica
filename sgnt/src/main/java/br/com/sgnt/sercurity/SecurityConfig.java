package br.com.sgnt.sercurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.accept.ContentNegotiationStrategy;

import br.com.sgnt.model.Usuario;
import br.com.sgnt.repository.UsuarioRepository;



@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//configurando pagina a ser redirecionada após o login
		http.userDetailsService(userDetailsService())
			.formLogin().loginPage("/login/login.xhtml").permitAll()
			.defaultSuccessUrl("/index/index.xhtml").and()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/login/login.css").permitAll()
			.antMatchers("/index/index.xhtml")
			.hasAnyRole("ADMIN")
			.anyRequest().authenticated();
		
	}

	@Override
	protected UserDetailsService userDetailsService() {
		
//		UserDetails user1 = new User("caio","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
//		UserDetails user2 = new User("admin","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
//		UserDetails user3 = new User("admTI","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADM_TI"));
//		UserDetails user4 = new User("admNumeracao","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADM_NUMERACAO"));
//		UserDetails user5 = new User("gerenteConta","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_GERENTE_CONTA"));
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		List<UserDetails> users = new ArrayList<>();
		
		for(Usuario user: usuarios) {
			UserDetails userDetail = new User(user.getUserName(),user.getSenha(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.getPerfil().getNome()));
			users.add(userDetail);
		}
		
		//passando uma lista de usuarios
		return new InMemoryUserDetailsManager(users);
				
//		//passando um array com os dois usuarios
//		return new InMemoryUserDetailsManager(Arrays.asList(user1, user2, user3, user4, user5));
	}
	
	@Override
	public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
		super.setContentNegotationStrategy(contentNegotiationStrategy);
	}
}
