package service;

import static service.Constants.JSON_MEDIA_TYPE;

import java.sql.Time;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.gov.frameworkdemoiselle.security.LoggedIn;
import business.RouteBC;
import business.UserBC;
import entity.Coordinate;
import entity.Route;
import entity.Weekday;

@Path("/route")
public class RouteService {

	@Inject
	private RouteBC routeBC;
	
	@Inject
	private UserBC userBC;

	@PUT
	@LoggedIn
	@Consumes(JSON_MEDIA_TYPE)
	public void create(Route route) throws Exception {
		routeBC.insert(route, userBC.getCurrentUser());
	}

	@DELETE
	@Path("/{id}")
	@LoggedIn
	@Produces(JSON_MEDIA_TYPE)
	public void delete(@PathParam("id") Integer id) throws Exception {
		Route route = new Route();
		route.setId(id);
		routeBC.delete(route);
	}

	@GET
	@Path("/{id}")
	@LoggedIn
	@Produces(JSON_MEDIA_TYPE)
	public Route load(@PathParam("id") Integer id) throws Exception {
		return routeBC.load(id);
	}

	@GET
	@LoggedIn
	@Produces(JSON_MEDIA_TYPE)
	public List<Route> find(@QueryParam("lat") Double latitude, @QueryParam("lng") Double longitude,
			@QueryParam("radius") Integer radius, @QueryParam("weekday") Integer weekday,
			@QueryParam("hourini") Time hourini, @QueryParam("hourend") Time hourend) throws Exception {

		List<Route> routes;

		boolean empty = true;
		empty &= latitude == null;
		empty &= longitude == null;
		empty &= radius == null;
		empty &= weekday == null;
		empty &= hourini == null;
		empty &= hourend == null;

		if (empty) {
			routes = routeBC.find(userBC.getCurrentUser());
		} else {
			routes = routeBC.find(new Coordinate(latitude, longitude), radius, userBC.getCurrentUser(), Weekday.valueOf(weekday), hourini, hourend);
		}

		return routes;

	}

}
