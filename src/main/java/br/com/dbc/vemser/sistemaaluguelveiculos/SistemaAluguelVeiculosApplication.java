package br.com.dbc.vemser.sistemaaluguelveiculos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class SistemaAluguelVeiculosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaAluguelVeiculosApplication.class, args);
    }

}
