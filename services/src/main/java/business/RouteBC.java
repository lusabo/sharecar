package business;

import java.util.List;

import javax.inject.Inject;

import persistence.RouteDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.Coordinate;
import entity.Route;
import entity.User;

@BusinessController
public class RouteBC {

	@Inject
	private RouteDAO routeDAO;

	public void insert(Route route, User user) throws Exception {
		routeDAO.insert(route, user);
	}

	public Route load(Integer id) throws Exception {
		return routeDAO.load(id);
	}
	
	public void delete(Integer id) throws Exception {
		routeDAO.delete(id);
	}

	// public List<Route> findAll() throws Exception {
	// return routeDAO.findAll();
	// }

	public List<Route> find(User user) throws Exception {
		return routeDAO.find(user);
	}

	public List<Route> find(Coordinate coord, Integer radius, User user) throws Exception {
		return routeDAO.find(coord, radius, user);
	}
}
