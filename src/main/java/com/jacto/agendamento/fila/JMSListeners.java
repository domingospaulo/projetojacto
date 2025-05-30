package com.jacto.agendamento.fila;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.jacto.agendamento.entity.Cliente;
import com.jacto.agendamento.entity.Fazenda;
import com.jacto.agendamento.entity.Funcionario;
import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.service.EmailService;
import com.jacto.agendamento.service.LocalizacaoService;
import com.jacto.agendamento.service.VisitaTecnicaService;

@Component
public class JMSListeners {
    private static final Logger logger = LoggerFactory.getLogger(JMSListeners.class);

    @Autowired
    private VisitaTecnicaService visitaTecnicaService;

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private EmailService emailService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  // Adicione esta linha
    
    @JmsListener(destination = "visita-tecnica-queue")
    public void processar(Long visitaId) {
        logger.info("Mensagem recebida com ID da visita: {}", visitaId);

        Optional<VisitaTecnica> visitaOpt = visitaTecnicaService.buscarPorId(visitaId);

        if (visitaOpt.isPresent()) {
            VisitaTecnica visita = visitaOpt.get();
            Funcionario funcionario = visita.getFuncionario();
            Fazenda fazenda = visita.getFazenda();
            Cliente cliente = fazenda.getCliente();

            String endereco = localizacaoService.getDetalhesLocalizacao(fazenda.getLatitude(), fazenda.getLongitude());

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
                    dateFormat.format(visita.getDataHoraVisitaInicioAgendado()),
                    endereco
            );

            try {
                emailService.enviarEmail(funcionario.getPessoa().getEmail(), assunto, texto);
                logger.info("Email enviado para {}", funcionario.getPessoa().getEmail());
            } catch (Exception e) {
                logger.error("Erro ao enviar email", e);
            }
        } else {
            logger.warn("Visita com ID {} não encontrada", visitaId);
        }
    }
}
