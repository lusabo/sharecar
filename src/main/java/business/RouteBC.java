package business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import persistence.RouteDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
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

}
