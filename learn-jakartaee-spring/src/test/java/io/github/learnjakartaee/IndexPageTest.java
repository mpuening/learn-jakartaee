package io.github.learnjakartaee;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlStartingWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.By.id;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.codeborne.selenide.WebDriverRunner;

import io.github.learnjakartaee.test.WebAppWarBuilder;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class IndexPageTest {

	@Deployment
	public static WebArchive createTestDeployment() {		
		return new WebAppWarBuilder("learn-jakartaee-jpa.war")
				.packages("io.github.learnjakartaee")
				.htmls()
				.webXml()
				.mavenDependencies()
				.build();
	}

	@ArquillianResource
	private URL baseURL;

	@BeforeAll
	public static void setup() {
		HtmlUnitDriver webDriver = new HtmlUnitDriver();
		webDriver.setJavascriptEnabled(true);
		WebDriverRunner.setWebDriver(webDriver);
	}

	@Test
	public void testArquillianBootedUp() {
		assertNotNull(baseURL);
	}

	@Test
	@Order(2)
	public void testHomePage() {
		open(baseURL);
		// System.out.println(WebDriverRunner.source());
		webdriver().shouldHave(urlStartingWith(baseURL.toString()));
		$(id("message")).shouldHave(text("Hello, thank you for visiting..."));
	}
}
