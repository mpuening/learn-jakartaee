package io.github.learnjakartaee.auth;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlStartingWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.xpath;

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

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.WebAppWarBuilder;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(OrderAnnotation.class)
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

	@BeforeAll
	public static void setup() {
		HtmlUnitDriver webDriver = new HtmlUnitDriver();
		webDriver.setJavascriptEnabled(true);
		WebDriverRunner.setWebDriver(webDriver);
	}

	@Test
	@Order(1)
	public void testArquillianBootedUp() {
		assertNotNull(baseURL);
	}

	@Test
	@Order(2)
	public void testLogin() {
		open(baseURL + "index.xhtml");
		// System.out.println(WebDriverRunner.source());

		// User get redirected to login page
		webdriver().shouldHave(urlStartingWith(baseURL.toString() + "views/auth/login.xhtml"));
		$(id("banner")).shouldHave(text("Welcome, please sign in..."));

		$(xpath("//input[contains(@id,':username')]")).clear();
		$(xpath("//input[contains(@id,':username')]")).sendKeys("alice");
		$(xpath("//input[contains(@id,':password')]")).clear();
		$(xpath("//input[contains(@id,':password')]")).sendKeys("password");
		$(xpath("//input[@value='Sign In']")).click();

		// Saved request is index page in this test case
		webdriver().shouldHave(urlStartingWith(baseURL + "index.xhtml"));
		$(id("banner")).shouldHave(text("Welcome"));

		// reload a few times, should stay on index.xthml because we are logged in
		open(baseURL + "index.xhtml");
		webdriver().shouldHave(urlStartingWith(baseURL.toString() + "index.xhtml"));
		$(id("banner")).shouldHave(text("Welcome"));

		open(baseURL + "index.xhtml");
		webdriver().shouldHave(urlStartingWith(baseURL.toString() + "index.xhtml"));
		$(id("banner")).shouldHave(text("Welcome"));
	}
}
