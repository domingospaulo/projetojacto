package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Ocorrencia;
import com.jacto.agendamento.repository.OcorrenciaRepository;
import com.jacto.agendamento.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OcorrenciaServiceImpl implements OcorrenciaService {

    @Autowired
    private OcorrenciaRepository repository;

    @Override
    public List<Ocorrencia> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Ocorrencia> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public Ocorrencia salvar(Ocorrencia ocorrencia) {
        return repository.save(ocorrencia);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}