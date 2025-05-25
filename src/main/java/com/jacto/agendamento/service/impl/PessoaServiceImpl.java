package com.jacto.agendamento.service.impl;

import com.jacto.agendamento.entity.Pessoa;
import com.jacto.agendamento.repository.PessoaRepository;
import com.jacto.agendamento.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public List<Pessoa> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<Pessoa> buscarPorCpfCnpj(String cpfCnpj) {
        return repository.findById(cpfCnpj);
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Override
    public void deletar(String cpfCnpj) {
        repository.deleteById(cpfCnpj);
    }
}