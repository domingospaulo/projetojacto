package com.jacto.agendamento.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.jacto.agendamento.service.EmailService;

@Service
public class EmailServiceImpl  implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    // @Value("${spring.mail.username}")
    //Remetente esta fixo por erro na leitura da variavel de ambiente
    //TODO: Corrigir leitura da variavel de ambiente
    private String remetente = "domingos.paul@gmail.com";

    public void enviarEmail(String para, String assunto, String texto) {
        SimpleMailMessage mensagem = new SimpleMailMessage();

        // Define o remetente
        mensagem.setFrom(remetente);

        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText(texto);

        try {
            mailSender.send(mensagem);
            logger.info("E-mail enviado com sucesso para: " + para);
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail para {}: {}", para, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}