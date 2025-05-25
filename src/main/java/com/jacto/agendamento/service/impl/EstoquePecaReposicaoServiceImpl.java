package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.EstoquePecaReposicao;
import com.jacto.agendamento.repository.EstoquePecaReposicaoRepository;
import com.jacto.agendamento.service.EstoquePecaReposicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoquePecaReposicaoServiceImpl implements EstoquePecaReposicaoService {

    @Autowired
    private EstoquePecaReposicaoRepository repository;

    @Override
    public List<EstoquePecaReposicao> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<EstoquePecaReposicao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<EstoquePecaReposicao> buscarPorCodigoPecaReposicao(Integer codigoPecaReposicao) {
        return repository.findByPecaReposicaoCodigo(codigoPecaReposicao);
    }

    @Override
    public EstoquePecaReposicao salvar(EstoquePecaReposicao estoque) {
        return repository.save(estoque);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}