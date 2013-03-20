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
	public void login(@FormParam("username") String username, @FormParam("password") String password) {

		Credentials credentials = Beans.getReference(Credentials.class);
		credentials.setUsername(username);
		credentials.setPassword(password);

		securityContext.login();

		String currentUsername = securityContext.getCurrentUser().getName();

		try {
			if (userBC.load(currentUsername) == null) {
				userBC.insert(new User(currentUsername));
			}
		} catch (Exception e) {
			System.out.println(":::: ERRO ::::");
		}
	}

	@DELETE
	public void logout() {
		securityContext.logout();
	}
	
	@GET
	@Produces(JSON_MEDIA_TYPE)
	public User getUser() {
		User user = new User();
		user.setUsername(securityContext.getCurrentUser().getName());
		return user;
	}
}
