package com.jrjr.inbest.email.service;

import com.amazonaws.services.simpleemail.model.Content;

public interface EmailService {

	void sendVerificationCode(String email);

	String generateVerificationCode(String email);

	Content createContent(String message);
}
