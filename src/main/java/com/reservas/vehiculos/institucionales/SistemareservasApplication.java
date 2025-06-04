package com.reservas.vehiculos.institucionales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching

public class SistemareservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemareservasApplication.class, args);
	}

}

