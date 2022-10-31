package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.Locacao;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "jhennyfer.sobrinho@dbccompany.com.br";

    private final JavaMailSender emailSender;

    public void sendEmail(Locacao locacao, String templateName, String destinatario) {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Map<String,Object> dados = new HashMap<>();
        dados.put("nome", locacao.getFuncionario().getNome());
        dados.put("id", locacao.getFuncionario().getIdFuncionario());
        dados.put("email", from);
        dados.put("id", locacao.getFuncionario().getIdFuncionario());
        dados.put("idLocacao",locacao.getIdLocacao());
        dados.put("valorLocacao",locacao.getValorLocacao());
        dados.put("modeloVeiculo",locacao.getVeiculo().getModelo());
        dados.put("marcaVeiculo",locacao.getVeiculo().getMarca());
        dados.put("placaVeiculo",locacao.getVeiculo().getPlaca());
        dados.put("dataLocacao",locacao.getDataLocacao());
        dados.put("dataDevolucao",locacao.getDataDevolucao());
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject("Locação");
            mimeMessageHelper.setText(geContentFromTemplate(dados,templateName), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(Map<String,Object> dados, String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
