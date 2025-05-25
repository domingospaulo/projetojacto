package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Operacao;
import com.jacto.agendamento.repository.OperacaoRepository;
import com.jacto.agendamento.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperacaoServiceImpl implements OperacaoService {

    @Autowired
    private OperacaoRepository repository;

    @Override
    public List<Operacao> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Operacao> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public Operacao salvar(Operacao operacao) {
        return repository.save(operacao);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}