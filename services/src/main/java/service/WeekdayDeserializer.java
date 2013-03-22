package service;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import entity.Weekday;

public class WeekdayDeserializer extends JsonDeserializer<Weekday> {

	@Override
	public Weekday deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return Weekday.valueOf(Integer.parseInt(jp.getText()));
	}

}
