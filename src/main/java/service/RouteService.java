package service;

import static service.Constants.JSON_MEDIA_TYPE;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import business.RouteBC;
import entity.Coordinate;
import entity.Route;

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
		return routeBC.findAll();
	}

	@GET
	@Path("/{id}")
	@Produces(JSON_MEDIA_TYPE)
	public Route find(@PathParam("id") Integer id) {
		return routeBC.load(id);
	}
	
	@GET
	@Produces(JSON_MEDIA_TYPE)
	public List<Route> find(@QueryParam("lat") Double latitude, @QueryParam("lng") Double longitude, @QueryParam("radius") Integer radius){
		return routeBC.find(new Coordinate(latitude, longitude), radius);
	}
}
