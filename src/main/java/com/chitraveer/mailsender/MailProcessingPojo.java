package com.chitraveer.mailsender;

public class MailProcessingPojo {
	private ProcessingPojo processingPojo;
	private String subject;
	private String templatePath;

	public MailProcessingPojo(ProcessingPojo processingPojo, String subject, String templatePath) {
		this.setProcessingPojo(processingPojo);
		this.subject = subject;
		this.templatePath = templatePath;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public ProcessingPojo getProcessingPojo() {
		return processingPojo;
	}

	public void setProcessingPojo(ProcessingPojo processingPojo) {
		this.processingPojo = processingPojo;
	}
}
