package br.com.sgnt.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.sgnt.model.Cliente;

//rodando o teste com o spring executando os testes 
//a classe vai testar utilizando a tecnica do JPA, ele testa, salva, executa o rollback e não contaminha o banco
@RunWith(SpringRunner.class)
@DataJpaTest // utilizando um banco interno onde sera utilizado para nossos testes
@AutoConfigureTestDatabase(replace=Replace.NONE) //utilizando nosso dataSource
public class TestClienteRepository  {
	
	//injetar uma dependencia, o spring constroi um objeto  e coloca o mesmo construido na propriedade
	//Objeto criado pelo Spring com todos os metodos 
	@Autowired
	private ClienteRepository clienteRepository;
	
	//fornencento o mecanismo de gerenciar entidades
	@Autowired
	EntityManager entity;
	
	@Test
	public void testSalvar() {
		
		//executando teste de inserir um usuario no banco mas no final do rollback
		//ao executar o teste eu crio todas as tabelas mapeadas no banco postgres 
		Cliente cli = new Cliente("Caio","caio@caio.com");
		clienteRepository.save(cli);
		
		Assert.assertNotNull(cli.getId());
	}
	
	@Test
	public void testBuscarPorEmail() {
		Cliente cli = new Cliente("Caio","caio@caio.com");
		clienteRepository.save(cli);
		
		//usando o manager pois o buscar por e-mail não pode ter dependencia de um metodo para funcionar o outro, por isso usamos o entity para salvar
		entity.persist(cli);
		
		Cliente cliEncontrado = clienteRepository.buscarPorEmail("caio@caio.com");
		assertThat(cliEncontrado.getNome().equals(cli.getNome()));
	}
	
	@Test
	public void testBuscarTodos() {
		Cliente cli = new Cliente("Caio","caio@caio.com");
		
		Cliente cli2 = new Cliente("Caio000","caio@caio000.com");
		
		entity.persist(cli2);
		
		entity.persist(cli);
		
		List<Cliente> listCliente = clienteRepository.listCliente();
		
		assertThat(listCliente.get(0).getNome().equals(cli.getNome()));
		
		assertThat(listCliente.get(1).getNome().equals(cli2.getNome()));
		
		//aula 3 parte 2 minuto 5:15
	}
	
}
