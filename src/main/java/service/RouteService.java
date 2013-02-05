package service;

import static java.util.Arrays.asList;
import static service.Constants.JSON_MEDIA_TYPE;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import business.RouteBC;
import entity.Coordenada;
import entity.Route;
import entity.User;

@Path("/route")
public class RouteService {

	@Inject
	private RouteBC routeBC;

	@PUT
	@Consumes(JSON_MEDIA_TYPE)
	public void create(Route route) {
		routeBC.insert(route);
	}

	@POST
	@Path("/{id}")
	@Consumes(JSON_MEDIA_TYPE)
	public void update(@PathParam("id") Integer id, Route route) {
		routeBC.insert(route);
	}

	@GET
	@Produces(JSON_MEDIA_TYPE)
	public List<Route> findAll() {
		return null;
	}
	
	@GET
	@Path("/{id}")
	@Produces(JSON_MEDIA_TYPE)
	public Route find(@PathParam("id") Integer id) {
		return routeBC.load(id);
	}	
}
