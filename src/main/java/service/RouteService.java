package service;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import business.RotaBC;
import entity.Coordenada;
import entity.Route;
import entity.Usuario;

@Path("/route")
public class RouteService {

	@Inject
	private RotaBC routeBC;

	@PUT
	@Consumes("application/json;charset=UTF-8")
	public void create(Route route) {
		routeBC.salvar(route);
	}

	@POST
	@Path("/{id}")
	@Consumes("application/json;charset=UTF-8")
	public void update(@FormParam("id") String id, Route route) {
		routeBC.salvar(route);
	}

	@GET
	@Produces("application/json;charset=UTF-8")
	public List<Route> findAll() {
		List<Route> routes = new ArrayList<Route>();

		routes.add(new Route("rota 1", asList(new Coordenada(1.1, 2.2), new Coordenada(0.8, 1.3)), new Usuario(
				"111.111.111-11")));
		routes.add(new Route("rota 3", asList(new Coordenada(6.8, 2.1), new Coordenada(99.3, 100021.3777)),
				new Usuario("111.111.111-11")));
		routes.add(new Route("rota 4", asList(new Coordenada(81.121, 589.0), new Coordenada(0.143, 3777.234)),
				new Usuario("111.111.111-11")));

		return routes;
	}
}
