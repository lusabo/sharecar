package service;

import static service.Constants.JSON_MEDIA_TYPE;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import entity.Route;
import entity.Schedule;

import business.ScheduleBC;

@Path("/schedule")
public class ScheduleService {
	
	@Inject
	ScheduleBC scheduleBC;
	
	@PUT
	@Consumes(JSON_MEDIA_TYPE)
	public void create(Schedule schedule) throws Exception {
		scheduleBC.insert(schedule);
	}
	

}
