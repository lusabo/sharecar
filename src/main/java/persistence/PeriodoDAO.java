package persistence;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.Crud;
import entity.Periodo;

@RequestScoped
@PersistenceController
public class PeriodoDAO implements Crud<Periodo, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Periodo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Periodo bean) {
		// TODO Auto-generated method stub
	}

	@Override
	public Periodo load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Periodo bean) {
		// TODO Auto-generated method stub
	}
}
