package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.HistoricoAgendamentoVisitaTecnica;
import com.jacto.agendamento.repository.HistoricoAgendamentoVisitaTecnicaRepository;
import com.jacto.agendamento.service.HistoricoAgendamentoVisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoAgendamentoVisitaTecnicaServiceImpl implements HistoricoAgendamentoVisitaTecnicaService {

    @Autowired
    private HistoricoAgendamentoVisitaTecnicaRepository repository;

    @Override
    public List<HistoricoAgendamentoVisitaTecnica> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<HistoricoAgendamentoVisitaTecnica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<HistoricoAgendamentoVisitaTecnica> buscarPorIdVisita(Long idVisitaTecnica) {
        return repository.findByVisitaTecnicaId(idVisitaTecnica);
    }

    @Override
    public HistoricoAgendamentoVisitaTecnica salvar(HistoricoAgendamentoVisitaTecnica historico) {
        return repository.save(historico);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}