package io.github.learnjakartaee.util.api;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;

public class ProblemDetailTest {

	@Test
	public void testProblemDetail() {
		ProblemDetail pd = ProblemDetail.fromStatus(Response.Status.FORBIDDEN);
		assertNotNull(pd);
		String json = pd.toJson();
		with(json)
				.assertThat("$.status", is(403))
				.assertThat("$.title", is("Forbidden"))
				.assertNotDefined("$.detail");
	}

	@Test
	public void testProblemDetailWithDetails() {
		ProblemDetail pd = ProblemDetail.fromStatus(Response.Status.FORBIDDEN);
		pd.setDetail("You are not authorized to access this resource.");
		assertNotNull(pd);
		String json = pd.toJson();
		with(json)
				.assertThat("$.status", is(403))
				.assertThat("$.title", is("Forbidden"))
				.assertThat("$.detail", is("You are not authorized to access this resource."));
	}

	@Test
	public void testProblemDetailWithMoreDetails() {
		ProblemDetail pd = ProblemDetail.fromStatus(Response.Status.FORBIDDEN);
		pd.setDetail("You are not authorized to access this resource.");
		pd.setProperty("help", "Contact help desk for access request, code 12345.");
		assertNotNull(pd);
		String json = pd.toJson();
		with(json)
				.assertThat("$.status", is(403)).assertThat("$.title", is("Forbidden"))
				.assertThat("$.detail", is("You are not authorized to access this resource."))
				.assertThat("$.help", is("Contact help desk for access request, code 12345."));
	}

	@Test
	public void testProblemDetailWithNestedDetails() {
		ProblemDetail pd = ProblemDetail.fromStatus(Response.Status.INTERNAL_SERVER_ERROR);
		pd.setDetail("Sorry, the problem is not in your set.");
		pd.setProperty("help", "Contact help desk for access request, code 54321.");
		pd.setProperty("rootCause", ProblemDetail.fromStatus(Response.Status.FORBIDDEN));
		assertNotNull(pd);
		String json = pd.toJson();
		with(json)
				.assertThat("$.status", is(500))
				.assertThat("$.title", is("Internal Server Error"))
				.assertThat("$.detail", is("Sorry, the problem is not in your set."))
				.assertThat("$.rootCause.status", is(403))
				.assertThat("$.rootCause.title", is("Forbidden"));
	}
}
