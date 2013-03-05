package service;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

@Provider
@ServerInterceptor
public class JSONPWriterInterceptor implements MessageBodyWriterInterceptor {

	@Context
	private HttpRequest request;

	@Override
	public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {

		String callback = request.getUri().getQueryParameters().getFirst("callback");

		if (callback != null) {
			context.getOutputStream().write(callback.getBytes());
			context.getOutputStream().write("(".getBytes());
		}

		context.proceed();

		if (callback != null) {
			context.getOutputStream().write(")".getBytes());
		}
	}
}
