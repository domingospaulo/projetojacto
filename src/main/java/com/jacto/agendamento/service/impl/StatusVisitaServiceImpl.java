package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.StatusVisita;
import com.jacto.agendamento.repository.StatusVisitaRepository;
import com.jacto.agendamento.service.StatusVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusVisitaServiceImpl implements StatusVisitaService {

    @Autowired
    private StatusVisitaRepository repository;

    @Override
    public List<StatusVisita> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<StatusVisita> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public StatusVisita salvar(StatusVisita statusVisita) {
        return repository.save(statusVisita);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}