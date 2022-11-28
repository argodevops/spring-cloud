package org.argo.javafoundations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaFoundationsApplication {

	public static void main(String[] args) {
		Demonstration d = new Demonstration("gewibge");
		d.main();
		SpringApplication.run(JavaFoundationsApplication.class, args);
	}

}
