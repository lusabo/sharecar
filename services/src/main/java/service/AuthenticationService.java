package service;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.gov.frameworkdemoiselle.security.Credentials;
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
	public void login(@FormParam("username") String username, @FormParam("password") String password)
			throws AuthenticationException, Exception {

		Credentials credentials = Beans.getReference(Credentials.class);
		credentials.setUsername(username);
		credentials.setPassword(password);

		securityContext.login();

		String currentUsername = securityContext.getUser().getId();

		if (userBC.load(currentUsername) == null) {
			userBC.insert(new User(currentUsername));
		}

		if (!securityContext.isLoggedIn()) {
			throw new AuthenticationException();
		}
	}

	@DELETE
	public void logout() throws AuthenticationException {
		securityContext.logout();
	}
}
