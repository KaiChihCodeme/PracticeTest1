package test01.Servlet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;

public class NoticeMessageGMail {

	public void sendMail(String messageBody) throws GeneralSecurityException, IOException {
		String to = "kobe850829@gmail.com"; //����H
		String from = "Spordan2018@gmail.com"; //�H��H
		String host = "smtp.gmail.com"; //host mail server
		Properties properties = System.getProperties();
		
		properties.setProperty("mail.smtp.host", host); //�]�wmail server
		properties.put("mail.smtp.auth", "true"); //�ݱ��v�{��
		properties.put("mail.smtp.starttls.enable", "true"); //�ϥ�tls
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.port", 587); //gmail tls port 587
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
//		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("Spordan2018@gmail.com", "SpordanFju"); 
				//�H��H�b���K�X���v
			}
		});
		
//		String htmlMessage = "<h1>SoftMobile</h1><br>";
//		htmlMessage += "<b>Wish you a nice day!</b><br>";
//        htmlMessage += "<font color=red>Duke</font>";
		
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from)); 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //�٦�CC�B�h����H���覡
			message.setSubject("<MessageBook> You got a new message!");
//			message.setText("jdaiosjd"); //���Ǥ��e�i�H��html�B�άO����(�n��MultiPart)
			
//			Date date = new Date();
//			SimpleDateFormat barDateFormat = new SimpleDateFormat("yyyyMMdd");
//			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//			String sDate = barDateFormat.format(date);
//			String sTime = timeFormat.format(date);
//			String sExtraString = "����x�Ө�|��²�T�k24���ҹ�A���d�b��_�������ͯf�֦~�M�L���ˡA�N�f�����a��šA�w�p���ѱߤW10��44�����k��F�x�A²�T�k��ܡA�g�L�@�Ӧh�P�����b�����q�A�b�U���U�U�~�৹���o������";
			
			//html�n��setContent�A�y�k�i�H�����g�A�άO��Ū�ɡAtemplate�H�~�N���ܼ�
			String htmlMessage = "<!DOCTYPE html>\r\n" + 
					"    <body>\r\n" + 
					"        <img src=\"cid:softmobileLogo\">\r\n" + 
					"        <center><h3><a href=\"http://www.softmobile.com.tw/\">SoftMobile</a></h3></center>\r\n" +
					"        <br>" +
					"        <center><h1> Somebody submit a message! </h1></center>\r\n" + 
					"        <br>\r\n" + 
					"        <br>\r\n" + 
					"        <hr>"+
					"        <div style=\"background-color: cornsilk; height: fit-content;\">\r\n" + 
					"            <font size=\"4pt\" color=\"gray\">" + messageBody + "</font>" + 
					"        </div>      " +
					"        <hr>"+
					"        <br>\r\n" + 
					"        <div style=\"background-color:#3b2f44;\">  \r\n" + 
					"        <center><font size=\"1pt\" color=\"white\">This letter is sent automatically.  Please do not reply directly.</font></center>\r\n" + 
					"        <center><font size=\"1pt\" color=\"white\"><i>@Design by Kai.</i></font></center>\r\n" + 
					"        </div> "+
					"    </body>\r\n" + 
					"</html>";
			
			//�Ϥ��ݭn��part
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlMessage, "text/html; charset=UTF-8");
			mp.addBodyPart(htmlPart);
			
			MimeBodyPart imagePart = new MimeBodyPart();
			imagePart.setHeader("Content-ID", "<softmobileLogo>"); //�o��]�w�Ϥ�unique id�A�h���html��img src�A�o��n�O�o<>
			imagePart.setDisposition(MimeBodyPart.INLINE);
			imagePart.attachFile("C:\\Users\\juija\\Desktop\\logo.gif"); //�Ϥ���m
			mp.addBodyPart(imagePart);
			
//			message.setContent(htmlMessage, "text/html; charset=UTF-8");
			
			message.setContent(mp);
			Transport.send(message); //�إ߳s�u�ðe�X�H��
			System.out.println("sent successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
