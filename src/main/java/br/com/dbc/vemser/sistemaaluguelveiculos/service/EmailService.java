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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String destinatario;

    private static final String remetente = "jhennyfer.sobrinho@dbccompany.com.br";

    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(destinatario);
        message.setTo(remetente);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException, FileNotFoundException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);

        helper.setFrom(destinatario);
        helper.setTo(remetente);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");
        File file1 = ResourceUtils.getFile("classpath:imagem.jpg");
        //File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

        emailSender.send(message);
    }

    public void sendEmail(Locacao locacao, String templateName, String destinatario) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        Map<String,Object> dados = new HashMap<>();
        dados.put("nome", locacao.getCliente().getNome());
        dados.put("id", locacao.getCliente().getIdCliente());
        dados.put("email", remetente);
        dados.put("idLocacao",locacao.getIdLocacao());
        dados.put("valorLocacao",locacao.getValorLocacao());
        dados.put("modeloVeiculo",locacao.getVeiculo().getModelo());
        dados.put("marcaVeiculo",locacao.getVeiculo().getMarca());
        dados.put("placaVeiculo",locacao.getVeiculo().getPlaca());
        dados.put("dataLocacao",locacao.getDataLocacao());
        dados.put("dataDevolucao",locacao.getDataDevolucao());
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setSubject("subject");
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
