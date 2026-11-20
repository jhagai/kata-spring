package com.jhagai.kata_spring_jhagai;

import org.springframework.boot.SpringApplication;

public class TestKataSpringJhagaiApplication {

	public static void main(String[] args) {
		SpringApplication.from(KataSpringJhagaiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
