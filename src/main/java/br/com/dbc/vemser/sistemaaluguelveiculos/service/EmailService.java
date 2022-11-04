package br.com.dbc.vemser.sistemaaluguelveiculos.service;

import br.com.dbc.vemser.sistemaaluguelveiculos.entity.LocacaoEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "";

    private final JavaMailSender emailSender;

    public void sendEmail(LocacaoEntity locacaoEntity, String templateName, String destinatario) {

//        MimeMessage mimeMessage = emailSender.createMimeMessage();
//        Map<String,Object> dados = new HashMap<>();
//        dados.put("nome", locacaoEntity.getFuncionarioEntity().getNome());
//        dados.put("id", locacaoEntity.getFuncionarioEntity().getIdFuncionario());
//        dados.put("email", from);
//        dados.put("id", locacaoEntity.getFuncionarioEntity().getIdFuncionario());
//        dados.put("idLocacao", locacaoEntity.getIdLocacao());
//        dados.put("valorLocacao", locacaoEntity.getValorLocacao());
//        dados.put("modeloVeiculo", locacaoEntity.getVeiculo().getModelo());
//        dados.put("marcaVeiculo", locacaoEntity.getVeiculo().getMarca());
//        dados.put("placaVeiculo", locacaoEntity.getVeiculo().getPlaca());
//        dados.put("dataLocacao", locacaoEntity.getDataLocacao());
//        dados.put("dataDevolucao", locacaoEntity.getDataDevolucao());
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(from);
//            mimeMessageHelper.setTo(destinatario);
//            mimeMessageHelper.setSubject("Locação");
//            mimeMessageHelper.setText(geContentFromTemplate(dados,templateName), true);
//
//            emailSender.send(mimeMessageHelper.getMimeMessage());
//
//        } catch (MessagingException | IOException | TemplateException e) {
//            e.printStackTrace();
//        }
    }

    public String geContentFromTemplate(Map<String,Object> dados, String templateName) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
