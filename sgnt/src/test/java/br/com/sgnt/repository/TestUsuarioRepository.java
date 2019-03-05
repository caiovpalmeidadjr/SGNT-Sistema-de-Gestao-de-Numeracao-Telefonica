package br.com.sgnt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sgnt.model.Usuario;

@RunWith(SpringRunner.class)
@DataJpaTest // utilizando um banco interno onde sera utilizado para nossos testes
@AutoConfigureTestDatabase(replace=Replace.NONE) //utilizando nosso dataSource
public class TestUsuarioRepository {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testarLogin() {
		
		Usuario teste = new Usuario();
		teste.setUserName("teste");
		
		Usuario usuario = usuarioRepository.buscaPorUsername(teste.getUserName());
		System.out.println(usuario.toString());
		
	}
	
}
