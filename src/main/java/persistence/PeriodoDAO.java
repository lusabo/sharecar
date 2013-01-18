package persistence;

import javax.enterprise.context.RequestScoped;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import entity.Periodo;
import entity.Rota;

@RequestScoped
@PersistenceController
public class PeriodoDAO extends JPACrud<Periodo, Integer> {

	private static final long serialVersionUID = 1L;
	
}
