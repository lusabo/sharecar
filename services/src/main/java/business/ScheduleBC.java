package business;

import javax.inject.Inject;

import entity.Route;
import entity.Schedule;
import entity.User;

import persistence.ScheduleDAO;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;

@BusinessController
public class ScheduleBC {

	@Inject
	ScheduleDAO scheduleDAO;
	
	public void insert(Schedule schedule) throws Exception {
		scheduleDAO.insert(schedule);
	}	
	
}
