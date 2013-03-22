package business;

import javax.inject.Inject;

import persistence.UserDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.User;

@BusinessController
public class UserBC {

	@Inject
	UserDAO userDAO;
	
	public void insert(User user) throws Exception {
		userDAO.insert(user);
	}
	
	public void update(User user) throws Exception {
		userDAO.update(user);
	}

	public User load(String username) throws Exception {
		return userDAO.loadByUsername(username);
	}	
	
}
