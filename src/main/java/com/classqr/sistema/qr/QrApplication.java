package com.classqr.sistema.qr;

import com.classqr.sistema.commons.configuration.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(
		basePackages = "com.classqr.sistema"
)
@Import(SpringSecurityConfig.class)
public class QrApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrApplication.class, args);
	}

}
