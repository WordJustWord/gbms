package com.tongtech.mnc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class MessageNotificationCenterApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MessageNotificationCenterApp.class);
        springApplication.run(args);
    }
}
