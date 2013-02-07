package business;

import java.util.List;

import javax.inject.Inject;

import persistence.RouteDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
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
}
