package persistence;

import javax.enterprise.context.RequestScoped;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import entity.Rota;

@RequestScoped
@PersistenceController
public class RotaDAO extends JPACrud<Rota, Integer>{
	
	private static final long serialVersionUID = 1L;
	
	public Rota obterReferencia(Integer id) {
		return getEntityManager().getReference(Rota.class, id);
	}
	
}
