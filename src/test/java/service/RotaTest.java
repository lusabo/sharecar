package service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import business.RotaBC;

@RunWith(DemoiselleRunner.class)
public class RotaTest {

	@Inject
	RotaBC rotaBC;

	@Test
	@SuppressWarnings("unchecked")
	public void testInsert() throws JsonParseException, JsonMappingException, IOException {
		assertEquals("true", "true");

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map = mapper.readValue("{\"nome\": \"valor\"}", map.getClass());

		System.out.println(map.get("nome"));
	}
}
