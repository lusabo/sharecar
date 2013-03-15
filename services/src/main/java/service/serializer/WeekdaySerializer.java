package service.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import entity.Weekday;

public class WeekdaySerializer extends JsonSerializer<Weekday> {

	@Override
	public void serialize(Weekday type, JsonGenerator gen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		gen.writeString(type.name().toLowerCase());
	}
}
