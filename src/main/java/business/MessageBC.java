package business;

import javax.inject.Inject;

import persistence.MessageDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import entity.Message;


@BusinessController
public class MessageBC {
	
	@Inject
	MessageDAO messageDAO;
	
	public void send(Message message) throws Exception {
		messageDAO.send(message);
	}
	

}
