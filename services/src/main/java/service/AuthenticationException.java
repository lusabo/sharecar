package service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationException extends Exception implements ExceptionMapper<AuthenticationException> {

	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(AuthenticationException exception) {
		return Response.status(401).build();
	}
}
