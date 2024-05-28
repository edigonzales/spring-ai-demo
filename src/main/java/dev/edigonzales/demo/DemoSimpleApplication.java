package dev.edigonzales.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import dev.edigonzales.demo.functions.WeatherConfigProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class DemoSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSimpleApplication.class, args);
	}

}
