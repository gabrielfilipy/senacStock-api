package com.senac.infrastruct.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.senac.core.email.EmailProp;
import com.senac.domain.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailInfraService implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private EmailProp emailProp;
	
	@Autowired
	private Configuration freemarker;

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			String corpo = procTemplate(mensagem);
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProp.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(corpo, true);
			
			emailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar esse E-mail " + e.getMessage(), e);
		}
	}
	
	public String procTemplate(Mensagem mensagem) {
		try {
			Template template = freemarker.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getModelos());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template desse E-mail " + e.getMessage(), e);
		}
	}
	
}
