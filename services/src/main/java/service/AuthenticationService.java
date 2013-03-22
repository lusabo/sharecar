package service;

import static service.Constants.JSON_MEDIA_TYPE;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.security.Credentials;
import br.gov.frameworkdemoiselle.security.LoggedIn;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.Beans;
import business.UserBC;
import entity.User;

@Path("/auth")
public class AuthenticationService {

	@Inject
	private SecurityContext securityContext;

	@Inject
	private UserBC userBC;

	@POST
	public void login(@FormParam("username") String username, @FormParam("password") String password) throws Exception {
		Credentials credentials = Beans.getReference(Credentials.class);
		credentials.setUsername(username);
		credentials.setPassword(password);

		securityContext.login();

		User currentUser = (User) securityContext.getCurrentUser();
		User persistedUser = userBC.load(currentUser.getName());

		if (persistedUser == null) {
			userBC.insert(currentUser);

		} else if (!currentUser.equals(persistedUser)) {
			currentUser.setId(persistedUser.getId());
			userBC.update(currentUser);
		}
	}

	@DELETE
	@LoggedIn
	public void logout() {
		securityContext.logout();
	}

	@GET
	@LoggedIn
	@Produces(JSON_MEDIA_TYPE)
	public User getUser() {
		return (User) securityContext.getCurrentUser();
	}
}
