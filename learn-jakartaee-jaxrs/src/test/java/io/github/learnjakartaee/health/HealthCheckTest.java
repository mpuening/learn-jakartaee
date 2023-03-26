package io.github.learnjakartaee.health;

import static com.jayway.jsonassert.JsonAssert.collectionWithSize;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.learnjakartaee.BaseTestCase;
import jakarta.ws.rs.core.Response;

@ExtendWith(ArquillianExtension.class)
public class HealthCheckTest extends BaseTestCase {

	@Deployment
	public static WebArchive createTestDeployment() {
		return commonTestDeployment();
	}

	@Test
	public void testHealthStarted() {
		assertNotNull(apiTestClient);

		Response response = apiTestClient.healthStarted();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.status", is("UP"))
				.and()
				.assertThat("$.checks[0].name", is("Learn Jakarta EE Startup Check"))
				.and()
				.assertThat("$.checks[0].status", is("UP"));
	}

	@Test
	public void testHealthReady() {
		assertNotNull(apiTestClient);

		Response response = apiTestClient.healthReady();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.status", is("UP"))
				.and()
				.assertThat("$.checks[0].name", is("Learn Jakarta EE Ready Check"))
				.and()
				.assertThat("$.checks[0].status", is("UP"));
	}

	@Test
	public void testHealthLive() {
		assertNotNull(apiTestClient);

		Response response = apiTestClient.healthLive();
		assertEquals(200, response.getStatus());
		with(response.readEntity(String.class))
				.assertThat("$.status", is("UP"))
				.and()
				.assertThat("$.checks", is(collectionWithSize(equalTo(3))))
				.and()
				// Order of checks is random, but they all start the same
				.assertThat("$.checks[0].name", startsWith("Learn Jakarta EE"))
				.and()
				.assertThat("$.checks[0].status", is("UP"));
	}
}
