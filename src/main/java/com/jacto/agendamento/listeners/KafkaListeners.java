package com.jacto.agendamento.listeners;


import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.entity.Fazenda;
import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.service.EmailService;
import com.jacto.agendamento.service.VisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class KafkaListeners {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private VisitaTecnicaService visitaTecnicaService;

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "visita-tecnica-notificacoes", groupId = "grupo-notificacoes")
    void listener(Long visitaTecnicaId) {
        logger.info("Recebida notificação para a visita técnica com ID: {}", visitaTecnicaId);

        // Busca a visita técnica pelo ID
        Optional<VisitaTecnica> visitaOptional = visitaTecnicaService.buscarPorId(visitaTecnicaId);

        if (visitaOptional.isPresent()) {
            VisitaTecnica visita = visitaOptional.get();

            // Extrai os dados da visita técnica
            Funcionario funcionario = visita.getFuncionario();
            Fazenda fazenda = visita.getFazenda();
            Cliente cliente = fazenda.getCliente();
            Date dataHoraVisitaInicioAgendado = visita.getDataHoraVisitaInicioAgendado();

            // Formata a mensagem de e-mail
            String assunto = "Lembrete: Visita Técnica Agendada";
            String texto = String.format("Prezado(a) %s,\n\n" +
                            "A visita técnica para o cliente %s, na fazenda %s, está agendada para iniciar em %s.\n\n" +
                            "Endereço da Fazenda: %s\n\n" +
                            "Por favor, prepare-se para a visita.\n\n" +
                            "Atenciosamente,\n" +
                            "Equipe de Agendamento",
                    funcionario.getPessoa().getNome(),
                    cliente.getPessoa().getNome(),
                    fazenda.getDescricao(),
                    dateFormat.format(dataHoraVisitaInicioAgendado),
                    fazenda.getEndereco()
            );

            // Envia o e-mail para o funcionário
            try {
                emailService.enviarEmail(funcionario.getPessoa().getEmail(), assunto, texto);
            } catch (Exception e) {
                logger.error("Erro ao enviar e-mail para o funcionário {}: {}", funcionario.getPessoa().getNome(), e.getMessage(), e);
            }
            logger.info("E-mail de notificação enviado para o funcionário {} (ID da visita: {})", funcionario.getPessoa().getNome(), visitaTecnicaId);
        } else {
            logger.warn("Visita técnica com ID {} não encontrada.", visitaTecnicaId);
        }
    }
}