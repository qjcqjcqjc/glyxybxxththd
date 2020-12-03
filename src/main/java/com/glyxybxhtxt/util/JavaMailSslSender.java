package com.glyxybxhtxt.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
	 private static Properties serverProps;
	   
	    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	    private static final String SMTP_HOST = "smtp.qq.com"; // smtp 服务器地址
	    private static final int SMTP_PORT = 465; // smtp 服务器端口
	   
	    private static final String FROM_ADDR = "431999764@qq.com"; // 邮件的发送者地址
	    private static final String USERNAME = "431999764"; // 用户名
	    private static final String PASSWORD = "qqtbxlpldwyabjjc"; // 密码
	   
	    static {
	        serverProps = new Properties();
	        serverProps.setProperty("mail.smtp.host", SMTP_HOST);
	        serverProps.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	        serverProps.setProperty("mail.smtp.socketFactory.fallback", "false");
	        serverProps.setProperty("mail.smtp.port", String.valueOf(SMTP_PORT));
	        serverProps.setProperty("mail.smtp.socketFactory.port", String.valueOf(SMTP_PORT));
	        serverProps.put("mail.smtp.auth", "true");
	    }
	   
	    public static void send(String receiver, String subject, String content) throws AddressException, MessagingException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
//	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"), "AES"));
			cipher.doFinal("QWEASDZS".getBytes("UTF-8"));
			Session session = Session.getDefaultInstance(serverProps, new Authenticator() {

	                @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(USERNAME, PASSWORD);
	                }
	        });
	       
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(FROM_ADDR));
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver, false));
	        msg.setSubject(subject);
	        msg.setText(content);
	        msg.setSentDate(new Date());
	        Transport.send(msg);

	        System.out.println("Message sent.");
	    }
}
