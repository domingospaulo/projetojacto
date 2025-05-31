package com.jacto.agendamento.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.VisitaTecnicaService;

@Component
@EnableScheduling
public class NotificacaoJob {

    private static final Logger logger = LoggerFactory.getLogger(NotificacaoJob.class);
    private static final String DATE_FORMAT = "HH:mm:ss";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    private VisitaTecnicaService visitaTecnicaService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(cron = "0 0/3 * * * *") // Executa no minuto 0 e 3 de cada hora
    public void enviarNotificacoesVisitaTecnica() {
        logger.info("Início da execução do job de notificação de visita técnica às {}", dateFormat.format(new Date()));

        Date inicio = new Date(); // Guarda o tempo de início
        try {
           
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 3); // Próximos 3 minutos
            Date nextHour = calendar.getTime();

            List<VisitaTecnica> visitasProximas = visitaTecnicaService.buscarPorDataHoraVisitaInicioAgendadoEntreEFlagReagendamento(now, nextHour, true);

            if (visitasProximas.isEmpty()) {
                logger.info("Nenhuma visita técnica agendada para a próxima hora.");
                return;
            }

            for (VisitaTecnica visita : visitasProximas) {
                if (visita.getFlagReagendamento())
                jmsTemplate.convertAndSend("visita-tecnica-queue", visita.getId());
                logger.info("Enviando notificação para a fila JMS para a visita técnica com ID: {}", visita.getId());
            }
         } finally {
            Date fim = new Date(); // Guarda o tempo de fim
            logger.info("Fim da execução do job de notificação de visita técnica às {}. Tempo de execução: {}ms",
                        dateFormat.format(fim), (fim.getTime() - inicio.getTime()));
        }
    }
}