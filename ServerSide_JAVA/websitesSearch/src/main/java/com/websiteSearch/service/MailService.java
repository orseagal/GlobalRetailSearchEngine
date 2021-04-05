package com.websiteSearch.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.websiteSearch.entity.NotificationEmail;
import com.websiteSearch.exceptionHander.WebsiteSearchException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
private final JavaMailSender mailSender;
private final MailContentBuilder mailContentBuilder;
	
	@Async
    void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage ->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springWebSearch@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			//messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()), true);
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			throw new WebsiteSearchException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}
	
	}
	
	
	@Async
    void sendMailWithList(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage ->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springWebSearch@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			//messageHelper.setText(mailContentBuilder.buildWithList(notificationEmail.getBody(),notificationEmail.getItems()));
			messageHelper.setText(mailContentBuilder.buildWithList(notificationEmail.getBody(),notificationEmail.getItems()), true);
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Email with new item sent");
		} catch (MailException e) {
			throw new WebsiteSearchException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
		}
	
	}
}
