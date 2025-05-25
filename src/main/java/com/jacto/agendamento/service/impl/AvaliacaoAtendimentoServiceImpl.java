package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.AvaliacaoAtendimento;
import com.jacto.agendamento.repository.AvaliacaoAtendimentoRepository;
import com.jacto.agendamento.service.AvaliacaoAtendimentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoAtendimentoServiceImpl implements AvaliacaoAtendimentoService {

    @Autowired
    private AvaliacaoAtendimentoRepository repository;

    @Override
    public List<AvaliacaoAtendimento> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<AvaliacaoAtendimento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public AvaliacaoAtendimento salvar(AvaliacaoAtendimento avaliacao) {
        return repository.save(avaliacao);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<AvaliacaoAtendimento> buscarPorVisita(Long idVisitaTecnica) {
        return repository.findByVisitaId(idVisitaTecnica);
    }

    @Override
    public Double mediaAvaliacoesPorFuncionario(Long matriculaFuncionario) {
        return repository.findAverageAvaliacaoByFuncionarioMatricula(matriculaFuncionario);
    }

    @Override
    public List<AvaliacaoAtendimento> buscarPorMatriculaFuncionario(Long matriculaFuncionario) {
        return repository.findByVisitaFuncionarioMatricula(matriculaFuncionario);
    }

    @Override
    public List<AvaliacaoAtendimento> buscarPorPeriodoDataOperacao(Date inicio, Date fim) {
        return repository.findByDataHoraOperacaoBetween(inicio, fim);
    }

}