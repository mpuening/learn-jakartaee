package io.github.learnjakartaee.util.api;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * A very loose interpretation of RFC 7807. (Work in progress)
 *
 * https://datatracker.ietf.org/doc/html/rfc7807
 *
 * QUESTIONABLE Why do some attributes not work? (e.g. hidden)
 */
@Schema
@JsonbTypeSerializer(ProblemDetail.ProblemDetailSerializer.class)
public class ProblemDetail {

	@SchemaProperty(name = "status", description = "HTTP Status Code")
	private int status;

	@SchemaProperty(name = "title", description = "Short Error Message")
	private String title;

	@SchemaProperty(name = "detail", description = "Detailed Error Message")
	private String detail;

	@SchemaProperty(name = "properties", hidden = true, description = "Varying other properties")
	private Map<String, Object> properties;

	protected ProblemDetail(int status, String title, String detail) {
		this.status = status;
		this.title = title;
		this.detail = detail;
	}

	public static ProblemDetail fromStatus(Response.Status status) {
		return new ProblemDetail(status.getStatusCode(), status.getReasonPhrase(), null);
	}

	public static ProblemDetail fromStatusAndDetail(Status status, String detail) {
		return new ProblemDetail(status.getStatusCode(), status.getReasonPhrase(), detail);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperty(String key, Object value) {
		if (this.properties == null) {
			this.properties = new LinkedHashMap<>();
		}
		this.properties.put(key, value);
	}

	public String toJson() {
		Jsonb jsonb = JsonbBuilder.create();
		return jsonb.toJson(this);
	}

	@Override
	public String toString() {
		return toJson();
	}

	public static class ProblemDetailSerializer implements JsonbSerializer<ProblemDetail> {

		@Override
		public void serialize(ProblemDetail pd, JsonGenerator generator, SerializationContext ctx) {
			generator.writeStartObject();
			generator.write("status", pd.status);
			if (!Objects.isNull(pd.title)) {
				generator.write("title", pd.title);
			}
			if (!Objects.isNull(pd.detail)) {
				generator.write("detail", pd.detail);
			}
			if (!Objects.isNull(pd.properties)) {
				pd.properties.forEach((k, v) -> {
					ctx.serialize(k, v, generator);
				});
			}
			generator.writeEnd();
		}
	}
}
