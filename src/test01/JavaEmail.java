package test01;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaEmail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String to = "";
		String from = "";
		String host = "";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("hello");
			message.setText("fjofj");
			
			Transport.send(message);
			System.out.println("sent successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
