package business;

import java.util.List;

import javax.inject.Inject;

import persistence.ScheduleDAO;
import br.gov.frameworkdemoiselle.security.AuthorizationException;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.Route;
import entity.Schedule;
import entity.User;

@BusinessController
public class ScheduleBC {

	@Inject
	private ScheduleDAO scheduleDAO;
	
	@Inject
	private UserBC userBC;

	public void insert(Schedule schedule) throws Exception {
		scheduleDAO.insert(schedule);
	}

	public Schedule load(Integer id) throws Exception {
		return scheduleDAO.load(id);
	}
	
	public void delete(Integer id) throws Exception {
		
		Schedule _schedule = scheduleDAO.load(id);
		User _user = userBC.getCurrentUser();

		if (_schedule.getRoute().getUser().equals(_user)) {
			scheduleDAO.delete(id);
		} else {
			throw new AuthorizationException("Não é possível remover horário associado a rota de outro usuário");
		}
		
		
	}
	
	public void delete(Route route) throws Exception {
		scheduleDAO.delete(route);
	}

	public List<Schedule> find(Route route) throws Exception {
		return scheduleDAO.find(route);
	}
}
