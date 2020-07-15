package pkg;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class GmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine engine;

	public void sendTextMailWithAttachments(HttpServletRequest request, MultipartFile[] files) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			if (request.getPart("attach").getSize() > 0) {
				helper = new MimeMessageHelper(message, true);
				for (MultipartFile file : files)
					helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
			} else
				helper = new MimeMessageHelper(message);

			helper.setTo(request.getParameter("to"));
			helper.setSubject(request.getParameter("subject"));
			helper.setText(request.getParameter("content"));

			mailSender.send(message);
		} catch (IOException | ServletException | MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendHtmlMailWithInlineImage(String to, String subject) {
		Resource resource = new ClassPathResource("static/thymeleaf-banner.png");

		Map<String, Object> variables = new HashMap<>();
		variables.put("name", "Christopher");
		variables.put("subscriptionDate", new Date());
		variables.put("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
		variables.put("imageResourceName", resource.getFilename());

		Context context = new Context();
		context.setVariables(variables);
		String html = engine.process("template", context);

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(html, true);
			helper.addInline(resource.getFilename(), resource);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
