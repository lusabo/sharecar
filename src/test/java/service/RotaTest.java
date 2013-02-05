package service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import business.RouteBC;

@RunWith(DemoiselleRunner.class)
public class RotaTest {

	@Inject
	RouteBC rotaBC;
	
	@Test
	public void testInsert() {
		assertEquals("true", "true");
	}

}
