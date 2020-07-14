package test01;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class JavaMailG {
	public static void main(String[] args) throws GeneralSecurityException, IOException, MessagingException {
		String to = "kobe850829@gmail.com"; //收件人
		String from = "Spordan2018@gmail.com"; //寄件人
		String host = "smtp.gmail.com"; //host mail server
		Properties properties = System.getProperties();
		
		properties.setProperty("mail.smtp.host", host); //設定mail server
		properties.put("mail.smtp.auth", "true"); //需授權認證
		properties.put("mail.smtp.starttls.enable", "true"); //使用tls
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.port", 587); //gmail tls port 587
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
//		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("Spordan2018@gmail.com", "SpordanFju"); 
				//寄件人帳號密碼授權
			}
		});
		
//		String htmlMessage = "<h1>SoftMobile</h1><br>";
//		htmlMessage += "<b>Wish you a nice day!</b><br>";
//        htmlMessage += "<font color=red>Duke</font>";
		
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from)); 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //還有CC、多收件人的方式
			message.setSubject("hi");
//			message.setText("jdaiosjd"); //有些內容可以用html、或是附件(要用MultiPart)
			
			Date date = new Date();
			SimpleDateFormat barDateFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			String sDate = barDateFormat.format(date);
			String sTime = timeFormat.format(date);
			String sExtraString = "荊門台商協會長簡俊男24日證實，滯留在湖北荊門的血友病少年和他母親，將搭乘長榮航空，預計今天晚上10時44分左右抵達台，簡俊男表示，經過一個多星期的奔走溝通，在各方協助下才能完成這項任務";
			
			//html要用setContent，語法可以直接寫，或是用讀檔，template以外就塞變數
			String htmlMessage = "<!DOCTYPE html>\r\n" + 
					"    <body>\r\n" + 
					"        <img src=\"cid:softmobileLogo\">\r\n" + 
					"        <center><h3><a href=\"http://www.softmobile.com.tw/\">SoftMobile</a></h3></center>\r\n" + 
					"        <table bgcolor=\"#38ddf4\" width=100%>\r\n" + 
					"            <tr>\r\n" + 
					"                <th>Date</th>\r\n" + 
					"                <th>Time</th>\r\n" + 
					"                <th>You</th>\r\n" + 
					"            </tr>\r\n" + 
					"            <tr>\r\n" + 
					"                <td align=\"center\">"+sDate+"</td>\r\n" + 
					"                <td align=\"center\">"+sTime+"</td>\r\n" + 
					"                <td align=\"center\">"+to+"</td>\r\n" + 
					"            </tr>\r\n" + 
					"        </table>\r\n" + 
					"        <br>\r\n" +
					"        <center><b>"+ sExtraString +"</b></center>"+
					"        <br>\r\n" + 
					"       <div style=\"background-color:#3b2f44;\">  \r\n" + 
					"        <font size=\"1pt\" color=\"white\">This letter is sent automatically.  Please do not reply directly.</font>\r\n" + 
					"        <center><font size=\"1pt\" color=\"white\"><i>Design by Kai.</i></font></center>\r\n" + 
					"        </div> "+
					"    </body>\r\n" + 
					"</html>";
			
			//圖片需要分part
			Multipart mp = new MimeMultipart();
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlMessage, "text/html; charset=UTF-8");
			mp.addBodyPart(htmlPart);
			
			MimeBodyPart imagePart = new MimeBodyPart();
			imagePart.setHeader("Content-ID", "<softmobileLogo>"); //這邊設定圖片unique id，去對到html的img src，這邊要記得<>
			imagePart.setDisposition(MimeBodyPart.INLINE);
			imagePart.attachFile("C:\\Users\\juija\\Desktop\\logo.gif"); //圖片位置
			mp.addBodyPart(imagePart);
			
//			message.setContent(htmlMessage, "text/html; charset=UTF-8");
			
			message.setContent(mp);
			Transport.send(message); //建立連線並送出信件
			System.out.println("sent successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
