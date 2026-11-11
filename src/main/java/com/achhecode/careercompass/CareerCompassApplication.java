package com.achhecode.careercompass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CareerCompassApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true"); // needed for JFreeChart on servers
        SpringApplication.run(CareerCompassApplication.class, args);
    }
}
