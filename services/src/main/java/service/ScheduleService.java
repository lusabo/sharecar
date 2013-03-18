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
import javax.ws.rs.QueryParam;

import business.RouteBC;
import business.ScheduleBC;
import entity.Route;
import entity.Schedule;

@Path("/schedule")
public class ScheduleService {
	
	@Inject
	ScheduleBC scheduleBC;
	
	@Inject
	RouteBC routeBC;
	
	@PUT
	@Consumes(JSON_MEDIA_TYPE)
	public void create(Schedule schedule) throws Exception {
		scheduleBC.insert(schedule);
	}
	
	@GET
	@Produces(JSON_MEDIA_TYPE)
	public List<Schedule>  find(@QueryParam("routeId") Integer routeId) throws Exception {
		return scheduleBC.find(routeBC.load(routeId));
	}	

	@DELETE
	@Path("/{id}")
	@Produces(JSON_MEDIA_TYPE)
	public void delete(@PathParam("id") Integer id) throws Exception {
		scheduleBC.delete(id);
	}	
}
