package io.github.learnjakartaee.config;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.enterprise.inject.Produces;

/**
 * https://db.apache.org/derby/docs/10.1/publishedapi/org/apache/derby/jdbc/EmbeddedDataSource.html
 */
@DataSourceDefinition(
		name = "java:app/env/jdbc/personDataSource",
		className = "org.apache.derby.jdbc.EmbeddedDataSource",
		databaseName = "memory:persondb",
		user = "APP",
		password = "",
		properties = {
				"createDatabase=create"
		})
@Singleton
public class DataSourceConfiguration {

	@Resource(lookup="java:app/env/jdbc/personDataSource")
	DataSource dataSource;
	
	@Produces
	public DataSource getDatasource() {
		return dataSource;
	}	

}
