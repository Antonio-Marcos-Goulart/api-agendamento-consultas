package com.antonio.SistemadeAgendamentodeConsultas.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class EmailService { // Realiza o envio dos emails solicitados por outras classes

    @Autowired
    private JavaMailSender javaMailService;

    // EMAIL COM ANEXO
    public void enviarEmail(String destinatario, String assunto, String conteudo, MultipartFile anexo) {
        try {
            MimeMessage mensagem = javaMailService.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8"); // Formatação UTF-8 = caracteres especiais ç, é, á, ô....

            helper.setFrom("antoniomarcosgoulartt@gmail.com"); // Remetente
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(conteudo, true); // Posso criar os e-mails em HTML
            helper.addAttachment(Objects.requireNonNull(anexo.getOriginalFilename()), anexo); // Envio de anexos (PDF, TXT...)

            javaMailService.send(mensagem); // Envia, faz o envio para o destino usando as informações do application.properties e aquelas do helper juntando com o email do cliente cadastrado com ele
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail " + e.getMessage());
        }
    }

    // EMAIL SEM ANEXO
    public void  enviarEmail(String destinatario, String assunto, String conteudo){
        try {
            MimeMessage mensagem = javaMailService.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8"); // Formatação UTF-8 = caracteres especiais ç, é, á, ô....

            helper.setFrom("antoniomarcosgoulartt@gmail.com"); // Remetente
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(conteudo, true); // Posso criar os e-mails em HTML

            javaMailService.send(mensagem); // Envia, faz o envio para o destino usando as informações do application.properties e aquelas do helper juntando com o email do cliente cadastrado com ele

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail " + e.getMessage());
        }
    }
}
