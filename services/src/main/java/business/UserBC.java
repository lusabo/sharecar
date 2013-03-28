package business;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import persistence.UserDAO;
import br.gov.frameworkdemoiselle.security.AfterLoginSuccessful;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Beans;
import entity.User;

@BusinessController
public class UserBC {

	@Inject
	private UserDAO userDAO;

	public void insert(User user) throws Exception {
		userDAO.insert(user);
	}

	public void update(User user) throws Exception {
		userDAO.update(user);
	}

	public User load(String username) throws Exception {
		return userDAO.loadByUsername(username);
	}

	protected void afterLoginSuccessful(@Observes AfterLoginSuccessful event) throws Exception {
		SecurityContext securityContext = Beans.getReference(SecurityContext.class);
		
		User currentUser = (User) securityContext.getCurrentUser();
		User persistedUser = load(currentUser.getName());

		if (persistedUser == null) {
			insert(currentUser);

		} else if (!currentUser.equals(persistedUser)) {
			currentUser.setId(persistedUser.getId());
			update(currentUser);
		}
	}
}
