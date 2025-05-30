package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.PecasReposicaoVisitaTecnica;
import com.jacto.agendamento.repository.PecasReposicaoVisitaTecnicaRepository;
import com.jacto.agendamento.service.PecasReposicaoVisitaTecnicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PecasReposicaoVisitaTecnicaServiceImpl implements PecasReposicaoVisitaTecnicaService {

    @Autowired
    private PecasReposicaoVisitaTecnicaRepository repository;

    @Override
    public PecasReposicaoVisitaTecnica salvar(PecasReposicaoVisitaTecnica entity) {
        return repository.save(entity);
    }

    @Override
    public List<PecasReposicaoVisitaTecnica> buscarPorVisitaTecnica(Long visitaTecnicaId) {
        return repository.findByVisitaTecnica_Id(visitaTecnicaId);
    }
}