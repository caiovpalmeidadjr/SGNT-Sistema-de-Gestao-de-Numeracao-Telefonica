package br.com.sgnt.sercurity;

import java.util.Arrays;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.accept.ContentNegotiationStrategy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//configurando pagina a ser redirecionada após o login
		http.userDetailsService(userDetailsService())
			.formLogin()
			.defaultSuccessUrl("/index.xhtml").and()
			.csrf()
			.disable()
			.authorizeRequests()
			.antMatchers("/index.xhtml")
			.hasAnyRole("ADMIN")
			.anyRequest().authenticated();
		
	}
	
	@Override
	protected UserDetailsService userDetailsService() {
		
		UserDetails user1 = new User("caio","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
		UserDetails user2 = new User("admin","1234", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
		
		//passando um array com os dois usuarios
		return new InMemoryUserDetailsManager(Arrays.asList(user1, user2));
	}
	
	@Override
	public void setContentNegotationStrategy(ContentNegotiationStrategy contentNegotiationStrategy) {
		// TODO Auto-generated method stub
		super.setContentNegotationStrategy(contentNegotiationStrategy);
	}
}
