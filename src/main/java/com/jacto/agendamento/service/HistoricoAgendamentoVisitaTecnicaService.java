package com.jacto.agendamento.service;

import com.jacto.agendamento.entity.HistoricoAgendamentoVisitaTecnica;

import java.util.List;
import java.util.Optional;

public interface HistoricoAgendamentoVisitaTecnicaService {
    List<HistoricoAgendamentoVisitaTecnica> listarTodos();
    Optional<HistoricoAgendamentoVisitaTecnica> buscarPorId(Long id);
    List<HistoricoAgendamentoVisitaTecnica> buscarPorIdVisita(Long idVisitaTecnica);
    HistoricoAgendamentoVisitaTecnica salvar(HistoricoAgendamentoVisitaTecnica historico);
    void deletar(Long id);
}