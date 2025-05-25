package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Prioridade;
import com.jacto.agendamento.repository.PrioridadeRepository;
import com.jacto.agendamento.service.PrioridadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrioridadeServiceImpl implements PrioridadeService {

    @Autowired
    private PrioridadeRepository repository;

    @Override
    public List<Prioridade> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Prioridade> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public Prioridade salvar(Prioridade prioridade) {
        return repository.save(prioridade);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}