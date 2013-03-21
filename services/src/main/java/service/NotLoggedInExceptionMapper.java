package service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.gov.frameworkdemoiselle.security.NotLoggedInException;

@Provider
public class NotLoggedInExceptionMapper implements ExceptionMapper<NotLoggedInException> {

	@Override
	public Response toResponse(NotLoggedInException exception) {
		// return Response.status(401).header("WWW-Authenticate", "Basic realm=realm").build();
		return Response.status(401).build();
	}
}
