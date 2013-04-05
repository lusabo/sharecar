package business;

import java.util.List;

import javax.inject.Inject;

import persistence.ScheduleDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.Route;
import entity.Schedule;

@BusinessController
public class ScheduleBC {

	@Inject
	private ScheduleDAO scheduleDAO;

	public void insert(Schedule schedule) throws Exception {
		scheduleDAO.insert(schedule);
	}

	public Schedule load(Integer id) throws Exception {
		return scheduleDAO.load(id);
	}
	
	public void delete(Integer id) throws Exception {
		scheduleDAO.delete(id);
	}
	
	public void delete(Route route) throws Exception {
		scheduleDAO.delete(route);
	}

	public List<Schedule> find(Route route) throws Exception {
		return scheduleDAO.find(route);
	}
}
