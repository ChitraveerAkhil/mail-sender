package com.chitraveer.mailsender;

import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MailProcessor {

	@Autowired
	Environment env;
	Logger log = LoggerFactory.getLogger(MailProcessor.class);

	public void formAndSendMail(MailProcessingPojo processingPojo) {
		VelocityEngine velocityEngine = new VelocityEngine();

		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty(env.getProperty(MailSenderConstants.CLASS_PATH_LOADER),
				ClasspathResourceLoader.class.getName());
		velocityEngine.init();
		VelocityContext context = formContext(processingPojo);

		String formattedMail = formatMail(context, velocityEngine, processingPojo.getTemplatePath());
		sendMail(formattedMail, processingPojo.getSubject());
	}

	private VelocityContext formContext(MailProcessingPojo mailMessage) {
		log.info("Forming mail context");
		VelocityContext context = new VelocityContext();
		context.put("fname", mailMessage.getProcessingPojo().getFname());
		context.put("lname", mailMessage.getProcessingPojo().getLname());
		context.put("emailId", mailMessage.getProcessingPojo().getEmailId());
		context.put("contactNo", mailMessage.getProcessingPojo().getContactNo());
		context.put("message", mailMessage.getProcessingPojo().getMessage());
		return context;
	}

	/*
	 * public static void main(String[] args) { MailProcessor processor = new
	 * MailProcessor(); processor.sendMail("testMail"); }
	 */
	private void sendMail(String formattedMail, String subject) {
		log.info("sending mail");
		String from = env.getProperty(MailSenderConstants.MAIL_FROM);
		String to = env.getProperty(MailSenderConstants.MAIL_TO);
		String pass = env.getProperty(MailSenderConstants.MAIL_PASS);

		Properties props = new Properties();
		props.setProperty(MailSenderConstants.SMTP, env.getProperty(MailSenderConstants.SMTP));
		props.setProperty(MailSenderConstants.HOST, env.getProperty(MailSenderConstants.HOST));
		props.put(MailSenderConstants.SMTP_AUTH, env.getProperty(MailSenderConstants.SMTP_AUTH));
		props.put(MailSenderConstants.SMTP_PORT, env.getProperty(MailSenderConstants.SMTP_PORT));
		props.put(MailSenderConstants.MAIL_DEBUG, env.getProperty(MailSenderConstants.MAIL_DEBUG));
		props.put(MailSenderConstants.SOCKET_FACTORY_PORT, env.getProperty(MailSenderConstants.SOCKET_FACTORY_PORT));
		props.put(MailSenderConstants.SOCKET_FACTORY_CLASS, env.getProperty(MailSenderConstants.SOCKET_FACTORY_CLASS));
		props.put(MailSenderConstants.SOCKET_FACTORY_FALLBACK,
				env.getProperty(MailSenderConstants.SOCKET_FACTORY_FALLBACK));

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		});
		try {
			Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(from);

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(subject);
			message.setText(formattedMail);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
		}
	}

	private String formatMail(VelocityContext context, VelocityEngine velocityEngine, String templatePath) {
		log.info("Formatting mail");
		Template template = velocityEngine.getTemplate(env.getProperty(templatePath));
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();

	}
}
