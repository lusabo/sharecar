package service;

import static service.Constants.JSON_MEDIA_TYPE;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.gov.frameworkdemoiselle.security.Credentials;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.Beans;
import entity.User;

@Path("/auth")
public class AuthService {

	@Inject
	private SecurityContext securityContext;

	@POST
	@Produces(JSON_MEDIA_TYPE)
	public User login(@FormParam("username") String username, @FormParam("password") String password) {
		
		Credentials credentials = Beans.getReference(Credentials.class);
		credentials.setUsername(username);
		credentials.setPassword(password);
		
		securityContext.login();
		
		//System.out.println(securityContext);

		return null;
	}
}
