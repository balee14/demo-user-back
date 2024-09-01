package com.zero.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.terry.demo")
@EnableJpaRepositories(basePackages = "com.terry.demo")
@EntityScan(basePackages = "com.terry.demo")
public class DemoUserBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoUserBackApplication.class, args);
    }

}
