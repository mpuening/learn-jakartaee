package io.github.learnjakartaee.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.github.learnjakartaee.AppInitializer;

@Configuration
@ComponentScan(basePackageClasses = AppInitializer.class)
public class ComponentsConfiguration {

}
