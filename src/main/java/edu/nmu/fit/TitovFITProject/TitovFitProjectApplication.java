package edu.nmu.fit.TitovFITProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TitovFitProjectApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", "9091");
		SpringApplication.run(TitovFitProjectApplication.class, args);
	}

}
