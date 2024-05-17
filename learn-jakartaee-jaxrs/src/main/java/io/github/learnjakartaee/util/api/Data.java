package io.github.learnjakartaee.util.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.annotation.JsonbTypeSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

/**
 * A very loose interpretation of the JSON API Spec. (Work in progress)
 *
 * https://jsonapi.org/
 */
@JsonbTypeSerializer(Data.DataSerializer.class)
public class Data<T> {

	private T data;

	protected Data(T data) {
		this.data = data;
	}

	public static <T> Data<T> from(T data) {
		return new Data<T>(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String toJson() {
		Jsonb jsonb = JsonbBuilder.create();
		return jsonb.toJson(this);
	}

	@Override
	public String toString() {
		return toJson();
	}

	public static class DataSerializer implements JsonbSerializer<Data<?>> {

		@Override
		public void serialize(Data<?> data, JsonGenerator generator, SerializationContext ctx) {
			generator.writeStartObject();
			ctx.serialize("data", data.data, generator);
			generator.writeEnd();
		}
	}
}
