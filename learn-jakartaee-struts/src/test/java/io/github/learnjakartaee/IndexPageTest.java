package io.github.learnjakartaee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import io.github.learnjakartaee.test.WebAppWarBuilder;

/**
 * When running within Eclipse/IntelliJ, make sure to add VM args:
 *
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * --add-opens=java.base/java.io=ALL-UNNAMED
 * --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED
 *
 * In Eclipse, the JRE configuration had a field for default VM args, so you
 * need only to set it once and apply it for all test cases.
 */
@ExtendWith(ArquillianExtension.class)
public class IndexPageTest {

	@Deployment
	public static WebArchive createTestDeployment() {
		return new WebAppWarBuilder("learn-jakartaee-jpa.war")
				.packages("io.github.learnjakartaee")
				.jsps()
				.css()
				.xmls()
				.webXml()
				.mavenDependencies()
				.build();
	}

	@ArquillianResource
	private URL baseURL;

	@Drone
	private WebDriver driver;

	@Test
	public void testArquillianBootedUp() {
		assertNotNull(baseURL);
		assertNotNull(driver);
	}

	@Test
	public void testHomePage() {
		IndexPage indexPage = Graphene.goTo(IndexPage.class);
		indexPage.assertPageLoaded();
	}
}
