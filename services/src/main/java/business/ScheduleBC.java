package business;

import java.util.List;

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

	public void delete(Integer id) throws Exception {
		scheduleDAO.delete(id);
	}

	public List<Schedule> find(Route route) throws Exception {
		return scheduleDAO.find(route);
	}
}
