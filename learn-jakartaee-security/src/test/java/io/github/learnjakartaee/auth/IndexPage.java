package io.github.learnjakartaee.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Location("index.xhtml")
public class IndexPage {
   
	public void assertLoginPageLoaded(WebDriver driver) {
		// Since not logged in, login page displays
		WebElement message = driver.findElement(By.id("loginMessage"));
		assertEquals("Please sign in...", message.getText());
	}

	public void assertIndexPageLoaded(WebDriver driver) {
		// Now logged in, banner displays
		WebElement banner = driver.findElement(By.id("banner"));
		assertEquals("Welcome", banner.getText());
	}

	public void loginWithFakeUser(WebDriver driver) {
		login(driver, "fakeuser", "fakepassword");
	}

	public void loginWithTestUser(WebDriver driver) {
		// This matches TestCredentials
		login(driver, "admin", "password");
	}

	protected void login(WebDriver driver, String u, String p) {
		WebElement username = driver.findElement(By.cssSelector("input[type=\"text\"]"));
		WebElement password = driver.findElement(By.cssSelector("input[type=\"password\"]"));
		WebElement submit = driver.findElement(By.cssSelector("input[type=\"submit\"]"));

		username.sendKeys(u);
		password.sendKeys(p);
		submit.click();
	}
}
