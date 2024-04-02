package org.example.dollar_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.example.dollar_front.feign")
public class DollarFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(DollarFrontApplication.class, args);
	}

}
