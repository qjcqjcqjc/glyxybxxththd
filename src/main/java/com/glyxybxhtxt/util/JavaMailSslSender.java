package com.glyxybxhtxt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailSslSender {
	@Autowired
	private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

	private static Properties serverProps;
	static {
		serverProps = new Properties();
		serverProps.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		serverProps.setProperty("mail.smtp.socketFactory.fallback", "false");
		serverProps.put("mail.smtp.auth", "true");
	}
	public static void send(String receiver, String subject, String content) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		mailSender.setJavaMailProperties(serverProps);

		helper.setSubject(subject);
		helper.setText(content,true);
		helper.setTo(receiver);
		helper.setFrom("431999764@qq.com");
		mailSender.send(mimeMessage);
	}

}
