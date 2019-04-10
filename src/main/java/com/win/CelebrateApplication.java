package com.win;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class CelebrateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CelebrateApplication.class, args);
	}

}

