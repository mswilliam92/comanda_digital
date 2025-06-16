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

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          // durante o teste, libera qualquer origem:
          .allowedOrigins("*")
          // ou, se quiser só liberar sub-domínios:
          // .allowedOriginPatterns("https://*.vercel.app", "https://*.onrender.com")
          .allowedMethods("*");
      }
    };
  }
}