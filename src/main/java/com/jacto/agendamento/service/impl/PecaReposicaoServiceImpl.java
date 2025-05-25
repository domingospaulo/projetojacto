package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.PecaReposicao;
import com.jacto.agendamento.repository.PecaReposicaoRepository;
import com.jacto.agendamento.service.PecaReposicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaReposicaoServiceImpl implements PecaReposicaoService {

    @Autowired
    private PecaReposicaoRepository repository;

    @Override
    public List<PecaReposicao> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<PecaReposicao> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public PecaReposicao salvar(PecaReposicao peca) {
        return repository.save(peca);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}