package service;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.spi.interception.MessageBodyWriterContext;
import org.jboss.resteasy.spi.interception.MessageBodyWriterInterceptor;

@Provider
@ServerInterceptor
public class ClallbackWriterInterceptor implements MessageBodyWriterInterceptor {

	@Inject
	private HttpServletRequest request;

	@Override
	public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {

		String callback = request.getParameter("callback");

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
