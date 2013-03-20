package business;

import java.sql.Time;
import java.util.List;

import javax.inject.Inject;

import persistence.RouteDAO;
import persistence.ScheduleDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Coordinate;
import entity.Route;
import entity.User;
import entity.Weekday;

@BusinessController
public class RouteBC {

	@Inject
	private RouteDAO routeDAO;
	
	@Inject
	private ScheduleBC scheduleBC;

	public void insert(Route route, User user) throws Exception {
		routeDAO.insert(route, user);
	}

	public Route load(Integer id) throws Exception {
		return routeDAO.load(id);
	}

	@Transactional
	public void delete(Route route) throws Exception {
		scheduleBC.delete(route);
		routeDAO.delete(route);
	}

	public List<Route> find(User user) throws Exception {
		return routeDAO.find(user);
	}

	public List<Route> find(Coordinate coord, Integer radius, User user, Weekday weekday, Time hourini, Time hourend) throws Exception {
		return routeDAO.find(coord, radius, user, weekday, hourini, hourend);
	}
	
}
