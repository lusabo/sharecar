package business;

import java.util.List;

import javax.inject.Inject;

import persistence.RouteDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.Coordinate;
import entity.Route;

@BusinessController
public class RouteBC {

	@Inject
	private RouteDAO routeDAO;

	public void insert(Route route) {
		routeDAO.insert(route);
	}

	public Route load(Integer id) {
		return routeDAO.load(id);
	}

	public List<Route> findAll() {
		return routeDAO.findAll();
	}
	
	public List<Route> find(Coordinate coord, Integer radius) {
		return routeDAO.find(coord, radius);
	}
}
