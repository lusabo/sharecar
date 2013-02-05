package persistence;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.Crud;
import entity.Schedule;

@RequestScoped
@PersistenceController
public class PeriodoDAO implements Crud<Schedule, Integer> {

	private static final long serialVersionUID = 1L;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Schedule> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Schedule bean) {
		// TODO Auto-generated method stub
	}

	@Override
	public Schedule load(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Schedule bean) {
		// TODO Auto-generated method stub
	}
}
