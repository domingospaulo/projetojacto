package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Fazenda;
import com.jacto.agendamento.repository.FazendaRepository;
import com.jacto.agendamento.service.FazendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FazendaServiceImpl implements FazendaService {

    @Autowired
    private FazendaRepository repository;

    @Override
    public List<Fazenda> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Fazenda> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Fazenda> buscarPorMatriculaCliente(Long matriculaCliente) {
        return repository.findByClienteMatricula(matriculaCliente);
    }

    @Override
    public Fazenda salvar(Fazenda fazenda) {
        return repository.save(fazenda);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Fazenda> findByDescricaoAndClienteMatricula(String descricao, Long matriculaCliente) {
        return repository.findByDescricaoAndCliente_Matricula(descricao, matriculaCliente);
    }   
}