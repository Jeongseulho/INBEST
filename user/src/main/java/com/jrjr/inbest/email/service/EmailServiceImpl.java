package com.jrjr.inbest.email.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.jrjr.inbest.email.entity.EmailVerificationCode;
import com.jrjr.inbest.email.repository.EmailVerificationCodeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final AmazonSimpleEmailService amazonSimpleEmailService;
	private final EmailVerificationCodeRepository emailVerificationCodeRepository;

	static final String SOURCE_EMAIL = "inbest110@gmail.com";

	@Override
	public void sendVerificationCode(String email) {
		log.debug("EmailServiceImpl - sendVerificationCode 실행: {}", email);

		Destination destination = new Destination().withToAddresses(email.trim());

		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("<span style='white-space:nowrap'>안녕하세요.&nbsp;</span>");
		sb.append("<span style='font-weight:bold;white-space:nowrap'>inBest</span><span> 입니다.</span>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<span>아래  인증코드를 회원가입 창으로 돌아가 입력해주세요</span>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<span style='color:#39CCCC;font-size:24px;font-weight:bold'>")
			.append(this.generateVerificationCode(email.trim()))
			.append("</span><br>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<span style='font-weight:bold'>※주의 : </span><span>인증번호는 "
			+ "</span><span style='font-weight:bold'>3분 이후에 만료&nbsp;</span>"
			+ "<span>되므로 꼭 3분 이내에 입력해주시길 바랍니다.</span>");
		sb.append("<br>");
		sb.append("<br>");
		sb.append("<p>감사합니다!<p>");

		Message message = new Message()
			.withSubject(createContent("inBest 이메일 인증 코드"))
			.withBody(new Body()
				.withHtml(createContent(sb.toString())));

		amazonSimpleEmailService.sendEmail(new SendEmailRequest()
			.withSource(SOURCE_EMAIL)
			.withDestination(destination)
			.withMessage(message));
	}

	@Override
	public String generateVerificationCode(String email) {
		log.debug("EmailServiceImpl - generateVerificationCode 실행: {}", email);

		String code = RandomStringUtils.randomAlphanumeric(6);
		emailVerificationCodeRepository.save(
			EmailVerificationCode.builder()
				.email(email)
				.code(code)
				.build());

		return code;
	}

	@Override
	public Content createContent(String message) {
		return new Content()
			.withCharset("UTF-8")
			.withData(message);
	}
}
