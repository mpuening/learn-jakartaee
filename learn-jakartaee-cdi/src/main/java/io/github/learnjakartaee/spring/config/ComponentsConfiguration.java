package io.github.learnjakartaee.spring.config;

import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import io.github.learnjakartaee.spring.AppInitializer;
import jakarta.persistence.EntityManager;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = AppInitializer.class)
public class ComponentsConfiguration {

	@Bean
	public JtaTransactionManager jtaTransactionManager() {
		JtaTransactionManager txMgr = new JtaTransactionManager();
		return txMgr;
	}

	@Bean
	public EntityManager entityManager() throws NamingException {
		// JNDI named defined in web.xml
		Object entityManager = new JndiTemplate().lookup("java:comp/env/persistence/appPersistenceUnit");
		return (EntityManager) entityManager;
	}
}
