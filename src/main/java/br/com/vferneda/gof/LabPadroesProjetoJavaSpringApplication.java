package br.com.vferneda.gof;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LabPadroesProjetoJavaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabPadroesProjetoJavaSpringApplication.class, args);
    }

}
