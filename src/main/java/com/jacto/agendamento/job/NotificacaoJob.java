package com.jacto.agendamento.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jacto.agendamento.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class NotificacaoJob {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoJob.class);

    @Autowired
    private EmailService emailService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // Executa a cada 30 minutos (0 * */30 * * * *)
    @Scheduled(cron = "0 */1 * * * *")
    public void enviarEmailPeriodicamente() {
        String para = "josedasilva@mailnator.com";
        String assunto = "Tarefa Agendada - Execução Periódica";
        String texto = "Este e-mail foi enviado automaticamente a cada 30 minutos. Horário da execução: " + dateFormat.format(new Date());

        try {
            emailService.enviarEmail(para, assunto, texto);
            logger.info("Job de envio de e-mail executado com sucesso para {} às {}", para, dateFormat.format(new Date()));
        } catch (Exception e) {
            logger.error("Erro ao enviar e-mail para {}: {}", para, e.getMessage(), e);
        }
    }
}