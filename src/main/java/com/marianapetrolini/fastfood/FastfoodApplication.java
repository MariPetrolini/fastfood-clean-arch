package com.marianapetrolini.fastfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação FastFood.
 * Configurada com Clean Architecture.
 */
@SpringBootApplication
public class FastfoodApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FastfoodApplication.class, args);
    }
}

