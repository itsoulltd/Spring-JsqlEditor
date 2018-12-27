package com.itsoul.lab.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.itsoul.lab.app.comnponents"})
public class SpringJsqlEditorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJsqlEditorApplication.class, args);
    }
}
