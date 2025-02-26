package com.bitbucket.autoclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.bitbucket.autoclone"})
public class AutoCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoCloneApplication.class, args);
    }

}
