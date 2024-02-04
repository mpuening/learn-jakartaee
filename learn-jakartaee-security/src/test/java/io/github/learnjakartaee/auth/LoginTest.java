package io.github.learnjakartaee.auth;

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

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.WebAppWarBuilder;

@ExtendWith(ArquillianExtension.class)
public class LoginTest {

	@Deployment
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unittest");

		return new WebAppWarBuilder("learn-jakartaee-security.war")
				.packages("io.github.learnjakartaee")
				.xhtmls()
				.css()
				.xmls()
				.webXml()
				.beansXml()
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

		indexPage.assertLoginPageLoaded(driver);
		indexPage.loginWithFakeUser(driver);
		indexPage.assertLoginPageLoaded(driver);
		
		indexPage.loginWithTestUser(driver);
		indexPage.assertIndexPageLoaded(driver);
	}
}
