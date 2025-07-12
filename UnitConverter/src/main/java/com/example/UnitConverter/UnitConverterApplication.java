package com.example.UnitConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UnitConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(UnitConverterApplication.class, args);
	}
}
