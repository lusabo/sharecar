package persistence;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.mail.Mail;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import entity.Message;



public class MessageDAO {

	@Inject
	private Mail mailer;
	
	@Inject
	private ResourceBundle resourceBundle;
	
	public void send(Message message) {
		mailer
			.to(resourceBundle.getString("demoiselle-mail-to"))
			.from(message.getSender().getEmail())
			.body().text(message.getText())
			.subject(message.getSubject())
			.send();
	}
	
}
