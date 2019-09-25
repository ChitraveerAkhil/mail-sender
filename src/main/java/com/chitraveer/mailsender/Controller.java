package com.chitraveer.mailsender;

import java.io.IOException;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailSender")
public class Controller {

	@Autowired
	MailProcessor processor;
	// @Autowired
	// private ResourceLoader resourceLoader;
	// final Resource fileResource =
	// resourceLoader.getResource("classpath:templates/emailBody.vm");

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(path = "/contactForm")
	public ResponseEntity<?> mailSender(@RequestBody ProcessingPojo processingPojo)
			throws ResourceNotFoundException, ParseErrorException, IOException {
		MailProcessingPojo mailProcessingPojo = new MailProcessingPojo(processingPojo, MailSenderConstants.CONTACT_FORM,
				MailSenderConstants.CONTACT_FORM_TEMPLATE_PATH);
		processor.formAndSendMail(mailProcessingPojo);
		return ResponseEntity.ok().build();
	}
}
