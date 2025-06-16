package com.ibeus.Comanda.Digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableTransactionManagement
@SpringBootApplication
public class ComandaDigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComandaDigitalApplication.class, args);
    }

    /**
     * Configurações de CORS para liberar requisições
     * vindas do Angular local e do deploy no Vercel.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                  .addMapping("/**")
                  .allowedOrigins(
                    "http://localhost:4200",
                    "https://comanda-digital-e8b8.onrender.com",
                    "https://dishapp-five.vercel.app"
                  )
                  .allowedMethods("*");
            }
        };
    }

}