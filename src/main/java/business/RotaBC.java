package business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import persistence.RotaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Rota;

@RequestScoped
@BusinessController
public class RotaBC{

	 @Inject
	 private RotaDAO rotaDAO;
	
	 /*
	 * Insere/Atualiza uma Rota
	 * */
	 @Transactional
	 public void create(Rota rota) {
	
	 Rota rotaSalva;
	
	 if (rota.getId() == null || rota.getId() == 0) {
		 rotaSalva = new Rota();
	 } else {
		 rotaSalva = rotaDAO.obterReferencia(rota.getId());
	 }
	
	 rotaSalva.setUsuario(rota.getUsuario());
	 rotaSalva.setDescricao(rota.getDescricao());
	 rotaSalva.setCaminho(rota.getCaminho());
	
	 rotaDAO.insert(rotaSalva);
	 }
	
	 public Rota show(Integer id) {
	 return rotaDAO.obterReferencia(id);
	 }

}
