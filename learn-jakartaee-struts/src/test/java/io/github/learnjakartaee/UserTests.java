package io.github.learnjakartaee;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlStartingWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;
import static org.openqa.selenium.By.partialLinkText;
import static org.openqa.selenium.By.tagName;
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
public class UserTests {

	@Deployment
	public static WebArchive createTestDeployment() {

		System.setProperty(Environment.PROFILES_PROPERTY_NAME, "unittest");

		return new WebAppWarBuilder("learn-jakartaee-struts.war")
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
		$(className("navbar-brand")).shouldHave(text("Learn Jakarta EE Struts"));

		// Click Sign Out to ensure we are logged out
		$(id("signOutButton")).click();
		$(tagName("body")).shouldHave(text("You have been logged out"));

		// Open protected page, make sure login page displays.
		$(partialLinkText("Sign In")).click();
		$(id("loginMessage")).shouldHave(text("Please sign in..."));

		$(xpath("//input[contains(@id,'username')]")).clear();
		$(xpath("//input[contains(@id,'username')]")).sendKeys("alice");
		$(xpath("//input[contains(@id,'password')]")).clear();
		$(xpath("//input[contains(@id,'password')]")).sendKeys("password");
		$(xpath("//button[@value='login']")).click();

		// Saved request is index page in this test case
		webdriver().shouldHave(urlStartingWith(baseURL + "index.jsp"));
		$(id("banner")).shouldHave(text("Welcome"));
	}

	@Test
	@Order(3)
	public void testMainLink() {
		$(partialLinkText("Main")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "aircraft.do"));
		$(tagName("body")).shouldHave(text("Aircraft Listing"));
	}

	@Test
	@Order(4)
	public void testAdminLink() {
		// alice does not have access, only admin
		$(partialLinkText("Admin")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "events.do"));
		$(tagName("body")).shouldHave(text("Access Denied"));
	}

	@Test
	@Order(5)
	public void testSignOut() {
		$(id("signOutButton")).click();
		webdriver().shouldHave(urlStartingWith(baseURL + "logout.do"));
		$(tagName("body")).shouldHave(text("You have been logged out"));

		// Re-open hello page should display login page
		open(baseURL);
		$(partialLinkText("Main")).click();
		$(id("loginMessage")).shouldHave(text("Please sign in..."));
	}
}
