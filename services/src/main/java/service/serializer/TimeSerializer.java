package service.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class TimeSerializer extends JsonSerializer<Date> {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

	@Override
	public void serialize(Date time, JsonGenerator gen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		
		gen.writeString(dateFormat.format(time));
	}

}