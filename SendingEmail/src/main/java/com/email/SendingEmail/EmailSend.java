package com.email.SendingEmail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSend {

	
	
	@Scheduled(cron = "${app.time}", zone = "${app.timezone}")
	public void reportCurrentTime() {
		System.out.println("Cron JOB RUN EVERY 10 SEC");

	}

	private void sendmail(String emailContent) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("sender Email ID  ", "email password");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("sender Email ID", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("reciver email id"));
		msg.setSubject("Tutorials point email");
		msg.setContent("Tutorials point email", "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(emailContent, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// only for attechedmnt of email

//		   MimeBodyPart attachPart = new MimeBodyPart();
//		   attachPart.attachFile("/var/tmp/image19.png");
//		   multipart.addBodyPart(attachPart);

		msg.setContent(multipart);
		Transport.send(msg);
	}
}
