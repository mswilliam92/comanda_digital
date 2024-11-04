package com.ibeus.Comanda.Digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement

@SpringBootApplication
public class ComandaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComandaDigitalApplication.class, args);
	}

}
