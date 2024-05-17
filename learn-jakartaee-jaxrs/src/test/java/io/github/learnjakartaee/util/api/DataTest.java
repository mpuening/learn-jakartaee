package io.github.learnjakartaee.util.api;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response.Status;

public class DataTest {

	@Test
	public void testStringData() {
		Data<String> data = Data.from("Hello");
		assertNotNull(data);
		String json = data.toJson();
		with(json)
				.assertThat("$.data", is("Hello"));
	}
	
	@Test
	public void testIntegerData() {
		Data<Integer> data = Data.from(123);
		assertNotNull(data);
		String json = data.toJson();
		with(json)
				.assertThat("$.data", is(123));
	}
	
	@Test
	public void testListData() {
		Data<List<String>> data = Data.from(List.of("A", "B", "C"));
		assertNotNull(data);
		String json = data.toJson();
		with(json)
				.assertThat("$.data[0]", is("A"))
				.assertThat("$.data[1]", is("B"))
				.assertThat("$.data[2]", is("C"));
	}
	
	@Test
	public void tesObjectData() {
		Data<ProblemDetail> data = Data.from(ProblemDetail.fromStatus(Status.OK));
		assertNotNull(data);
		String json = data.toJson();
		with(json)
				.assertThat("$.data.status", is(200))
				.assertThat("$.data.title", is("OK"));
	}
}
