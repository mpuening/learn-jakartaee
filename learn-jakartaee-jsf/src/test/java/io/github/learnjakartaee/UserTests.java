package io.github.learnjakartaee;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.partialLinkText;
import static org.openqa.selenium.By.tagName;
import static org.openqa.selenium.By.xpath;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlStartingWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.codeborne.selenide.WebDriverRunner;

import io.github.learnjakartaee.env.Environment;
import io.github.learnjakartaee.test.WebAppWarBuilder;

@ExtendWith(ArquillianExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserTests {

	@Deployment
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unittest");

		return new WebAppWarBuilder("learn-jakartaee-jsf.war")
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
		open(baseURL);
		// System.out.println(WebDriverRunner.source());
		webdriver().shouldHave(urlStartingWith(baseURL.toString()));
		$(className("navbar-brand")).shouldHave(text("Learn Jakarta EE JSF"));

		// Click Sign Out to ensure we are logged out
		$(partialLinkText("Sign Out")).click();
		$(tagName("body")).shouldHave(text("You have been logged out"));

		// Open protected page, make sure login page displays.
		$(partialLinkText("Sign In")).click();
		$(id("loginMessage")).shouldHave(text("Please sign in..."));

		$(xpath("//input[contains(@id,':username')]")).clear();
		$(xpath("//input[contains(@id,':username')]")).sendKeys("alice");
		$(xpath("//input[contains(@id,':password')]")).clear();
		$(xpath("//input[contains(@id,':password')]")).sendKeys("password");
		$(xpath("//input[@value='Sign In']")).click();

		// Saved request is index page in this test case
		webdriver().shouldHave(urlStartingWith(baseURL + "index.xhtml"));
		$(id("banner")).shouldHave(text("Welcome"));
	}

	@Test
	@Order(3)
	public void testMainLink() {
		$(partialLinkText("Main")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "views/main/aircraft.xhtml"));
		$(tagName("body")).shouldHave(text("Welcome alice"));
	}

	@Test
	@Order(4)
	public void testAdminLink() {
		// alice does not have access, only admin
		$(partialLinkText("Admin")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "views/admin/events.xhtml"));
		$(tagName("body")).shouldHave(text("Access Denied"));
	}

	@Test
	@Order(5)
	public void testSignOut() {
		$(partialLinkText("Sign Out")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "views/auth/logged-out.xhtml"));
		$(tagName("body")).shouldHave(text("You have been logged out"));

		// Re-open hello page should display login page
		open(baseURL + "views/main/aircraft.xhtml");
		$(id("loginMessage")).shouldHave(text("Please sign in..."));
	}
}
