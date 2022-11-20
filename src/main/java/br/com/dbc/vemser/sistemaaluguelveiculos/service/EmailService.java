package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static String TO = "";

    private final JavaMailSender emailSender;


    public void sendEmail(String base, String destinatario) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Map<String, Object> dados = new HashMap<>();
        dados.put("base", base);
        dados.put("email", from);
        try {
            TO = destinatario;
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject("Aluguel de Veiculos");
            mimeMessageHelper.setText(getContentFromTemplate(dados, "email-template.ftl"), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(Map<String, Object> dados,
                                        String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
