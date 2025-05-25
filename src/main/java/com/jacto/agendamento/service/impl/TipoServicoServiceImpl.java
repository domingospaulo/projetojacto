package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.TipoServico;
import com.jacto.agendamento.repository.TipoServicoRepository;
import com.jacto.agendamento.service.TipoServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoServicoServiceImpl implements TipoServicoService {

    @Autowired
    private TipoServicoRepository repository;

    @Override
    public List<TipoServico> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<TipoServico> buscarPorCodigo(Integer codigo) {
        return repository.findById(codigo);
    }

    @Override
    public TipoServico salvar(TipoServico tipoServico) {
        return repository.save(tipoServico);
    }

    @Override
    public void deletar(Integer codigo) {
        repository.deleteById(codigo);
    }
}