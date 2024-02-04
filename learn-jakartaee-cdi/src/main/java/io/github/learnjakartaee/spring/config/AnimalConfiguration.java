package io.github.learnjakartaee.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.learnjakartaee.shared.model.Animal;

@Configuration
public class AnimalConfiguration {

	@Value("${animal.classname}")
	String animalClassName;
	
	@Bean
	public Animal animal() throws Exception {
		return (Animal) Class.forName(animalClassName).getDeclaredConstructor().newInstance();
	}
}
