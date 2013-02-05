package business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import persistence.RouteDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Route;

@RequestScoped
@BusinessController
public class RotaBC {

	@Inject
	private RouteDAO rotaDAO;

	/*
	 * Insere/Atualiza uma Rota
	 */
	@Transactional
	public void salvar(Route rota) {

		Route rotaSalva;

		if (rota.getId() == null || rota.getId() == 0) {
			rotaSalva = new Route();
		} else {
			rotaSalva = rotaDAO.load(rota.getId());
		}

		rotaSalva.setUsuario(rota.getUsuario());
		rotaSalva.setDescription(rota.getDescription());
		rotaSalva.setCaminho(rota.getCaminho());

		rotaDAO.insert(rotaSalva);
	}

}
