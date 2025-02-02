package org.example.bibliotecaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BibliotecaSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaSpringBootApplication.class, args);
    }
}
