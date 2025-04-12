package com.ibeus.Comanda.Digital;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ComandaDigitalApplicationTests {

	@Test
	void contextLoads() {
		// Esse teste verifica se o contexto do Spring é carregado com sucesso.
	}

	@Test
	void mainMethodRuns() {
		// Esse teste roda o método main para garantir que ele não lança exceções.
		ComandaDigitalApplication.main(new String[] {});
	}
}
