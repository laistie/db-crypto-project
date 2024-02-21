package com.crypto;

import com.crypto.service.MercadoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CryptoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CryptoApplication.class, args);
        try (context) {
            MercadoService mercadoService = context.getBean(MercadoService.class);
            mercadoService.coletarDadosDaAPI(); // Coleta dados dos mercados
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
