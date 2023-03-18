package io.github.learnjakartaee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("index.jsp")
public class HomePage {

	@FindBy(id = "banner")
	private WebElement banner;

	public void assertPageLoaded() {
		assertEquals("Welcome", banner.getText());
	}

}
