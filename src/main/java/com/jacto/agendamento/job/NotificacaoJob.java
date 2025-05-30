package com.jacto.agendamento.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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
    private KafkaTemplate<String, Long> kafkaTemplate;

    private final String topicName = "visita-tecnica-notificacoes"; // Nome do tópico Kafka

    @Scheduled(cron = "0 * * * * *") // Executa a cada minuto
    public void enviarNotificacoesVisitaTecnica() {
        logger.info("Início da execução do job de notificação de visita técnica às {}", dateFormat.format(new Date()));

        Date inicio = new Date(); // Guarda o tempo de início
        try {

            if (kafkaTemplate == null){
                logger.error("KafkaTemplate is null. Verify kafka configuration");
                return;
            }
           
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR_OF_DAY, 1); // Próxima hora
            Date nextHour = calendar.getTime();

            List<VisitaTecnica> visitasProximas = visitaTecnicaService.buscarPorDataHoraVisitaInicioAgendadoEntre(now, nextHour);

            if (visitasProximas.isEmpty()) {
                logger.info("Nenhuma visita técnica agendada para a próxima hora.");
                return;
            }

            for (VisitaTecnica visita : visitasProximas) {
                kafkaTemplate.send(topicName, visita.getId()); // Envia o ID da visita para a fila
                logger.info("Enviando notificação para a fila Kafka para a visita técnica com ID: {}", visita.getId());
            }
         } finally {
            Date fim = new Date(); // Guarda o tempo de fim
            logger.info("Fim da execução do job de notificação de visita técnica às {}. Tempo de execução: {}ms",
                        dateFormat.format(fim), (fim.getTime() - inicio.getTime()));
        }
    }
}