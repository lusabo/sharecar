package service;

import static service.Constants.JSON_MEDIA_TYPE;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.Beans;
import business.RouteBC;
import entity.Coordinate;
import entity.Route;
import entity.User;

@Path("/route")
public class RouteService {

	@Inject
	private RouteBC routeBC;

	@PUT
	@Consumes(JSON_MEDIA_TYPE)
	public void create(Route route) throws Exception {
		routeBC.insert(route, getCurrentUser());
	}

//	@POST
//	@Path("/{id}")
//	@Consumes(JSON_MEDIA_TYPE)
//	public void update(@PathParam("id") Integer id, Route route) throws Exception {
//		routeBC.insert(route, getCurrentUser());
//	}

	@GET
	@Produces(JSON_MEDIA_TYPE)
	public List<Route> findAll() throws Exception {
		return routeBC.find(getCurrentUser());
	}

	@GET
	@Path("/{id}")
	@Produces(JSON_MEDIA_TYPE)
	public Route find(@PathParam("id") Integer id) throws Exception {
		return routeBC.load(id);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(JSON_MEDIA_TYPE)
	public void delete(@PathParam("id") Integer id) throws Exception {
		routeBC.delete(id);
	}

	@GET
	@Path("/{lat}/{lng}/{radius}")
	@Produces(JSON_MEDIA_TYPE)
	public List<Route> find(@PathParam("lat") Double latitude, @PathParam("lng") Double longitude,
			@PathParam("radius") Integer radius) throws Exception {
		return routeBC.find(new Coordinate(latitude, longitude), radius, getCurrentUser());
	}

	private User getCurrentUser() {
		SecurityContext securityContext = Beans.getReference(SecurityContext.class);
		return new User(securityContext.getUser().getId());
	}
}
