package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.VisitaTecnica;
import com.jacto.agendamento.repository.VisitaTecnicaRepository;
import com.jacto.agendamento.service.VisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class VisitaTecnicaServiceImpl implements VisitaTecnicaService {

    @Autowired
    private VisitaTecnicaRepository repository;

    @Override
    public List<VisitaTecnica> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<VisitaTecnica> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public VisitaTecnica salvar(VisitaTecnica visita) {
        return repository.save(visita);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<VisitaTecnica> buscarPorMatriculaFuncionario(Long matriculaFuncionario) {
        return repository.findByFuncionarioMatricula(matriculaFuncionario);
    }

    @Override
    public List<VisitaTecnica> buscarPorIdFazenda(Long idFazenda) {
        return repository.findByFazendaId(idFazenda);
    }

    @Override
    public List<VisitaTecnica> buscarPorCodigoTipoServico(Integer codigoTipoServico) {
        return repository.findByTipoServicoCodigo(codigoTipoServico);
    }

    @Override
    public List<VisitaTecnica> buscarPorCodigoPrioridade(Integer codigoPrioridade) {
        return repository.findByPrioridadeCodigo(codigoPrioridade);
    }

    @Override
    public List<VisitaTecnica> buscarPorCodigoStatusVisita(Integer codigoStatusVisita) {
        return repository.findByStatusVisitaCodigo(codigoStatusVisita);
    }

    @Override
    public List<VisitaTecnica> buscarPorCodigoOcorrencia(Integer codigoOcorrencia) {
        return repository.findByOcorrenciaCodigo(codigoOcorrencia);
    }

    @Override
    public List<VisitaTecnica> buscarPorDataHoraVisitaInicioAgendadoEntre(Date inicio, Date fim) {
        return repository.findByDataHoraVisitaInicioAgendadoBetween(inicio, fim);
    }

    @Override
    public List<VisitaTecnica> buscarPorDataHoraVisitaInicioAgendadoEntreEFlagReagendamento(Date inicio, Date fim, Boolean flagReagendamento) {
        return repository.findByDataHoraVisitaInicioAgendadoBetweenAndFlagReagendamento(inicio, fim, flagReagendamento);
    }
}
