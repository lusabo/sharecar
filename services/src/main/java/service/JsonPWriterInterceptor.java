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
public class JsonPWriterInterceptor implements MessageBodyWriterInterceptor {

	@Context
	private HttpRequest request;

	@Override
	public void write(MessageBodyWriterContext context) throws IOException, WebApplicationException {

		// context.getAttribute("callback");

		String callback = request.getUri().getQueryParameters().getFirst("callback");

		// HttpServletRequest requestx = Beans.getReference(HttpServletRequest.class);
		//
		// Enumeration<String> enumeration;
		//
		// enumeration = requestx.getAttributeNames();
		// while (enumeration.hasMoreElements()) {
		// System.out.println(enumeration.nextElement());
		// }
		//
		// enumeration = requestx.getParameterNames();
		// while (enumeration.hasMoreElements()) {
		// System.out.println(enumeration.nextElement());
		// }

		if (callback != null) {
			context.getOutputStream().write(callback.getBytes());
			context.getOutputStream().write("(".getBytes());
			context.proceed();
			context.getOutputStream().write(")".getBytes());
		} else {
			context.proceed();
		}
	}
}
