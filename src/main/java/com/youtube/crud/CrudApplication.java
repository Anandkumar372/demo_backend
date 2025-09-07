package com.youtube.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class CrudApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CrudApplication.class, args);
//	}
//
//}

@SpringBootApplication
public class CrudApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CrudApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }
}
