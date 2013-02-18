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

import br.gov.frameworkdemoiselle.internal.configuration.JDBCConfig;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.gov.frameworkdemoiselle.util.Beans;
import business.RouteBC;

@RunWith(DemoiselleRunner.class)
public class RotaTest {

	@Inject
	private RouteBC rotaBC;

	// @Test
	// public void xx() {
	// XConfig config = Beans.getReference(XConfig.class);
	//
	// Map<String, String> jndi = config.getJndiName();
	//
	// // DataConfiguration
	//
	// for (String key : jndi.keySet()) {
	// System.out.println(key + ": " + jndi.get(key));
	// }
	//
	// // String[] xx= config.getX();
	// //
	// // for(String x : xx) {
	// // System.out.println(x);
	// // }
	//
	// }

	class H {

		private String x;

		public H(String x) {
			this.x = x;
		}

		public String getX() {
			return x;
		}

		public void setX(String x) {
			this.x = x;
		}
	}

	@Test
	public void xxxxxx() {
		JDBCConfig config = Beans.getReference(JDBCConfig.class);
		Map<String, String> map = config.getJndiName();

		// System.out.println(map);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testInsert() throws JsonParseException, JsonMappingException, IOException {
		assertEquals("true", "true");

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map = mapper.readValue("{\"nome\": \"valor\"}", map.getClass());

		// System.out.println(map.get("nome"));
	}
}
