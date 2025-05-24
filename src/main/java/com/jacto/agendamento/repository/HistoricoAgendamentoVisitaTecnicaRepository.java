package com.jacto.agendamento.repository;

import com.jacto.agendamento.entity.HistoricoAgendamentoVisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoAgendamentoVisitaTecnicaRepository extends JpaRepository<HistoricoAgendamentoVisitaTecnica, Long> {

    // Buscar todos os históricos relacionados a uma visita técnica específica
    List<HistoricoAgendamentoVisitaTecnica> findByVisitaTecnicaId(Long idVisitaTecnica);
}