package com.terry.demo.core.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
public class AppRunner implements ApplicationRunner {

    private final Environment environment;

    public AppRunner(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        LocalDateTime now = LocalDateTime.now();
        log.info("현재시간 " + now);
//        log.info("===================다중 프로파일 테스트===================");
//        log.info("Active profiles : "+ Arrays.toString(environment.getActiveProfiles()));
//        log.info("Server Port : " + environment.getProperty("server.port"));
//        log.info("Datasource driver : " + environment.getProperty("spring.datasource.driver-class-name"));
//        log.info("Datasource url : " + environment.getProperty("spring.datasource.url"));
//        log.info("Datasource username : " + environment.getProperty("spring.datasource.username"));
//        log.info("Datasource password : " + environment.getProperty("spring.datasource.password"));
//        log.info("====================================================");
    }
}
