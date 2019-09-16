package com.chitraveer.mailsender;

public class MailProcessingPojo {
	private String fname;
	private String lname;
	private String emailId;
	private String contactNo;
	private String message;
	private String subject;
	private String templatePath;

	public MailProcessingPojo(String fname, String lname, String emailId, String contactNo, String message, String subject, String templatePath) {
		this.fname = fname;
		this.lname = lname;
		this.emailId = emailId;
		this.contactNo = contactNo;
		this.message = message;
		this.subject = subject;
		this.templatePath = templatePath;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
}
