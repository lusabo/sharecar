package service;

import static service.Constants.JSON_MEDIA_TYPE;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.gov.frameworkdemoiselle.security.LoggedIn;
import business.MessageBC;
import business.UserBC;
import entity.Message;


@Path("/message")
public class MessageService {
	
	
	@Inject
	private MessageBC messageBC;
	
	@Inject
	private UserBC userBC;

	@POST
	@LoggedIn
	@Consumes(JSON_MEDIA_TYPE)
	public void send(String text) throws Exception {
		Message message = new Message();
		message.setSender(userBC.getCurrentUser());
		message.setSubject("Sugestão e Críticas");
		message.setText(text);
		messageBC.send(message);
	}


}
