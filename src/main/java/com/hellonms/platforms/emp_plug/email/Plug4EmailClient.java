package com.hellonms.platforms.emp_plug.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.hellonms.platforms.emp_core.server.transaction.EmpContext;
import com.hellonms.platforms.emp_core.share.error.ERROR_CODE_CORE;
import com.hellonms.platforms.emp_core.share.error.EmpException;

public class Plug4EmailClient {

	private class Plug4EmailClientAuthenticator extends Authenticator {

		private String account;

		private String password;

		public Plug4EmailClientAuthenticator(String account, String password) {
			this.account = account;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(account, password);
		}

	}

	/**
	 * <p>
	 * Plain TEXT 이메일을 보낸다.
	 * </p>
	 * 
	 * @param host
	 *            SMTP 주소
	 * @param port
	 *            SMTP port
	 * @param account
	 *            SMTP 계정
	 * @param password
	 *            SMTP 암호
	 * @param from
	 *            보내는 사람 Email
	 * @param fromName
	 *            보내는 사람 이름
	 * @param tos
	 *            수신 목록
	 * @param ccs
	 *            참조 목록
	 * @param bccs
	 *            숨은참조 목록
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @param files
	 *            첨부파일
	 * @throws EmpException
	 */
	public void sendEmail(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", String.valueOf(port));
			props.put("mail.smtp.user", fromMail);
			props.put("mail.smtp.auth", "true");

			Plug4EmailClientAuthenticator auth = new Plug4EmailClientAuthenticator(account, password);
			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromMail, MimeUtility.encodeText(fromName, "UTF-8", "B")));

			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			for (String to : tos) {
				toList.add(new InternetAddress(to));
			}
			msg.setRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[0]));

			List<InternetAddress> ccList = new ArrayList<InternetAddress>();
			for (String cc : ccs) {
				ccList.add(new InternetAddress(cc));
			}
			msg.setRecipients(Message.RecipientType.CC, ccList.toArray(new InternetAddress[0]));

			List<InternetAddress> bccList = new ArrayList<InternetAddress>();
			for (String bcc : bccs) {
				bccList.add(new InternetAddress(bcc));
			}
			msg.setRecipients(Message.RecipientType.BCC, bccList.toArray(new InternetAddress[0]));

			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new java.util.Date());

			Multipart mp = new MimeMultipart();

			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html; charset=UTF-8");
			mp.addBodyPart(bodyPart);

			for (Entry<String, File> file : files.entrySet()) {
				MimeBodyPart mbp = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(file.getValue());
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(MimeUtility.encodeText(file.getKey(), "UTF-8", "B"));
				mp.addBodyPart(mbp);
			}

			msg.setContent(mp);
			Transport.send(msg);
		} catch (Exception e) {
			throw new EmpException(ERROR_CODE_CORE.NETOWRK_IO, e);
		}
	}

	/**
	 * <p>
	 * SSL 이메일을 보낸다.
	 * </p>
	 * 
	 * @param host
	 *            SMTP 주소
	 * @param port
	 *            SMTP port
	 * @param id
	 *            SMTP 계정
	 * @param password
	 *            SMTP 암호
	 * @param from
	 *            보내는 사람 Email
	 * @param fromName
	 *            보내는 사람 이름
	 * @param tos
	 *            수신 목록
	 * @param ccs
	 *            참조 목록
	 * @param bccs
	 *            숨은참조 목록
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @param files
	 *            첨부파일
	 * @throws EmpException
	 */
	public void sendEmail4SSL(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", String.valueOf(port));
			props.put("mail.smtp.user", fromMail);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.socketFactory.port", String.valueOf(port));
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			Plug4EmailClientAuthenticator auth = new Plug4EmailClientAuthenticator(account, password);
			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromMail, MimeUtility.encodeText(fromName, "UTF-8", "B")));

			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			for (String to : tos) {
				toList.add(new InternetAddress(to));
			}
			msg.setRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[0]));

			List<InternetAddress> ccList = new ArrayList<InternetAddress>();
			for (String cc : ccs) {
				ccList.add(new InternetAddress(cc));
			}
			msg.setRecipients(Message.RecipientType.CC, ccList.toArray(new InternetAddress[0]));

			List<InternetAddress> bccList = new ArrayList<InternetAddress>();
			for (String bcc : bccs) {
				bccList.add(new InternetAddress(bcc));
			}
			msg.setRecipients(Message.RecipientType.BCC, bccList.toArray(new InternetAddress[0]));

			Multipart mp = new MimeMultipart();

			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new java.util.Date());

			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html; charset=UTF-8");
			mp.addBodyPart(bodyPart);

			for (Entry<String, File> file : files.entrySet()) {
				MimeBodyPart mbp = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(file.getValue());
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(MimeUtility.encodeText(file.getKey(), "UTF-8", "B"));
				mp.addBodyPart(mbp);
			}

			msg.setContent(mp);
			Transport.send(msg);
		} catch (Exception e) {
			throw new EmpException(ERROR_CODE_CORE.NETOWRK_IO, e);
		}
	}

	/**
	 * <p>
	 * SSL 이메일을 보낸다.
	 * </p>
	 * 
	 * @param host
	 *            SMTP 주소
	 * @param port
	 *            SMTP port
	 * @param id
	 *            SMTP 계정
	 * @param password
	 *            SMTP 암호
	 * @param from
	 *            보내는 사람 Email
	 * @param fromName
	 *            보내는 사람 이름
	 * @param tos
	 *            수신 목록
	 * @param ccs
	 *            참조 목록
	 * @param bccs
	 *            숨은참조 목록
	 * @param subject
	 *            제목
	 * @param content
	 *            내용
	 * @param files
	 *            첨부파일
	 * @throws EmpException
	 */
	public void sendEmail4Gmail(EmpContext context, String host, int port, String account, String password, String fromMail, String fromName, String[] tos, String[] ccs, String[] bccs, String subject, String content, Map<String, File> files) throws EmpException {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.user", account);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			// props.put("mail.smtp.starttls.enable", "true");
			// props.put("mail.transport.protocol", "smtp");
			// props.put("mail.smtp.host", host);
			// props.put("mail.smtp.port", String.valueOf(port));
			// props.put("mail.smtp.user", fromMail);
			// props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.debug", "true");
			// props.put("mail.smtp.socketFactory.port", String.valueOf(port));
			// props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// props.put("mail.smtp.socketFactory.fallback", "false");

			Plug4EmailClientAuthenticator auth = new Plug4EmailClientAuthenticator(account, password);
			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromMail, MimeUtility.encodeText(fromName, "UTF-8", "B")));

			List<InternetAddress> toList = new ArrayList<InternetAddress>();
			for (String to : tos) {
				toList.add(new InternetAddress(to));
			}
			msg.setRecipients(Message.RecipientType.TO, toList.toArray(new InternetAddress[0]));

			List<InternetAddress> ccList = new ArrayList<InternetAddress>();
			for (String cc : ccs) {
				ccList.add(new InternetAddress(cc));
			}
			msg.setRecipients(Message.RecipientType.CC, ccList.toArray(new InternetAddress[0]));

			List<InternetAddress> bccList = new ArrayList<InternetAddress>();
			for (String bcc : bccs) {
				bccList.add(new InternetAddress(bcc));
			}
			msg.setRecipients(Message.RecipientType.BCC, bccList.toArray(new InternetAddress[0]));

			Multipart mp = new MimeMultipart();

			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new java.util.Date());

			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html; charset=UTF-8");
			mp.addBodyPart(bodyPart);

			for (Entry<String, File> file : files.entrySet()) {
				MimeBodyPart mbp = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(file.getValue());
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(MimeUtility.encodeText(file.getKey(), "UTF-8", "B"));
				mp.addBodyPart(mbp);
			}

			msg.setContent(mp);
			Transport.send(msg);
		} catch (Exception e) {
			throw new EmpException(ERROR_CODE_CORE.NETOWRK_IO, e);
		}
	}

}
