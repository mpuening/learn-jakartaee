package io.github.learnjakartaee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("index.xhtml")
public class HomePage {

	@FindBy(id = "loginMessage")
	private WebElement message;

	public void assertPageLoaded() {
		// Since not logged in, login page displays
		assertEquals("Please sign in...", message.getText());
	}

}
