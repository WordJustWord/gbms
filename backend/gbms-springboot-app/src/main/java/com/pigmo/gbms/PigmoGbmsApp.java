package com.pigmo.gbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class PigmoGbmsApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PigmoGbmsApp.class);
        springApplication.run(args);
    }
}
