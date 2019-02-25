package br.com.sgnt.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

//rodando o teste com o spring executando os testes 
//a classe vai testar utilizando a tecnica do JPA, ele testa, salva, executa o rollback e não contaminha o banco
@RunWith(SpringRunner.class)
@DataJpaTest
public class TestClienteRepository  {
	
	//injetar uma dependencia, o spring constroi um objeto  e coloca o mesmo construido na propriedade
	//Objeto criado pelo Spring com todos os metodos 
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	public void testSalvar() {
		
	}
	
}
