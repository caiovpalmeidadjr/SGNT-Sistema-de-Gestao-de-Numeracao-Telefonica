package br.com.sgnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SgntApplication extends SpringBootServletInitializer{

	
	//retorna um objeto de configuração do spring
	//fazendo isso já vai estar ativado o modo web automaticamente
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SgntApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SgntApplication.class, args);
	}

}
