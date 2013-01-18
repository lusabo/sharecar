package business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import persistence.PeriodoDAO;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;

@RequestScoped
@BusinessController
public class PeriodoBC {

	@Inject
	PeriodoDAO periodoDAO;
	
	public void adicionar() {}
	
	public void remover() {}
	
}
