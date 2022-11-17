package br.com.dbc.vemser.sistemaaluguelveiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SistemaAluguelVeiculosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAluguelVeiculosApplication.class, args);
    }

}
